package delta.dkt.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import delta.dkt.R;

import java.util.ArrayList;

public class CheatUserAdapter extends RecyclerView.Adapter<CheatUserAdapter.CheatUserViewHolder> {
    private ArrayList<String> playerNames;
    private ArrayList<Integer> figureIdentifiers = new ArrayList<>();

    public CheatUserAdapter(ArrayList<String> playerNames) {
        this.playerNames = playerNames;

        figureIdentifiers.add(R.drawable.circle_blue);
        figureIdentifiers.add(R.drawable.circle_purple);
        figureIdentifiers.add(R.drawable.circle_green);
        figureIdentifiers.add(R.drawable.circle_orange);
        figureIdentifiers.add(R.drawable.circle_pink);
        figureIdentifiers.add(R.drawable.circle_red);
    }

    @NonNull
    @Override
    public CheatUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.report_cheat_popup_user, parent, false);
        return new CheatUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheatUserViewHolder holder, int position) {
        holder.username.setText(playerNames.get(position));
        holder.userFigure.setImageResource(figureIdentifiers.get(position));
    }


    @Override
    public int getItemCount() {
        return playerNames.size();
    }


    class CheatUserViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        ImageView userFigure;

        public CheatUserViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.lblCheatUserName);
            userFigure = itemView.findViewById(R.id.imgCheatUser);
        }
    }


}
