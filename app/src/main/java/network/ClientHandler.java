package network;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.logging.LogRecord;

import delta.dkt.R;

public class ClientHandler extends Handler {

    private TextView testView;

    public static final String testType = "MODIFYTEST";

    public ClientHandler(TextView testView){
        this.testView = testView;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        if(msg.getData().containsKey(testType)){
            testView.setText(msg.getData().get(testType).toString());
            //TODO: Modify some UI Element
        }
    }
}
