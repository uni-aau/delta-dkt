package network;

import static org.junit.jupiter.api.Assertions.*;

import android.util.Log;

import org.junit.jupiter.api.AfterAll;
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

    @Test
    void ping() {

        client.ping();

        wait(100);

        assertTrue(server.getIndependentMessages().contains("ping"));

    }

    @Test
    void sendAndReceive() {
        server.insertIntoOutputBuffer("ECHOTEST");
        wait(100);

        assertTrue(server.getAnsweredMessages().contains("ECHOTEST"));

    }

    public void wait(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}