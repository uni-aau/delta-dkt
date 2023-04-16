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

    public void startDiscovery(final OnDiscoveryListener listener) {
        stopDiscovery();
        discoveredServices.clear();
        discoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                handler.post(() -> listener.onStartDiscoveryFailed(serviceType, errorCode));
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                handler.post(() -> listener.onStopDiscoveryFailed(serviceType, errorCode));
            }

            @Override
            public void onDiscoveryStarted(String serviceType) {
                handler.post(listener::onDiscoveryStarted);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                handler.post(listener::onDiscoveryStopped);
            }

            @Override
            public void onServiceFound(NsdServiceInfo serviceInfo) {
                if (serviceInfo.getServiceType().equals(serviceType)) {
                    discoveredServices.add(serviceInfo);
                    handler.post(() -> listener.onServiceFound(serviceInfo));
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo serviceInfo) {
                discoveredServices.remove(serviceInfo);
                handler.post(() -> listener.onServiceLost(serviceInfo));
            }
        };
        nsdManager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
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


    /**
     *      In the activity class
     *
     *     private NetworkServiceDiscoveryClient nsdClient;
     *
     *     @Override
     *     protected void onCreate(Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         setContentView(R.layout.activity_main);
     *
     *         // Initialize the NSD client
     *         nsdClient = new NetworkServiceDiscoveryClient(this);
     *
     *         // Start the NSD discovery
     *         nsdClient.startDiscovery(new NsdManager.DiscoveryListener() {
     *             @Override
     *             public void onStartDiscoveryFailed(String serviceType, int errorCode) {
     *                 Log.e("NSD", "onStartDiscoveryFailed: " + errorCode);
     *             }
     *
     *             @Override
     *             public void onStopDiscoveryFailed(String serviceType, int errorCode) {
     *                 Log.e("NSD", "onStopDiscoveryFailed: " + errorCode);
     *             }
     *
     *             @Override
     *             public void onDiscoveryStarted(String serviceType) {
     *                 Log.d("NSD", "onDiscoveryStarted");
     *             }
     *
     *             @Override
     *             public void onDiscoveryStopped(String serviceType) {
     *                 Log.d("NSD", "onDiscoveryStopped");
     *             }
     *
     *             @Override
     *             public void onServiceFound(NsdServiceInfo serviceInfo) {
     *                 Log.d("NSD", "onServiceFound: " + serviceInfo.getServiceName());
     *             }
     *
     *             @Override
     *             public void onServiceLost(NsdServiceInfo serviceInfo) {
     *                 Log.d("NSD", "onServiceLost: " + serviceInfo.getServiceName());
     *             }
     *         });
     *     }
     *
     *     @Override
     *     protected void onDestroy() {
     *         super.onDestroy();
     *
     *         // Stop the NSD discovery
     *         nsdClient.stopDiscovery();
     *     }
     * }
     */

}
