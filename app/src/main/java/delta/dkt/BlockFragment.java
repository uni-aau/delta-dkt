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

    private String fragmentNumberInput;
    private String propNumberInput;

    public BlockFragment() {
        // Required empty public constructor
    }

    public static BlockFragment newInstance(String param1, String param2) {
        BlockFragment fragment = new BlockFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FRAGMENT_NUMBER, param1);
        args.putString(ARG_PROP_NUMBER, param2);
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