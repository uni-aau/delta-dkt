package network2;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

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
