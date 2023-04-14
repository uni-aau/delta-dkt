package ClientUIHandling;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
        }
    }

    public TextView getTestView() {
        return testView;
    }
}
