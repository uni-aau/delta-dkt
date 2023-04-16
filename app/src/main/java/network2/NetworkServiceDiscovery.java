package network2;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager.RegistrationListener;

public class NetworkServiceDiscovery {

    private static final String SERVICE_NAME = "_delta-dkt";

    private static final String SERVICE_PROTOCOLL ="_tcp";
    private static final String SERVICE_TYPE = SERVICE_NAME+"."+SERVICE_PROTOCOLL; // service type to be registered
    private static final String TAG = NetworkServiceDiscovery.class.getSimpleName();

    private NsdManager nsdManager;

    private RegistrationListener registrationListener;

    public NsdServiceInfo nsdServiceInfo;



    public NetworkServiceDiscovery(Context context) {
        this.nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);

        initializeRegistrationListener();
    }

    public void registerService(int port) {
        nsdServiceInfo = new NsdServiceInfo();
        nsdServiceInfo.setServiceName(SERVICE_NAME);
        nsdServiceInfo.setServiceType(SERVICE_TYPE);
        nsdServiceInfo.setPort(port);
        nsdManager.registerService(nsdServiceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
    }

    public void tearDown() {
        if (nsdManager != null) {
            nsdManager.unregisterService(registrationListener);
            registrationListener = null;
            nsdManager = null;
        }
    }

    private void initializeRegistrationListener() {
        registrationListener = new NsdManager.RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo serviceInfo) {
                System.out.println(TAG+" onServiceRegistered: " + serviceInfo);
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                System.out.println(TAG+" onRegistrationFailed: " + serviceInfo + ", errorCode: " + errorCode);
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
                System.out.println(TAG+" onServiceUnregistered: "  + serviceInfo);
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                System.out.println(TAG+" onUnregistrationFailed: " +serviceInfo + ", errorCode: " + errorCode);
            }
        };
    }

}
