package network2;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;
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
        printErrorMessage(s);
    }

    @Override
    public void onStopDiscoveryFailed(String s, int i) {
        printErrorMessage(s);
    }

    @Override
    public void onDiscoveryStarted(String s) {
        printStatusMessage(s);
    }

    @Override
    public void onDiscoveryStopped(String s) {
        printStatusMessage(s);
    }

    @Override
    public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
        this.manager.resolveService(nsdServiceInfo, new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {
                printErrorMessage("Failed to resolve service!");
            }

            @Override
            public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
                printStatusMessage("Successfully resolved service!");
                Log.d("Game-", nsdServiceInfo.toString());
                activity.runOnUiThread(() -> {
                    activity.addHost(nsdServiceInfo);
                });
            }
        });
        printStatusMessage("A new service has been found!");
        Log.d("Client-HostList", "I have found a new service!");
    }

    @Override
    public void onServiceLost(NsdServiceInfo nsdServiceInfo) {
        Log.d("Client-HostList", "I have lost a service!");
        printStatusMessage("A new service has been lost!");
        activity.runOnUiThread(() -> {
            this.activity.removeHost(nsdServiceInfo);
        });
    }
}
