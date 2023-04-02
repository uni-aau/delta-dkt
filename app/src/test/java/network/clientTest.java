package network;

import static org.junit.jupiter.api.Assertions.*;

import android.util.Log;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

class clientTest {

    static TestServer server;

    static Client client;

    static final int orderTestSize = 100;


    @BeforeAll
    static void setup() {
        server = new TestServer(6868);
        client = new Client("localhost", 6868);

        client.start();
        server.start();
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

        waitForTraffic();

        assertTrue(server.getIndependentMessages().contains("ping"));



    }

    @Test
    void sendAndReceive() {
        server.insertIntoOutputBuffer("ECHOTEST");
        waitForTraffic();

        assertTrue(server.getAnsweredMessages().contains("ECHOTEST"));

    }

    @Test
    void testOrder(){
        for(int i = 0; i<orderTestSize;i++){
            server.insertIntoOutputBuffer("MESSAGE"+i);
        }

        waitForTraffic();

        for(int i = 0; i<orderTestSize;i++){
            assertEquals("MESSAGE"+i,server.getAnsweredMessages().get(i));

        }

    }

    @Test
    void testClientSend(){
        client.insertIntoOutputBuffer(new Message(false, "TESTPING", MessageType.PING));

        waitForTraffic();

        assertTrue(server.getIndependentMessages().contains("TESTPING"));
    }

    public void waitForTraffic(){
        try {
            Thread.sleep(100);
            while(!server.hasFinishedQueue()&& !client.hasFinishedQueue()){
                Thread.sleep(1);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}