package delta.dkt.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import delta.dkt.R;

public class BlockFragment extends Fragment {

    private static final String ARG_FRAGMENT_NUMBER = "fragmentNumberInput";
    private static final String ARG_PROP_NUMBER = "propNumberInput";
    private static final String ARG_PROP_NAME = "propNameInput";
    private static final String ARG_PROP_PRICE = "propPriceInput";
    private static final String ARG_PROP_OWNER = "propOwnerInput";
    private static final String ARG_PROP_PLOTAMOUNT = "propPlotAmountInput";

    private String fragmentNumberInput;
    private String propNumberInput;
    private String propNameInput;
    private int propPriceInput;
    private String propOwnerInput;
    private int propPlotAmountInput;


    public BlockFragment() {
        // Required empty public constructor
    }

    public static BlockFragment newInstance(String fragmentNumber, String propNumber, String propName, int propPrice, String propOwner, int propPlotAmount) { // Todo convert to object?
        BlockFragment fragment = new BlockFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FRAGMENT_NUMBER, fragmentNumber);
        args.putString(ARG_PROP_NUMBER, propNumber);
        args.putString(ARG_PROP_NAME, propName);
        args.putInt(ARG_PROP_PRICE, propPrice);
        args.putString(ARG_PROP_OWNER, propOwner);
        args.putInt(ARG_PROP_PLOTAMOUNT, propPlotAmount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentNumberInput = getArguments().getString(ARG_FRAGMENT_NUMBER);
            propNumberInput = getArguments().getString(ARG_PROP_NUMBER);
            propNameInput = getArguments().getString(ARG_PROP_NAME);
            propPriceInput = getArguments().getInt(ARG_PROP_PRICE);
            propOwnerInput = getArguments().getString(ARG_PROP_OWNER);
            propPlotAmountInput = getArguments().getInt(ARG_PROP_PLOTAMOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_block, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFragmentData(view);
    }

    protected void setFragmentData(View view) {
        TextView propNumber = view.findViewById(R.id.textview_propNumber);
        TextView propName = view.findViewById(R.id.textView_propName);
        TextView price = view.findViewById(R.id.textView_price);
        TextView ownedBy = view.findViewById(R.id.textView_ownedBy);

        propNumber.setText(propNumberInput);
        propName.setText(String.format(getString(R.string.text_propName), propNameInput));
        price.setText(String.format(getString(R.string.text_price), propPriceInput));
        ownedBy.setText(String.format(getString(R.string.text_ownedBy), propOwnerInput));

        // Todo
        /*
        Property Haus-Implementierung
         */
    }
}