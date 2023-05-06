package delta.dkt.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import delta.dkt.R;

public class HostAdapter extends RecyclerView.Adapter<HostAdapter.HostViewHolder> {

    Context context;
    ArrayList<String> hostId;
    public static View selectedView;
    private int position;

    public HostAdapter (Context context, ArrayList<String> hostId) {
        this.context=context;
        this.hostId= hostId;
    }

    @NonNull
    @Override
    public HostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_find_host, parent, false);
        return new HostAdapter.HostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostViewHolder holder, int position) {
        holder.hostId.setText(hostId.get(position));
        this.position = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return hostId.size();
    }

    class HostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Grabs informations thats needed from the recycler_view_find_host.xml
        TextView hostId;
        public HostViewHolder (@NonNull View itemView) {
            super(itemView);
            hostId = itemView.findViewById(R.id.hostId_txt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String selectedHost = hostId.getText().toString();
            view.findViewById(R.id.recViewFindHost).setBackgroundResource(R.drawable.host_background);

            if(selectedView == null){
                selectedView = view;
            }else{
                selectedView.findViewById(R.id.recViewFindHost).setBackgroundResource(R.drawable.user_card_background);
                selectedView = view;
            }

            FindHostViewActivity.joinButton.setVisibility(View.VISIBLE);

            FindHostViewActivity findHostViewActivity = (FindHostViewActivity) context;
            findHostViewActivity.setSelectedHost(position);

            Toast.makeText(context, "Selected host: " + selectedHost, Toast.LENGTH_SHORT).show();
        }
    }
}
