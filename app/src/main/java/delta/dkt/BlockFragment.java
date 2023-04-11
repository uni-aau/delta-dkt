package delta.dkt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private String propPriceInput;
    private String propOwnerInput;
    private String propPlotAmountInput;



    public BlockFragment() {
        // Required empty public constructor
    }

    public static BlockFragment newInstance(String fragmentNumber, String propNumber, String propName, String propPrice, String propOwner, String propPlotAmount) { // Todo convert to object?
        BlockFragment fragment = new BlockFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FRAGMENT_NUMBER, fragmentNumber);
        args.putString(ARG_PROP_NUMBER, propNumber);
        args.putString(ARG_PROP_NAME, propName);
        args.putString(ARG_PROP_PRICE, propPrice);
        args.putString(ARG_PROP_OWNER, propOwner);
        args.putString(ARG_PROP_PLOTAMOUNT, propPlotAmount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentNumberInput = getArguments().getString(ARG_FRAGMENT_NUMBER);
            propNumberInput = getArguments().getString(ARG_PROP_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_block, container, false);

        setFragmentData(view);

        return view;
    }

    protected void setFragmentData(View view) {
        TextView propNumber = view.findViewById(R.id.textview_propNumber);
        TextView propName = view.findViewById(R.id.textView_propName);
        TextView price = view.findViewById(R.id.textView_price);
        TextView ownedBy = view.findViewById(R.id.textView_ownedBy);

        propNumber.setText(propNumberInput);

        // Todo
        /*
        Button zum Zurückführen
        Scrollbar
        Property Haus-Implementierung
         */
    }
}