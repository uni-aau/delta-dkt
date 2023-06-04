package delta.dkt.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ClientUIHandling.Config;
import delta.dkt.R;
import delta.dkt.logic.structure.PropertyListElement;

public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.CustomViewHolder> {
    private final ArrayList<PropertyListElement> propListElement;
    private final Context context;

    public PropertyListAdapter(ArrayList<PropertyListElement> list, Context context) {
        this.propListElement = list;
        this.context = context;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_property_element, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        TextView propNumber = holder.layoutView.findViewById(R.id.textview_propNumber);
        TextView propName = holder.layoutView.findViewById(R.id.textView_propName);
        TextView price = holder.layoutView.findViewById(R.id.textView_price);
        TextView propRent = holder.layoutView.findViewById(R.id.textView_propRent);
        TextView ownedBy = holder.layoutView.findViewById(R.id.textView_ownedBy);
        TextView housesAmountView = holder.layoutView.findViewById(R.id.textView_propertiesAmountTitle);
        int housesAmount = propListElement.get(position).getHousesAmount();

        propName.setText(String.format(context.getString(R.string.text_propName), propListElement.get(position).getPropName()));
        propNumber.setText(propListElement.get(position).getPropNumber());
        price.setText(String.format(context.getString(R.string.text_price), propListElement.get(position).getPropPrice()));
        propRent.setText(String.format(context.getString(R.string.text_rent), propListElement.get(position).getPropRent()));
        ownedBy.setText(String.format(context.getString(R.string.text_ownedBy), propListElement.get(position).getPropOwner()));
        housesAmountView.setText(String.format(context.getString(R.string.text_housesAmount), housesAmount));

        setPropertyAmount(holder, housesAmount);
    }

    private void setPropertyAmount(CustomViewHolder holder, int propPlotAmountInput) {
        if (propPlotAmountInput > Config.MAX_HOUSES)
            throw new IllegalArgumentException("Too much inserted houses!");

        ImageView[] houses = new ImageView[]{
                holder.layoutView.findViewById(R.id.imageView_prop1),
                holder.layoutView.findViewById(R.id.imageView_prop2),
                holder.layoutView.findViewById(R.id.imageView_prop3),
                holder.layoutView.findViewById(R.id.imageView_prop4)
        };

        for (int i = 0; i < propPlotAmountInput; i++) {
            houses[i].setVisibility(View.VISIBLE);
        }
        if (propPlotAmountInput <= 0) {
            ((TextView) holder.layoutView.findViewById(R.id.textView_propertiesAmountTitle)).setText(String.format(context.getString(R.string.text_housesAmount), 0));
        }
    }

    @Override
    public int getItemCount() {
        return propListElement.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private final View layoutView;

        public CustomViewHolder(View layoutView) {
            super(layoutView);
            this.layoutView = layoutView;
        }
    }
}
