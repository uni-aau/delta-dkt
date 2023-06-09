package network2;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.assertEquals;

import delta.dkt.activities.MainMenuActivity;


class NetworkConnectionTest {

    private final static int PORT = 5050;

    private static NetworkConnection client;
    private static ServerSocket serverSocket;

    @BeforeAll
    static void setUp() throws IOException {
        MainMenuActivity.role = true;
        serverSocket = new ServerSocket(PORT);
        new Thread(() -> {
            try {
                Socket socket = serverSocket.accept();
                client = new NetworkConnection(socket,null);
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        client.close();
        serverSocket.close();
    }

    @Test
    void testSendAndReceive() throws IOException {
        NetworkConnection sender = new NetworkConnection(new Socket("localhost", PORT),null);
        sender.start();

        String message = "Hello from client";
        sender.send(message);

        try {
            Thread.sleep(1000); // wait for the message to be processed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String lastMsgReceived = client.getLastMsgReceived();
        assertEquals(message, lastMsgReceived);
    }


}