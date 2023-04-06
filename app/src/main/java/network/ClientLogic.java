package network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class ClientLogic {

    private Handler handler;

    public ClientLogic(Handler handler) {
        this.handler = handler;
    }

    public void setTextOfTestElement(String text){
        sendHandle(text, ClientHandler.testType);
    }

    public void sendHandle(String message, String type) {

        android.os.Message handleMessage = new Message();
        Bundle b = new Bundle();
        b.putString(type, message);
        handleMessage.setData(b);
        handler.sendMessage(handleMessage);
    }
}
