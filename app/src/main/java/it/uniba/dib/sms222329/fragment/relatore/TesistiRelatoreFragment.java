package it.uniba.dib.sms222329.fragment.relatore;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.uniba.dib.sms222329.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TesistiRelatoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TesistiRelatoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TesistiRelatoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TesistiRelatoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TesistiRelatoreFragment newInstance(String param1, String param2) {
        TesistiRelatoreFragment fragment = new TesistiRelatoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tesisti_relatore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView richieste = getActivity().findViewById(R.id.richieste);
        TextView tesisti = getActivity().findViewById(R.id.tesisti);

        View u_richieste = getActivity().findViewById(R.id.u_richieste);
        View u_tesisti = getActivity().findViewById(R.id.u_tesisti);

        int defaultColor = richieste.getTextColors().getDefaultColor();
        int u_defaultColor = view.getDrawingCacheBackgroundColor();

        richieste.setTextColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));
        u_richieste.setBackgroundColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));

        richieste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tesisti.setTextColor(defaultColor);
                richieste.setTextColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));
                u_richieste.setBackgroundColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));
                u_tesisti.setBackgroundColor(u_defaultColor);
            }
        });

        tesisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tesisti.setTextColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));
                richieste.setTextColor(defaultColor);
                u_tesisti.setBackgroundColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));
                u_richieste.setBackgroundColor(u_defaultColor);
            }
        });

    }
}