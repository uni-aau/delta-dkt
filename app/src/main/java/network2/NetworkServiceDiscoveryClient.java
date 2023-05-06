package network2;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

public class NetworkServiceDiscoveryClient {

    private final Context context;
    private final NsdManager nsdManager;
    private final String serviceType;
    private final List<NsdServiceInfo> discoveredServices = new ArrayList<>();
    private NsdManager.DiscoveryListener discoveryListener;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public NetworkServiceDiscoveryClient(Context context, String serviceType) {
        this.context = context;
        this.serviceType = serviceType;
        nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    public void startDiscovery(DiscoveryListener listener) {
        stopDiscovery();
        discoveredServices.clear();
        discoveryListener = listener;
    }

    public void stopDiscovery() {
        if (discoveryListener != null) {
            nsdManager.stopServiceDiscovery(discoveryListener);
            discoveryListener = null;
        }
    }

    public List<NsdServiceInfo> getDiscoveredServices() {
        return discoveredServices;
    }

    public interface OnDiscoveryListener {
        void onStartDiscoveryFailed(String serviceType, int errorCode);
        void onStopDiscoveryFailed(String serviceType, int errorCode);
        void onDiscoveryStarted();
        void onDiscoveryStopped();
        void onServiceFound(NsdServiceInfo serviceInfo);
        void onServiceLost(NsdServiceInfo serviceInfo);
    }
}
