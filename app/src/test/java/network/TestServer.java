package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class TestServer extends Thread {


    private ServerSocket serverSocket;
    private Socket socket;

    private Stack<String> outputBuffer;

    private static final long TIMEOUT = 20000000l;

    private boolean isRunning;

    private Object runningToken;

    private int port;

    private Set<String> answeredMessages;

    private Set<String> independentMessages;
    public TestServer(int port) {
        this.port = port;

        outputBuffer = new Stack<>();
        answeredMessages = new HashSet<>();
        independentMessages = new HashSet<>();
        runningToken = "";
        isRunning = true;

    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            PrintStream ps = new PrintStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {

                synchronized (outputBuffer) {

                    while (!outputBuffer.isEmpty()) {

                        ps.println(outputBuffer.pop());

                        ps.flush();

                        boolean gotAnswer = waitForAnswer(br);

                        if (gotAnswer) {

                            String answer = getAnswer(br);
                            answeredMessages.add(answer);

                            System.out.println("CLIENT ANSWERED: " + answer);
                        } else {
                            System.out.println("<No Answer>");
                        }


                    }
                }
                    boolean receivedMessage = waitForAnswer(br);

                    if (receivedMessage) {
                        String message = getAnswer(br);
                        independentMessages.add(message);

                        System.out.println("CLIENT SENT: " + message);
                    }


                synchronized (runningToken) {
                    if (!isRunning) {
                        serverSocket.close();
                        break;
                    }
                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        synchronized (runningToken) {
            isRunning = false;
        }
    }

    public void insertIntoOutputBuffer(String message){

        synchronized (outputBuffer){

            outputBuffer.push(message);
        }
    }

    private boolean waitForAnswer(BufferedReader br) throws IOException, InterruptedException {
        boolean gotAnswer = true;
        long time = System.nanoTime();
        while (!br.ready()) {

            if (System.nanoTime() - time > TIMEOUT) {

                gotAnswer = false;
                break;
            }
            Thread.sleep(1);
        }

        return gotAnswer;
    }

    private String getAnswer(BufferedReader br) throws IOException {
        String answer = "";

        while (br.ready()) {
            answer += br.readLine();
        }
        return answer;
    }

    public Set<String> getAnsweredMessages() {
        return answeredMessages;
    }

    public Set<String> getIndependentMessages() {
        return independentMessages;
    }
}
