package network2;

import static ClientUIHandling.Constants.LOG_NETWORK;
import static ClientUIHandling.Constants.PREFIX_ADD_USER_TO_LIST;
import static delta.dkt.activities.MainActivity.user;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayDeque;

import ClientUIHandling.ClientLogic;
import ClientUIHandling.Constants;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.MainMenuActivity;


/**
 * This class shall be the underlying base class to handle network level I/O for each client
 * <p>
 * This class shall be used as an abstraction object for all server side network classes
 * This class provides all the features that any socket based network connection needs. It features
 * a socket property, a buffered reader and writer for I/O on each socket
 **/


public class NetworkConnection extends Thread { //execute each instance within a separate thread

    //properties needed
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    private String lastMsgReceived;

    private static final String TAG = LOG_NETWORK + "-NC";

    private boolean isRunning;

    private final Object runningToken;

    private final ClientLogic logic;

    private final ArrayDeque<String> outputBuffer;

    private String ip;
    private int port;
    private int timeout;

    private boolean closeHasBeenRequested;

    private Object closeRequestToken;

    //we introduce the socket as parameter because the server will accept/retrieve socket objects
    //while listening to its port
    //for us , the port of the socket is defined by the client communicating with us
    public NetworkConnection(String ip, int port, int timeout, ClientLogic logic) {
        Log.d(TAG, "Saving socket for client connection and creating Reader/Writer Objects");
        this.isRunning = true;
        runningToken = "";
        this.logic = logic;
        outputBuffer = new ArrayDeque<>();

        this.ip = ip;
        this.port = port;
        this.timeout = timeout;
        closeHasBeenRequested = false;
        closeRequestToken = "";
    }

    public NetworkConnection(Socket socket, ClientLogic logic) {
        Log.d(TAG, "Saving socket for client connection and creating Reader/Writer Objects");
        this.isRunning = true;
        runningToken = "";
        this.logic = logic;
        outputBuffer = new ArrayDeque<>();

        this.socket = socket;
        closeHasBeenRequested = false;
        closeRequestToken = "";
    }

    @Override
    public void run() {   //equivalent to the read method .. always active
        try {
            if (socket == null) {

                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), timeout);
            }
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

            if (!MainMenuActivity.role) {
                String clientID = reader.readLine();
                GameViewActivity.clientID = Integer.parseInt(clientID.split(":")[1]);

                if (GameViewActivity.clientID == -1) {
                    if (logic != null) {
                        logic.sendHandle(Constants.PREFIX_SERVER_FULL, Constants.MAINMENU_ACTIVITY_TYPE);
                    }
                    return;
                }
                send(Constants.PREFIX_SERVER + ":" + PREFIX_ADD_USER_TO_LIST + " " + user + " " + GameViewActivity.clientID);
            } else {
                GameViewActivity.clientID = 1;
            }

            Log.d(TAG, "Waiting for incoming messages");
            while (true) {
                read: if (reader.ready()) {

                    if(hasCloseBeenRequested()){
                        Log.e(TAG, "CONNECTION COULDNT READ, CLOSE HAS ALREADY BEEN REQUESTED");
                        break read;
                    }

                    String msg = reader.readLine();
                    //if we have received a message , handle it
                    Log.d(TAG, "Incoming message " + msg);
                    this.lastMsgReceived = msg;
                    // BEGIN-NOSCAN
                    if (logic != null) {

                        String[] messageSplit = msg.split(":");
                        if (messageSplit.length >= 2) {
                            logic.sendHandle(messageSplit[1], messageSplit[0]);
                        }
                    }
                    // END-NOSCAN


                }


                sendThroughNetwork();


                synchronized (runningToken) {
                    if (!isRunning) {
                        break;
                    }
                }

            }

            //TRY TO ALLOW LAST MESSAGES TO SEND
            synchronized (closeRequestToken){
                if(hasCloseBeenRequested()){
                    synchronized (outputBuffer){
                        if(outputBuffer.isEmpty()){
                            synchronized (runningToken){
                                isRunning = false;
                            }
                        }
                    }
                }
            }

            Thread.sleep(1);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());

        } catch (InterruptedException e) {
            Log.e(TAG, "Interrupted!" + e);

            Thread.currentThread().interrupt();
        } finally {
            // Close the connection always!
            try {
                this.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        synchronized (outputBuffer) {
            if (!outputBuffer.isEmpty()) {
                for (String message : outputBuffer) {
                    Log.e(TAG, "CONNECTION COULDNT SEND " + message + ". CONNECTION IS ALREADY CLOSED!");
                }
            }
        }
    }

    public void send(String message) {
        if (hasCloseBeenRequested()) {
            Log.e(TAG, "CONNECTION COULDNT SEND " + message + ". CONNECTION HAS ALREADY BEEN REQUESTED TO CLOSE!");
            return;
        }

        synchronized (outputBuffer) {

            outputBuffer.add(message);

        }

    }

    private void sendThroughNetwork() {
        synchronized (outputBuffer) {

            while (!outputBuffer.isEmpty()) {
                try {
                    String message = outputBuffer.pop();
                    Log.d(TAG, "Sending following message to server: " + message);
                    writer.write(message);
                    writer.newLine(); //adds newline == NULLBYTE termination of messages (EOF signal)
                    writer.flush(); //flushes the message within the OUTPUTBUFFER -> sends to cient/server via Socket
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getLastMsgReceived() {
        if (this.lastMsgReceived == null) {
            return "";
        }
        return this.lastMsgReceived;
    }

    public void close() {
        synchronized (closeRequestToken) {
            closeHasBeenRequested = true;
        }

    }

    public boolean hasCloseBeenRequested() {
        synchronized (closeRequestToken) {
            return closeHasBeenRequested;
        }
    }

    private void shutdown() throws IOException {
        if (this.reader != null) {
            this.reader.close();
        }
        if (this.writer != null) {
            this.writer.close();
        }
        if (this.socket != null) {
            this.socket.close();
        }
        synchronized (runningToken) {
            isRunning = false;
        }
    }


}
