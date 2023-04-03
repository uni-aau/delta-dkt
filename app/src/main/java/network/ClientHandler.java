package network;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.logging.LogRecord;

public class ClientHandler extends Handler {

    @Override
    public void handleMessage(@NonNull Message msg) {
        if(msg.getData().containsKey("MODIFYTEST")){
            //TODO: Modify some UI Element
        }
    }
}
