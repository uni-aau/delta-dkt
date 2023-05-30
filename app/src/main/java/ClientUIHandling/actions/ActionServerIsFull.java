package ClientUIHandling.actions;

import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.MainMenuActivity;

public class ActionServerIsFull implements ClientActionInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Toast.makeText(activity, "SERVER IS FULL!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(activity, MainMenuActivity.class);
        intent.putExtra(INTENT_PARAMETER, MainMenuActivity.username);
        activity.startActivity(intent);
    }
}
