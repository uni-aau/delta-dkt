package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.SynchronousQueue;

public class Client extends Thread {

    private Socket socket;
    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private ArrayDeque<Message> outputBuffer;

    private String host;
    private int port;

    private boolean isRunning;

    private Object runningToken;


    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        outputBuffer = new ArrayDeque<>();
        isRunning = true;
        runningToken = "";
    }

    private void initialize() {
        try {
            this.socket = new Socket(host, port);
            this.printStream = new PrintStream(socket.getOutputStream());
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ping() {

        Message m = new Message(true, "ping", MessageType.PING);
        insertIntoOutputBuffer(m);
    }

    /**
     * This method inserts a message into the output buffer, which then gets send to the server.
     * The reason it has to be synchronized is because an external thread can call this method
     * and the outputbuffer gets also used in the internal thread.
     *
     * @param m
     */
    public void insertIntoOutputBuffer(Message m) {
        synchronized (outputBuffer) {
            outputBuffer.add(m);
        }
    }


    @Override
    public void run() {

        //Open the socket and set the buffered-reader and the print-stream
        initialize();

        while (true) {
            //Check if a message has to be sent
            // (synchronized because of the exposure to external threads in the insertIntoOutputBuffer method)
            synchronized (outputBuffer) {
                sendBuffer();
            }
            //Check if the server has sent a message to the client
            checkInput();

            //Check if the client has to shutdown
            // (synchronized because of the exposure to external threads in the shutdown method)
            synchronized (runningToken) {
                if (!isRunning) {
                    cleanup();
                    break;
                }
            }

            //Give the synchronized resources breathing space
            // (so external threads can access them in the synchronous spaces)
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void cleanup() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method will set the boolean isRunning to true
     * which the internal thread checks periodically and
     * will cause the thread to break the loop and finish
     */
    public void shutdown() {
        synchronized (runningToken) {
            this.isRunning = false;
        }
    }

    /**
     * Sends the Messages in the buffer to the server
     */
    private void sendBuffer() {
        while (!outputBuffer.isEmpty()) {
            //The first element in the deque (to ensure the right order) gets sent to the server
            //and an reply can be received
            String answer = outputBuffer.poll().sendMessage(printStream, bufferedReader);
            if (answer != null) {
               handleInput(answer);
            }
        }
    }


    private void checkInput() {

        try {
            if (bufferedReader.ready()) {
                String answer = "";
                while (bufferedReader.ready()) {
                    answer += bufferedReader.readLine();
                }
                if (!answer.isEmpty()) {
                    handleInput(answer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean hasFinishedQueue() {
        synchronized (outputBuffer) {
            return outputBuffer.isEmpty();
        }
    }

    /**
     * Handles the input the client receives. This is currently incomplete and it will (hopefully)
     * defer the message to the logic
     * @param message
     */
    private void handleInput(String message) {
        //TODO Defer to the logic classes
        //Currently for testing: ECHO
        if (!message.equals("pong")) {
            insertIntoOutputBuffer(new Message(false, message, MessageType.ECHO));
        }
    }
}
