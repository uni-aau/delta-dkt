package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class Message {
    private boolean expectsAnswer;
    private String payLoad;
    private MessageType type;

    private static final long TIMEOUT = 500000000l;

    public Message(boolean expectsAnswer, String payLoad, MessageType type) {
        this.expectsAnswer = expectsAnswer;
        this.payLoad = payLoad;
        this.type = type;
    }

    public String sendMessage(PrintStream printStream, BufferedReader bufferedReader){

        return Message.sendAndReceive(printStream, bufferedReader, payLoad, expectsAnswer);
    }

    /**
     * Sends a message and receives a potential response
     * @param printStream
     * @param bufferedReader
     * @param message
     * @param expectsAnswer
     * @return
     */
    public static String sendAndReceive(PrintStream printStream, BufferedReader bufferedReader, String message, boolean expectsAnswer) {
        String answer = null;

        printStream.println(message);
        printStream.flush();
        if (expectsAnswer) {
            answer = "";
            try {

                long time = System.nanoTime();
                //Wait for the recipient to respond
                while (!bufferedReader.ready()) {
                    if(System.nanoTime()-time > TIMEOUT) {
                        //TODO: This timeout could be due to bad networking, maybe pass a flag to the logic so the user can get feedback?
                        System.err.println("TIMEOUT WHILE WAITING FOR RESPONSE ON MESSAGE: "+message);
                        break;
                    }

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

}
