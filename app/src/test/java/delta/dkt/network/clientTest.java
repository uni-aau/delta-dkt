package delta.dkt.network;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import network.Client;
import network.Message;
import network.MessageType;

class clientTest {

    static TestServer server;

    static Client client;

    static final int orderTestSize = 100;


    @BeforeAll
    static void setup() {
        server = new TestServer(6868);
        client = new Client("localhost", 6868);
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
           // System.out.println("ping "+server.getAnsweredMessages().size() );
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
           // System.out.println("sendAndReceive "+server.getAnsweredMessages().size() );
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
           // System.out.println("testOrder "+server.getAnsweredMessages().size() );
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
          //  System.out.println("testClientSend "+server.getAnsweredMessages().size() );
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
          //  System.out.println("testMessageSeparation "+server.getAnsweredMessages().size() );
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

    public void waitForTraffic(){
        try {
            Thread.sleep(250);
            while(!server.hasFinishedQueue()&& !client.hasFinishedQueue()){
                Thread.sleep(1);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}