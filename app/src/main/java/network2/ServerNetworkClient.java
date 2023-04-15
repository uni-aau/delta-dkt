package network2;

import android.content.Context;
import android.net.Network;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.RegistrationListener;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


import java.net.ServerSocket;

/**
 * This class maintains a set of clientNetworkConnections and listens to a
 * predefined server port via Socket for Incoming Connections
 */
public class ServerNetworkClient extends Thread{ //always executed on a separate thread (IO)

    private ServerSocket serverSocket;

    private int port; //the number of the port where the serverThread listens on for incoming connections

    private List<NetworkConnection> clientConnections ;

    /** NSD vars
    private String serviceName = "delta_dkt";
    private String serviceType = "_delta_dkt.tcp";

    private Context context;

    RegistrationListener registrationListener;



    public ServerNetworkClient(Context context){
        ServerNetworkClient();
        this.context = context;
    }
     */

    public ServerNetworkClient(){
        this.port = 0; //not yet set AND the prequesite for allocating a dynamic port
        clientConnections = new ArrayList<>(); //init list
    }

    public void run() {
        try{
            //at first , initialize serverSocket at a dynamic port
            initializeServerSocket();

            //before start listening, register the service
            // registerService();
            //initializeRegistrationListener();

            System.out.println("Server started on port " + port);
            while (!isInterrupted()) {
                Socket socket = serverSocket.accept();
                NetworkConnection clientSocket = new NetworkConnection(socket);
                clientConnections.add(clientSocket);
                clientSocket.start();
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
            clientConn = null; //delete reference so everything gets disposed by gc
        }
        //serversocket is already disposed because of using statement
        //eliminate references to this instance to finish cleaning
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

    /**
     * NSD functions


    private void registerService() {
        // Create the NsdServiceInfo object, and populate it.
        NsdServiceInfo serviceInfo = new NsdServiceInfo();

        // The name is subject to change based on conflicts
        // with other services advertised on the same network.
        serviceInfo.setServiceName(this.serviceName);
        serviceInfo.setServiceType(this.serviceType);
        serviceInfo.setPort(this.port);


        NsdManager nsdManager = (NsdManager)this.context.getSystemService(Context.NSD_SERVICE);

        nsdManager.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
    }

    private void initializeRegistrationListener() {
        this.registrationListener = new RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                // Save the service name. Android may have changed it in order to
                // resolve a conflict, so update the name you initially requested
                // with the name Android actually used.
                serviceName = NsdServiceInfo.getServiceName();
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed! Put debugging code here to determine why.
                Log.d("NsdServiceInfo_failed","NetworkClient::initializeRegistrationListener::onRegistrationFailed"+serviceInfo.toString());
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                // Service has been unregistered. This only happens when you call
                // NsdManager.unregisterService() and pass in this listener.
                Log.d("NsdServiceInfo_unregistered", "Service "+arg0.getServiceName()+" has been unregistered. ("+ arg0.toString()+")");
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // unregistration failed. Put debugging code here to determine why.
                Log.d("NsdServiceInfo_unregistration_failed", "NetworkClient::initializeRegistrationListener::onUnregistrationFailed"
                        +"\n"+"errorCode: "+errorCode + "\n" + serviceInfo.toString());
            }
        };
    }



 */

}



