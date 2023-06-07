package network2;

import android.content.Context;
import android.net.nsd.NsdManager;

public class NetworkServiceDiscoveryClient {

    private final Context context;
    private final NsdManager nsdManager;
    private final String serviceType;
    private NsdManager.DiscoveryListener discoveryListener;

    public NetworkServiceDiscoveryClient(Context context, String serviceType) {
        this.context = context;
        this.serviceType = serviceType;
        nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    public void startDiscovery(DiscoveryListener listener) {
        stopDiscovery();
        listener.setManager(nsdManager);
        discoveryListener = listener;
        nsdManager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void stopDiscovery() {
        if (discoveryListener != null) {
            nsdManager.stopServiceDiscovery(discoveryListener);
            discoveryListener = null;
        }
    }
}
