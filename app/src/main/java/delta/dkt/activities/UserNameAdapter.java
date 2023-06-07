package delta.dkt.activities;

import static delta.dkt.activities.MainMenuActivity.getTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import delta.dkt.R;

class UserNameAdapter extends RecyclerView.Adapter<UserNameAdapter.UserViewHolder> {

    ArrayList<String> usernames;

    public UserNameAdapter (ArrayList<String> usernames) {
        this.usernames= usernames;
    }

    @NonNull
    @Override
    public UserNameAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This method inflates the layout, meaning giving a look to our rows
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_user_row, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserNameAdapter.UserViewHolder holder, int position) {
        // assigning values to the views that come back on the screen again (in the recycler view layout file)
        // based on the position of the recycler view

        holder.username.setText(usernames.get(position));

        if (position == 0) holder.userRole.setText(R.string.Host);
        else holder.userRole.setText(R.string.Player);


        holder.joiningTime.setText("Joined: "+ getTime());

    }

    @Override
    public int getItemCount() {
        // this method just returns the number of items you want to display
        return usernames.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        // Grabs informations thats needed from the recycler_view_user_row.xml

        TextView username;
        TextView userRole;
        TextView joiningTime;

        public UserViewHolder (@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName_txt);
            userRole = itemView.findViewById(R.id.userRole);
            joiningTime = itemView.findViewById(R.id.joiningTime);
        }
    }


}
