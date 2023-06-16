package ClientUIHandling;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import delta.dkt.activities.GameViewActivity;

public class ClientLogic {

    private HashMap<String, ClientHandler> handlers;

    public static boolean isTEST;

    public ClientLogic(HashMap<String, ClientHandler> handlers) {

        this.handlers = handlers;
    }

    public void sendHandle(String message, String type) {


        //This distinction is necessary because the JUNIT environment does not have the android class implementation
        //The error messages refers to a dead website
        //https://g.co/androidstudio/not-mocked

        if (!isTEST) {

            if(attemptSend(message,type)){
                return;
            }

            for(int i = 0; i<10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.w("Warning", "Interrupted!", e);
                    // Restore interrupted state...
                    Thread.currentThread().interrupt();
                }
                if (attemptSend(message, type)) {
                    return;
                } else {
                    Log.i("ERROR", "INVALID UI TYPE " + type);
                }
            }


        }
    }

    private boolean attemptSend(String message, String type){
        synchronized (handlers) {
            if (handlers.containsKey(type)) {
                send(message, type);
                return true;
            }
        }
        return false;
    }


    private void send(String message, String type) {

        android.os.Message handleMessage = new Message();
        Bundle b = new Bundle();
        b.putString("payload", message);
        handleMessage.setData(b);

        handlers.get(type).sendMessage(handleMessage);
    }

    public HashMap<String, ClientHandler> getHandler() {
        return handlers;
    }

    public void registerActivity(String type, AppCompatActivity activity) {
        synchronized (handlers) {
            if (handlers.containsKey(type)) {
                Log.i("LOGICINFO", "ALREADY CONTAINS " + type + " with signature " + handlers.get(type).getUIActivity());
                handlers.get(type).replaceActivity(activity);
            } else {
                handlers.put(type, new ClientHandler(activity));
            }
        }
    }

    public void removeType(String type){
        synchronized (handlers){
            if(handlers.containsKey(type)){
                handlers.remove(type);
            }
        }
    }
}
