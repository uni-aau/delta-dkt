package ClientUIHandling;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;

public class ClientLogic {

    private HashMap<String, ClientHandler> handlers;

    public static boolean isTEST;

    public ClientLogic(HashMap<String, ClientHandler> handlers) {

        this.handlers = handlers;
    }

    public void setTextOfTestElement(String text){
        sendHandle(text, ClientHandler.testType);
    }

    public void sendHandle(String message, String type) {


        //This distinction is necessary because the JUNIT environment does not have the android class implementation
        //The error messages refers to a dead website
        //https://g.co/androidstudio/not-mocked
        if(!isTEST) {
            if(handlers.containsKey(type)) {
                android.os.Message handleMessage = new Message();
                Bundle b = new Bundle();
                b.putString("payload", message);
                handleMessage.setData(b);
                handlers.get(type).sendMessage(handleMessage);
            }else{
                Log.e("ERROR", "Wrong type used.");
            }
        }

    }

    public HashMap<String, ClientHandler> getHandler() {
        return handlers;
    }
}
