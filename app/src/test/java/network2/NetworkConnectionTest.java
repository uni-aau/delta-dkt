package network2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;

public class NetworkConnectionTest {

    private static final int PORT = 9999;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private NetworkConnection networkConnection;

    @BeforeEach
    void setup() throws IOException {
        serverSocket = new ServerSocket(PORT);
        clientSocket = new Socket("localhost", PORT);
        networkConnection = new NetworkConnection(clientSocket);
    }

    @AfterEach
    void tearDown() throws IOException {
        networkConnection.close();
        clientSocket.close();
        serverSocket.close();
    }

    @Test
    void testSend() throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String message = "Test Message";
        networkConnection.send(message);
        writer.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        assertEquals(message, reader.readLine());
    }

    @Test
    void testClose() throws IOException {
        assertTrue(clientSocket.isConnected());
        networkConnection.close();
        assertFalse(clientSocket.isConnected());
    }
}