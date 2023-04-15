package network2; 

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.ResolveListener;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ClientUIHandling.ClientLogic;


/**
 * This class can be introduced as wrapper class for a network connection specifically for client connections (server side)
 * It may store some other client specific attributes like:
 *  - a game participant (a player object that has its own properties, (money-) account etc.
 *  - a nickname ?
 *  - the network connection object
 *  - etc..
 */
public class NetworkClientConnection {

    private NetworkConnection connection;

    private ClientLogic logic;

    /**

    ResolveListener resolveListener;

    NsdManager.DiscoveryListener discoveryListener;

    Context context;

    String serviceName = "delta_dkt";

    String serviceType = "_delta_dkt.tcp";

    NsdManager nsdManager;

    NsdServiceInfo mService;

    List<NsdServiceInfo> foundServices;

    int serverPort;
    InetAddress host;


    public NetworkClientConnection(Context context){
        this.context = context;
        nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);

       // initializeDiscoveryListener();

        //foundServices = new ArrayList<NsdServiceInfo>();

        //startDiscovery();


        //once we picked a discovered service from our list, we need to resolve the service
        //for this procedure we need to initialize a resolveListener Callback Object
        //initializeResolveListener();

    }
     */


    public NetworkClientConnection(String ip, int port, int timeout, ClientLogic logic) {

        this.connection = new NetworkConnection(ip,  port, timeout,logic);
        this.logic = logic;
    }

    public void start(){
        this.connection.start();
    }

    public void sendMessage(String msg) {
         connection.send(msg);
    }

    public void stopConnection() throws Exception {
        connection.close();
    }

    public NetworkConnection getConnection() {
        return connection;
    }

    /**
     * NSD Discovery area
     */
    /**
    public void resolveService(int pos){
        nsdManager.resolveService(foundServices.get(0),resolveListener);

    }

    public void startDiscovery(){
        //we tell the manager to start discovering services
        nsdManager.discoverServices(this.serviceType
                , NsdManager.PROTOCOL_DNS_SD, this.discoveryListener);
    }

    public void tearDown(){
        if (nsdManager != null) {
            stopServiceDiscovery();
        }
    }

    private void stopServiceDiscovery(){
        nsdManager.stopServiceDiscovery(discoveryListener);
    }

    private void initializeDiscoveryListener() {

        // Instantiate a new DiscoveryListener
        this.discoveryListener = new NsdManager.DiscoveryListener() {

            // Called as soon as service discovery begins.
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d("service_discovery_started", "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                // A service was found! Do something with it.
                Log.d("service_found", "Service discovery success" + service);
                if (!service.getServiceType().equals(serviceType)) {
                    // Service type is the string containing the protocol and
                    // transport layer for this service.
                    Log.d("unknown_service_type", "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(serviceName)) {
                    // The name of the service tells the user what they'd be
                    // connecting to. It could be "Bob's Chat App".
                    Log.d("service_same_machine", "Same machine: " + serviceName);
                } else if(foundServices.contains(service) == false){
                    foundServices.add(service);
                    Log.d("add_found_service_to_list","Added a new service ("+service.getServiceName()+") to the list of results");
                }
                else{
                    Log.d("onServiceFound_fallback","We have fallen back at case distinguishment. Do something.. Discovered Service: "+service.toString()+"\n ResultList:"+foundServices.toString());
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                // When the network service is no longer available.
                // Internal bookkeeping code goes here.
                Log.e("initializeDiscoveryListener_onserviceLost", "service lost: " + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i("initializeDiscoveryListener_onDiscoveryStopped", "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e("initializeDiscoveryListener_onStartDiscoveryFailed", "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e("initializeDiscoveryListener_onStopDiscoveryFailed", "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }
        };
    }
    */
    /**
     * Initializes the resolve callback object instance var and also sets additional port and host members (they might be removed in future)
     */
    /**
    private void initializeResolveListener() {
        this.resolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails. Use the error code to debug.
                Log.e("resolve_failed", "Resolve failed: " + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e("service_resolved", "Resolve Succeeded. " + serviceInfo);

                if (serviceInfo.getServiceName().equals(serviceName)) {
                    Log.d("same_ip", "Same IP.");
                    return;
                }

                mService = serviceInfo;
                serverPort = mService.getPort();
                host = mService.getHost();
            }
        };
    }
   */

}
