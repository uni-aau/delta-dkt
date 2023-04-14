package network2;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.assertEquals;

import android.util.Log;


public class NetworkConnectionTest {

    private final static int PORT = 5050;

    private final static String TAG = "NetworkConnectionTest";
    private NetworkConnection client;
    private ServerSocket serverSocket;
    private Socket socket;

    @BeforeAll
    public void setUp() throws IOException {
        //serverSocket = new ServerSocket(PORT); // use port 9090 for the server
        Log.d(TAG,"Established a server socket on port"+PORT+", listening for incoming requests");
        new Thread(() -> {
            try {
                socket = serverSocket.accept(); // accept the incoming connection and create the socket
                Log.d(TAG, "Server: Accepted incoming client request. Assigned server<-->client connection on port: "+socket.getPort());
                client = new NetworkConnection(socket);
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @AfterAll
    public void tearDown() throws IOException, InterruptedException {
        client.close();
        socket.close();
        serverSocket.close();
    }

    @Test
    public void testSendAndReceive() throws IOException,InterruptedException {
        Log.d(TAG,"Client: Socket request send to Server localhost on port "+PORT+".");
        NetworkConnection sender = new NetworkConnection(new Socket("localhost", PORT)); // connect to the serverport

        sender.send("Hello from client");
        Thread.sleep(1000);
        assertEquals("Hello from client", client.getLastMsgReceived());
    }
}