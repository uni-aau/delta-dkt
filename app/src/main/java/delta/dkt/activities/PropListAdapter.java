package delta.dkt.activities;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PropListAdapter extends RecyclerView.Adapter<PropListAdapter.CustomViewHolder> {
    private ArrayList<String> list;

    public PropListAdapter(ArrayList<String> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private View layoutView;

        public CustomViewHolder(View layoutView) {
            super(layoutView);
            this.layoutView = layoutView;
        }

        public View getLayoutView() {
            return layoutView;
        }
    }
}
