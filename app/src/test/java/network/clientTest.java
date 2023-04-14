package network;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ClientUIHandling.ClientHandler;
import ClientUIHandling.ClientLogic;

class clientTest {

    static TestServer server;

    static Client client;

    static final int orderTestSize = 100;


    @BeforeAll
    static void setup() {
        ClientLogic.isTEST = true;
        server = new TestServer(6868);
        client = new Client("localhost", 6868, new ClientLogic(new ClientHandler(null)));
        server.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        client.start();

    }

    @AfterAll
    static void cleanup() {
        client.shutdown();
        server.shutdown();
    }
    @AfterEach
     void cleanupMessages(){
        server.getAnsweredMessages().clear();
        server.getIndependentMessages().clear();
    }

    @Test
    void ping() {

        client.ping();

        while(server.getIndependentMessages().size() != 1){

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        assertTrue(server.getIndependentMessages().contains("ping"));



    }

    @Test
    void sendAndReceive() {
        server.insertIntoOutputBuffer("ECHOTEST");
        while(server.getAnsweredMessages().size() != 1){

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        assertTrue(server.getAnsweredMessages().contains("ECHOTEST"));

    }

    @Test
    void testOrder(){
        for(int i = 0; i<orderTestSize;i++){
            server.insertIntoOutputBuffer("MESSAGE"+i);
        }

        while(server.getAnsweredMessages().size() != orderTestSize){

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for(int i = 0; i<orderTestSize;i++){
            assertEquals("MESSAGE"+i,server.getAnsweredMessages().get(i));

        }

    }

    @Test
    void testClientSend(){
        client.insertIntoOutputBuffer(new Message(false, "TESTPING", MessageType.PING));

        while(server.getAnsweredMessages().size() != 1){

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        assertTrue(server.getAnsweredMessages().contains("TESTPING"));
    }

    @Test
    void testMessageSeparation(){
        server.insertIntoOutputBuffer("MESSAGE1\0Message2\0Message3\0");

        while(server.getAnsweredMessages().size() != 3){

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        assertEquals("MESSAGE1",server.getAnsweredMessages().get(0));
        assertEquals("Message2",server.getAnsweredMessages().get(1));
        assertEquals("Message3",server.getAnsweredMessages().get(2));

    }

}