package network2;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ServerNetworkClientTest {
    private static ServerNetworkClient server;
    private static NetworkClientConnection client;
    private static int dynServerPORT;


    /**
     * since ServerNetworkClient stores every incoming request in a list of NetworkConnections , the Server-Side client Connection is given in the connList atIndex 0
     * @throws IOException
     */
    @BeforeAll
    static void setup() throws IOException, InterruptedException{
        server = new ServerNetworkClient();
        System.out.println("TRYING TO START");
        server.start();

        Thread.sleep(1000);
        dynServerPORT = server.getPort();
        client = new NetworkClientConnection("localhost",server.getPort(), 1000, null);
        client.start();
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
    void testGetter(){
        assertEquals(dynServerPORT, server.getPort());
    }


}