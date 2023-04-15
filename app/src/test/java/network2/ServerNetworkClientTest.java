package network2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ServerNetworkClientTest {
    private static ServerNetworkClient server;
    private static NetworkClientConnection client;
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

        try {
            client = new NetworkClientConnection("localhost",PORT, 1000, null);
            client.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @AfterAll
    static void teardown() throws Exception {
        client.stopConnection();
        server.tearDown();
    }

    @Test
    void testSendMessageToServer() throws Exception {
        String message = "Hello, server!";
        client.sendMessage(message);
        Thread.sleep(1000);
        String receivedMessage = server.getConnections().get(0).getLastMsgReceived();
        assertEquals(message, receivedMessage);
    }

    @Test
    void testBroadcastMessageToClients() throws InterruptedException {
        String message = "Hello, clients!";
        server.broadcast(message);
        Thread.sleep(1000);
        String receivedMessage = client.getConnection().getLastMsgReceived();
        assertEquals(message, receivedMessage);
    }

    @Test
    public void testGetter(){
        assertEquals(PORT, server.getPort());
    }


}