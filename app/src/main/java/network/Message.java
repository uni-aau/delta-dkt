package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class Message {
    private boolean expectsAnswer;
    private String payLoad;
    private MessageType type;

    public Message(boolean expectsAnswer, String payLoad, MessageType type) {
        this.expectsAnswer = expectsAnswer;
        this.payLoad = payLoad;
        this.type = type;
    }

    public String sendMessage(PrintStream printStream, BufferedReader bufferedReader){

        return Message.sendAndReceive(printStream, bufferedReader, payLoad, expectsAnswer);
    }

    public static String sendAndReceive(PrintStream printStream, BufferedReader bufferedReader, String message, boolean expectsAnswer) {
        String answer = null;

        printStream.println(message);
        printStream.flush();
        if (expectsAnswer) {
            answer = "";
            try {

                while (!bufferedReader.ready()) {
                    Thread.sleep(1);
                }

                while (bufferedReader.ready()) {
                    answer += bufferedReader.readLine();

                }

            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return answer;

    }

    public static int ping(PrintStream printStream, BufferedReader bufferedReader){
        long time = System.nanoTime();
        sendAndReceive(printStream,bufferedReader,"ping", true);
        long difference = System.nanoTime()-time;

        int millis = (int)(((double)difference)/1000000.0);

        return millis;
    }
}
