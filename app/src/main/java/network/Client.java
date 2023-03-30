package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Stack;

public class Client extends Thread{

    private Socket socket;
    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private Stack<Message> outputBuffer;

    private String host;
    private int port;

    private boolean isRunning;

    private Object runningToken;



    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        outputBuffer = new Stack<>();
        isRunning = true;
        runningToken = "";
    }

    private void initialize() {
        try {
            this.printStream = new PrintStream(socket.getOutputStream());
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ping(){

       Message m = new Message(true, "ping", MessageType.PING);
        insertIntoOutputBuffer(m);
    }

    public void insertIntoOutputBuffer(Message m){
        synchronized (outputBuffer){
            outputBuffer.push(m);
        }
    }



    @Override
    public void run() {
        try {
            this.socket = new Socket(host, port);
            initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       while(true){
           synchronized (outputBuffer){
              sendBuffer();
           }
           checkInput();

           synchronized (runningToken){
               if(!isRunning){
                   try {
                       socket.close();
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
                   break;
               }
           }

           try {
               Thread.sleep(1);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }

       }
    }

    public void shutdown(){
        synchronized (runningToken) {
            this.isRunning = false;
        }
    }

    private void sendBuffer(){
        while(!outputBuffer.isEmpty()){

            String answer = outputBuffer.pop().sendMessage(printStream, bufferedReader);
            if(answer!=null){
                System.out.println(answer);
            }
        }
    }

    private void checkInput(){
        try {
            String answer = "";
            while (bufferedReader.ready()) {
                answer+= bufferedReader.readLine();
            }
            if(!answer.isEmpty()){
                insertIntoOutputBuffer(new Message(false, answer, MessageType.ECHO));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
