package it.uniba.dib.sms222329.fragment.relatore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.activities.UtenteLoggato;
import it.uniba.dib.sms222329.database.Database;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TesiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TesiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TesiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TesiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TesiFragment newInstance(String param1, String param2) {
        TesiFragment fragment = new TesiFragment();
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
        View view = inflater.inflate(R.layout.fragment_tesi_relatore, container, false);

        // Get a reference to the button
        FloatingActionButton addButton = view.findViewById(R.id.aggiungiTesi);
        Button editButton = view.findViewById(R.id.modificaTesi);

        // Set up a click listener for the button
        addButton.setOnClickListener(view1 -> {
            Fragment fragment = new GestioneTesiFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
        });

        editButton.setOnClickListener(view1 -> {
            Fragment fragment = new GestioneTesiFragment(); //TODO Passare oggetto con tesi selezionata
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
        });

        // Return the view hierarchy
        return view;
    }


}