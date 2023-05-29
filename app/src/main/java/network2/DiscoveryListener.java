package network2;

import ClientUIHandling.Constants;
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
            Log.v(Constants.LOG_BACKTRACE, "There was an error when listeneing for servers!");
            SnackBarHandler.createSnackbar(v, message, 2000, true, "#ffffff", "#e77373").show();
        });
    }

    private void printStatusMessage(String message){
        this.activity.runOnUiThread(() -> {
            View v = activity.findViewById(R.id.joinbtn);
            Log.v(Constants.LOG_BACKTRACE, "There was an status when listeneing for servers! => "+message);
            SnackBarHandler.createSnackbar(v, message, 2000, true, "#ffffff", "#47964b").show();
        });
    }

    @Override
    public void onStartDiscoveryFailed(String s, int i) {
        Log.v(Constants.LOG_BACKTRACE, "The discorvery has failed when started!");
        printErrorMessage(s);
    }

    @Override
    public void onStopDiscoveryFailed(String s, int i) {
        Log.v(Constants.LOG_BACKTRACE, "The Discorvery has dailfed and stopped!");
        printErrorMessage(s);
    }

    @Override
    public void onDiscoveryStarted(String s) {
        Log.v(Constants.LOG_BACKTRACE, "Discorvery has been started!");
        printStatusMessage(s);
    }

    @Override
    public void onDiscoveryStopped(String s) {
        Log.v(Constants.LOG_BACKTRACE, "Discorvery has been stopped!");
        printStatusMessage(s);
    }

    @Override
    public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
        this.manager.resolveService(nsdServiceInfo, new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {
                Log.v(Constants.LOG_BACKTRACE, "Could not resovle a service!");
                printErrorMessage("Failed to resolve service!");
            }

            @Override
            public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
                Log.v(Constants.LOG_BACKTRACE, "Resolved a new service!");
                printStatusMessage("Successfully resolved service!");
                Log.d("Game-", nsdServiceInfo.toString());
                activity.runOnUiThread(() -> {
                    activity.addHost(nsdServiceInfo);
                });
            }
        });
        Log.v(Constants.LOG_BACKTRACE, "A new service has been found by NSD!");
        printStatusMessage("A new service has been found!");
        Log.d("Client-HostList", "I have found a new service!");
    }

    @Override
    public void onServiceLost(NsdServiceInfo nsdServiceInfo) {
        Log.d("Client-HostList", "I have lost a service!");
        printStatusMessage("A new service has been lost!");
        activity.runOnUiThread(() -> this.activity.removeHost(nsdServiceInfo));
    }
}
