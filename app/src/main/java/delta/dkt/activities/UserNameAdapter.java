package delta.dkt.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class UserNameAdapter extends RecyclerView.Adapter<UserNameAdapter.UserViewHolder> {

    Context context;
    ArrayList <String> usernames = new ArrayList<>();

    public UserNameAdapter (Context context, ArrayList<String> usernames) {
        this.context=context;
        this.usernames= usernames;
    }







    @NonNull
    @Override
    public UserNameAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserNameAdapter.UserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
