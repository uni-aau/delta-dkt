package ClientUIHandling;

import android.os.Bundle;
import android.os.Message;

public class ClientLogic {

    private ClientHandler handler;

    public static boolean isTEST;

    public ClientLogic(ClientHandler handler) {
        this.handler = handler;
    }

    public void setTextOfTestElement(String text){
        sendHandle(text, ClientHandler.testType);
    }

    public void sendHandle(String message, String type) {


        //This distinction is necessary because the JUNIT environment does not have the android class implementation
        //The error messages refers to a dead website
        //https://g.co/androidstudio/not-mocked
        if(!isTEST) {
            android.os.Message handleMessage = new Message();
            Bundle b = new Bundle();
            b.putString(type, message);
            handleMessage.setData(b);
            handler.sendMessage(handleMessage);
        }

    }

    public ClientHandler getHandler() {
        return handler;
    }
}
