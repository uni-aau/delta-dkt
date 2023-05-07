package delta.dkt.activities;


import static ClientUIHandling.Constants.PREFIX_ADD_USER_TO_LIST;

import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;
import static delta.dkt.activities.MainActivity.user;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import ClientUIHandling.ClientHandler;
import ClientUIHandling.ClientLogic;
import ClientUIHandling.Constants;
import delta.dkt.R;
import network2.DiscoveryListener;
import network2.NetworkClientConnection;
import network2.NetworkServiceDiscoveryClient;


public class FindHostViewActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    HostAdapter adapter;
    //ArrayList<String> hostList;
    private ArrayList<NsdServiceInfo> hosts = new ArrayList<>();
    public static Button joinButton;
    private NsdServiceInfo selection = null;
    private static final String SERVICE_NAME = "_delta-dkt";
    private static final String SERVICE_PROTOCOLL ="_tcp";
    private static final String SERVICE_TYPE = SERVICE_NAME+"."+SERVICE_PROTOCOLL;


    public void setSelectedHost(int hostId) {
        selection = hosts.get(hostId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_host_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button backButton = findViewById(R.id.backbtn);
        joinButton = findViewById(R.id.joinbtn);
        String newUser = getIntent().getStringExtra(INTENT_PARAMETER);

        NetworkServiceDiscoveryClient discoveryClient = new NetworkServiceDiscoveryClient(this, SERVICE_TYPE);

        discoveryClient.startDiscovery(new DiscoveryListener(this));

        Log.d("Game-", "Discovery has been started!");

        updateHostList();
        //hostList = new ArrayList<>();

        //hostList.add("Host1");
        //hostList.add("Host2");
        //hostList.add("Host3");

        //recyclerView = findViewById(R.id.RecyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new HostAdapter(this, hostList);
        //recyclerView.setAdapter(adapter);


        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            startActivity(intent);
        });

        joinButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LobbyViewActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            if(selection != null){
                NetworkClientConnection client = new NetworkClientConnection(selection.getHost().getHostAddress(), selection.getPort(), 1000, MainActivity.logic);
                client.start();
                ClientHandler.setClient(client);


                client.sendMessage(Constants.PREFIX_SERVER+":"+PREFIX_ADD_USER_TO_LIST+" "+user);
            }

            startActivity(intent);
        });

        MainActivity.subscribeToLogic(Constants.FIND_HOST_VIEW_ACTIVITY_TYPE, this);

    }

    public void addHost(NsdServiceInfo host){
        this.hosts.add(host);
        updateHostList();
    }

    public void removeHost(NsdServiceInfo host){
        this.hosts.remove(host);
        updateHostList();
    }

    private void updateHostList(){
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> hostNames = new ArrayList<>();

        this.hosts.forEach(h -> hostNames.add(h.getHost()+":"+h.getPort()));

        adapter = new HostAdapter(this, hostNames);
        recyclerView.setAdapter(adapter);
    }
}