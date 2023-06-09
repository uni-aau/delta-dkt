package network2;

import android.content.Context;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ClientUIHandling.Config;

import android.util.Log;

import delta.dkt.activities.MainActivity;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;

import static ClientUIHandling.Constants.*;

/**
 * This class maintains a set of clientNetworkConnections and listens to a
 * predefined server port via Socket for Incoming Connections
 */
public class ServerNetworkClient extends Thread { //always executed on a separate thread (IO)

    private ServerSocket serverSocket;

    private int port; //the number of the port where the serverThread listens on for incoming connections

    private boolean serverInterrupted;

    private List<NetworkConnection> clientConnections;
    private NetworkServiceFinder nsd;


    private Object synchTearDownToken;


    public ServerNetworkClient() {
        initProperties();
    }

    public ServerNetworkClient(Context context) {
        this.nsd = new NetworkServiceFinder(context);

        initProperties();
    }

    public String getIP() {
        return getIPAddress();
    }

    public static String getIPAddress() {

        List<NetworkInterface> interfaces = new ArrayList<>();
        try {
            interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        } catch (SocketException e) {
            Log.e(LOG_ERROR, "Error while trying to create list of NetworkInterfaces: " + e);
        }
        for (NetworkInterface networkInterface : interfaces) {
            List<InetAddress> addresses = Collections.list(networkInterface.getInetAddresses());
            for (InetAddress address : addresses) {

                if (!address.isLoopbackAddress()) {
                    String ip = address.getHostAddress();
                    //Check if it is not an ipv6 address
                    if (ip.indexOf(':') == -1) {
                        return ip;
                    }

                }
            }
        }

        return "";
    }

    private void initProperties() {
        this.port = 0; //not yet set AND the prequesite for allocating a dynamic port
        clientConnections = new ArrayList<>(); //init list
        serverInterrupted = false;
        this.synchTearDownToken = "";
    }

    @Override
    public void run() {
        try {
            //at first , initialize serverSocket at a dynamic port
            initializeServerSocket();

            //before start listening, register the service.. the port has been set
            if (nsd != null) { //for testcases this class has to be called without service registration
                nsd.registerService(getPort());
                Log.d(LOG_NETWORK, "REGISTERED SERVICE");
            }

            Log.d(LOG_NETWORK, "Server started on port " + port);
            while (!serverInterrupted) {
                Socket socket = serverSocket.accept();
                NetworkConnection clientSocket = new NetworkConnection(socket, MainActivity.logic);
                clientConnections.add(clientSocket);
                clientSocket.start();

                if (Game.getPlayers().size() >= Config.MAX_CLIENTS) {
                    clientSocket.send("IPINNIT:-1");
                    continue;
                }

                // sets clientID & username
                int clientID = Game.getPlayers().size() + 1;
                String userName = "-";
                clientSocket.send("IPINNIT:" + clientID);

                Player player = new Player(userName);
                player.setId(clientID);

                Game.getPlayers().put(clientID, player);


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally { //always clean up, short extra try-catch needed because if exception thrown above, teardown might not be executed
            try {
                tearDown();
            } catch (IOException e) {
                Log.d(LOG_ERROR, "Error while tearing down server: " + e);
            }
        }
    }

    /**
     * later when we have the option to introduce a dynamic port
     */
    public void initializeServerSocket() throws IOException {
        // Initialize a server socket on the next available port.
        this.serverSocket = new ServerSocket(this.port);
        // Store the chosen port.
        this.port = serverSocket.getLocalPort();
    }

    public void broadcast(String message) { //synchronized is not necessary and only slows down the server
        synchronized (synchTearDownToken) {
            if(!hasClosed()) {
                for (NetworkConnection clientConnection : clientConnections) {
                    clientConnection.send(message);
                }
            }else{
                Log.e(LOG_ERROR, "COULDNT BROADCAST "+ message+", SERVER ALREADY CLOSED!");
            }
        }
    }

    /**
     * This method will use the required syntax of the original broadcoast method.
     *
     * @param activity The activity name, using a variable in the Constants class, which is the target.
     * @param prefix   The prefix defines the action that should be executed and should also be defined in the Constants class.
     * @param args     The additional parameters that follow along with the command.
     */
    public void broadcast(String activity, String prefix, String[] args) {
        this.broadcast(activity + ":" + prefix + " " + String.join(";", args));
    }

    public void stopThread() {
        synchronized (synchTearDownToken) {
            this.serverInterrupted = true;
        }
    }

    public void tearDown() throws IOException {
        stopThread();
        for (NetworkConnection clientConn : clientConnections) {
            clientConn.close();
            clientConn.interrupt();
        }//after disposing all the clients, get rid of nsd service (unregister) and stop the server
        if (nsd != null) {
            nsd.tearDown();
        }
        clientConnections.clear();
        serverSocket.close();
    }

    public boolean hasClosed() {
        synchronized (synchTearDownToken) {
            return this.serverInterrupted;
        }
    }

    /**
     * property getter/setter
     */
    public int getPort() {
        return port;
    }

    public List<NetworkConnection> getConnections() {
        return this.clientConnections;
    }
}



