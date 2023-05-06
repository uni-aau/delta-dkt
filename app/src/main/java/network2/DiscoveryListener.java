package network2;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.view.View;

import ClientUIHandling.handlers.notifications.SnackBarHandler;
import delta.dkt.R;
import delta.dkt.activities.FindHostViewActivity;

public class DiscoveryListener implements NsdManager.DiscoveryListener{
    private FindHostViewActivity activity;
    private NsdManager manager;

    public DiscoveryListener(FindHostViewActivity activity){
        this.activity = activity;
    }

    public void setManager(NsdManager manager){
        this.manager = manager;
    }

    private void printErrorMessage(String message){
        this.activity.runOnUiThread(() -> {
            View v = activity.findViewById(R.id.joinbtn);
            SnackBarHandler.createSnackbar(v, message, 2000, true, "#ffffff", "#e77373").show();
        });
    }

    private void printStatusMessage(String message){
        this.activity.runOnUiThread(() -> {
            View v = activity.findViewById(R.id.joinbtn);
            SnackBarHandler.createSnackbar(v, message, 2000, true, "#ffffff", "#47964b").show();
        });
    }

    @Override
    public void onStartDiscoveryFailed(String s, int i) {

    }

    @Override
    public void onStopDiscoveryFailed(String s, int i) {

    }

    @Override
    public void onDiscoveryStarted(String s) {

    }

    @Override
    public void onDiscoveryStopped(String s) {

    }

    @Override
    public void onServiceFound(NsdServiceInfo nsdServiceInfo) {

    }

    @Override
    public void onServiceLost(NsdServiceInfo nsdServiceInfo) {

    }
}
