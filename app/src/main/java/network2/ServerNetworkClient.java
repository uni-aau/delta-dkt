package network2;

import android.content.Context;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import delta.dkt.activities.MainActivity;

/**
 * This class maintains a set of clientNetworkConnections and listens to a
 * predefined server port via Socket for Incoming Connections
 */
public class ServerNetworkClient extends Thread { //always executed on a separate thread (IO)

    private ServerSocket serverSocket;

    private int port; //the number of the port where the serverThread listens on for incoming connections

    public boolean serverInterrupted;

    private List<NetworkConnection> clientConnections ;

    private Context context;

    private NetworkServiceDiscovery nsd;

    private static int idCounter = 1;


    public ServerNetworkClient(){
      initProperties();
    }

    public ServerNetworkClient(Context context){
        this.context = context;
        this.nsd = new NetworkServiceDiscovery(context);

        initProperties();
    }

    public String getIP(){
        if(clientConnections.size() == 1) {
            return clientConnections.get(0).getIP();
        }else{
            return clientConnections.get(1).getIP();
        }
    }

    private void initProperties(){
        this.port = 0; //not yet set AND the prequesite for allocating a dynamic port
        clientConnections = new ArrayList<>(); //init list
        serverInterrupted = false;
    }

    @Override
    public void run() {
        try{
            //at first , initialize serverSocket at a dynamic port
            initializeServerSocket();

            //before start listening, register the service.. the port has been set
            if(nsd != null){ //for testcases this class has to be called without service registration
                nsd.registerService(getPort());
                System.out.println("REGISTERED SERVICE");
            }

            System.out.println("Server started on port " + port);
            while (serverInterrupted == false) {
                Socket socket = serverSocket.accept();
                NetworkConnection clientSocket = new NetworkConnection(socket, MainActivity.logic);
                clientConnections.add(clientSocket);
                clientSocket.start();

                clientSocket.send("IPINNIT:"+idCounter++);


            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        finally { //always clean up, short extra try-catch needed because if exeption thrown above, teardown might not be executed
            try{ tearDown();}catch (IOException ex){ ex.printStackTrace(); }
        }
    }

    /** later when we have the option to introduce a dynamic port */
    public void initializeServerSocket() throws IOException{
        // Initialize a server socket on the next available port.
        this.serverSocket = new ServerSocket(this.port);
        // Store the chosen port.
        this.port = serverSocket.getLocalPort();
    }

    public synchronized void broadcast(String message) { //synchronized might not be necessary since there should always be <= 1 server thread
        for (NetworkConnection clientConnection : clientConnections) {
            clientConnection.send(message);
        }
    }

    /**
     * This method will use the required syntax of the original broadcoast method.
     * @param activity The activity name, using a variable in the Constants class, which is the target.
     * @param prefix The prefix defines the action that should be executed and should also be defined in the Constants class.
     * @param args The additional parameters that follow along with the command.
     */
    public synchronized void broadcast(String activity, String prefix, String[] args){
        this.broadcast(activity+":"+prefix+" "+String.join(";", args));
    }

    /**
     * TODO: move this method and all other things concerning connection handling to a separate clientHandler class
     * @param client
     */
    public synchronized void removeClient(NetworkConnection client) {
        clientConnections.remove(client);
    }

    public void tearDown() throws IOException{
        for(NetworkConnection clientConn : clientConnections){
            clientConn.close();
            clientConn.interrupt();
        }//after disposing all the clients, get rid of nsd service (unregister) and stop the server
        if(nsd != null){ nsd.tearDown();}
        serverSocket.close();
    }

    /**
     * property getter/setter
     */
    public int getPort() {
        return port;
    }

    public List<NetworkConnection> getConnections(){
        return this.clientConnections;
    }


}



