package network2;

import static org.junit.jupiter.api.Assertions.*;

import android.app.Instrumentation;
import android.content.Context;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

class ServerNetworkClientTest {

    private Context context;

    public ServerNetworkClientTest(Context context){
        this.context = context;
    }

    @Test
    /**
     * If a start a Server , it´ll listen on a given port for incoming connections.
     * Test if a connection on the port is accepted after starting
     */
    void start() {
        ServerNetworkClient server = new ServerNetworkClient(69533,context);
        server.start(); //server started

        int port = server.getPort();
        //now try to connect to server on the given port
        //create NetworkConnection object in a separate Thread
        Socket socket = null;
        try {
            socket = new Socket("localhost",port);
            assert(socket.isConnected());
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void broadcast() {
    }

    @Test
    void removeClient() {
    }

    @Test
    void registerService() {
    }

    @Test
    void initializeRegistrationListener() {
    }
}