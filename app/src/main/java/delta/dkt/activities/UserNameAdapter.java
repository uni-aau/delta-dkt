package delta.dkt.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import delta.dkt.R;

class UserNameAdapter extends RecyclerView.Adapter<UserNameAdapter.UserViewHolder> {

    Context context;
    ArrayList<String> usernames;

    public UserNameAdapter (Context context, ArrayList<String> usernames) {
        this.context=context;
        this.usernames= usernames;
    }



    class UserViewHolder extends RecyclerView.ViewHolder {
        // Grabs informations thats needed from the recycler_view_user_row.xml

        TextView tv_username;

        public UserViewHolder (@NonNull View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.userName_txt);
        }
    }






    @NonNull
    @Override
    public UserNameAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This method inflates the layout, meaning giving a look to our rows
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserNameAdapter.UserViewHolder holder, int position) {
        // assigning values to the views that come back on the screen again (in the recycler view layout file)
        // based on the position of the recycler view


    }

    @Override
    public int getItemCount() {
        // this method just returns the number of items you want to display
        // now its 1 because of testing purpose as we only have 1 user at the moment
        return usernames.size();
    }
}