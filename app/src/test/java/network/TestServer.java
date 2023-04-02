package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * A testclass for the client to simulate a server.
 * This will get deprecated when the actual server gets implemented.
 */
public class TestServer extends Thread {


    private ServerSocket serverSocket;
    private Socket socket;

    private ArrayDeque<String> outputBuffer;

    private static final long TIMEOUT = 20000000l;

    private boolean isRunning;

    private Object runningToken;

    private int port;

    private ArrayList<String> answeredMessages;

    private ArrayList<String> independentMessages;

    private static final String messageDelimiter = "\0";

    public TestServer(int port) {
        this.port = port;

        outputBuffer = new ArrayDeque<>();
        answeredMessages = new ArrayList<>();
        independentMessages = new ArrayList<>();
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
                            handleMessage(answer, false);

                           // System.out.println("CLIENT ANSWERED: " + answer);
                        } else {
                           // System.out.println("<No Answer>");
                        }


                    }
                }


                if (br.ready()) {
                    String message = getAnswer(br);
                    handleMessage(message,true);



                   // System.out.println("CLIENT SENT: " + message);
                }


                synchronized (runningToken) {
                    if (!isRunning) {
                        serverSocket.close();
                        break;
                    }
                }

                Thread.sleep(1);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleMessage(String message, boolean isIndependent){
        String[] messages = message.split(messageDelimiter);
        if(isIndependent){
            for(String m:messages){
                independentMessages.add(m);
                if(m.equals("ping")){
                    insertIntoOutputBuffer("pong");
                }
            }
        }else{
            for(String m:messages){
                answeredMessages.add(m);
            }
        }
    }

    public void shutdown() {
        synchronized (runningToken) {
            isRunning = false;
        }
    }

    public void insertIntoOutputBuffer(String message) {

        synchronized (outputBuffer) {

            outputBuffer.add(message);
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

    public boolean hasFinishedQueue(){
        synchronized (outputBuffer){
            return outputBuffer.isEmpty();
        }
    }

    private String getAnswer(BufferedReader br) throws IOException {
        String answer = "";

        while (br.ready()) {
            answer += br.readLine();
        }
        return answer;
    }

    public ArrayList<String> getAnsweredMessages() {
        return answeredMessages;
    }

    public ArrayList<String> getIndependentMessages() {
        return independentMessages;
    }
}
