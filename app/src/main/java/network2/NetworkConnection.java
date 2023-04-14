package network2;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


/**
 *  This class shall be the underlying base class to handle network level I/O for each client
 *
 * This class shall be used as an abstraction object for all server side network classes
 * This class provides all the features that any socket based network connection needs. It features
 *  a socket property, a buffered reader and writer for I/O on each socket
 *
 **/


public class NetworkConnection extends Thread{ //execute each instance within a separate thread

    //properties needed
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    private String lastMsgReceived;

    private static final String TAG = "NetworkConnection";

    //we introduce the socket as parameter because the server will accept/retrieve socket objects
    //while listening to its port
    //for us , the port of the socket is defined by the client communicating with us
    public NetworkConnection(Socket connection){
        Log.d(TAG,"NetworkConnection::(): Saving socket for client connection and creating Reader/Writer Objects");
        try{
            this.socket = connection;
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        }
        catch (Exception ex){
                Log.e(TAG,ex.getMessage());
        }
    }

    @Override
    public void run() {   //equivalent to the read method .. always active
        try {
            Log.d(TAG, "Waiting for incoming messages");
            while (true) {
                String msg = reader.readLine(); //We wait for incoming messages
                if (msg != null) { //if we have received a message , handle it
                    Log.d(TAG, "Incoming message "+ msg);
                    this.lastMsgReceived = msg;
                    //TODO: Implement a handler that handles incoming game-related messages

                    //TODO: CALL CLIENT LOGIC

                    //TODO: IMPLEMENT HANDLE AS SYNCHRONIZED METHOD TO AVOID inconsistency due to concurrent executions
                    // or on message handler object level
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());

        } finally {
            // Close the connection always!
            try {
                this.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) {  //only active when called, executed on the caller-thread or instance thread??
        try {
            writer.write(message);
            writer.newLine(); //adds newline == NULLBYTE termination of messages (EOF signal)
            writer.flush(); //flushes the message within the OUTPUTBUFFER -> sends to cient/server via Socket
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getLastMsgReceived(){
        if(this.lastMsgReceived == null){
            return "";
        }
        return this.lastMsgReceived;
    }
    public void close() throws IOException {
        this.reader.close();
        this.writer.close();
        this.socket.close();
    }

}
