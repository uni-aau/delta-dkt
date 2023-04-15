package network2;

import static org.junit.jupiter.api.Assertions.*;

import android.app.Instrumentation;
import android.content.Context;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ServerNetworkClientTest {
    private static ServerNetworkClient server;
    private static NetworkConnection connection;
    private static final int PORT = 12312;

    /**
     * since ServerNetworkClient stores every incoming request in a list of NetworkConnections , the Server-Side client Connection is given in the connList atIndex 0
     * @throws IOException
     */
    @BeforeAll
    static void setup() throws IOException{
        server = new ServerNetworkClient(PORT);
        System.out.println("TRYING TO START");
        server.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        connection = new NetworkConnection(new Socket("localhost",PORT));
        connection.start();
    }

    @AfterAll
    static void teardown() throws IOException{
        server.tearDown();
    }

    @Test
    void testSendMessageToServer() throws InterruptedException {
        String message = "Hello, server!";
        connection.send(message);
        Thread.sleep(1000);
        String receivedMessage = server.getConnections().get(0).getLastMsgReceived();
        assertEquals(message, receivedMessage);
    }

    @Test
    void testBroadcastMessageToClients() throws InterruptedException {
        String message = "Hello, clients!";
        server.broadcast(message);
        Thread.sleep(1000);
        String receivedMessage = connection.getLastMsgReceived();
        assertEquals(message, receivedMessage);
    }

    @Test
    public void testGetter(){
        assertEquals(PORT, server.getPort());
    }


}