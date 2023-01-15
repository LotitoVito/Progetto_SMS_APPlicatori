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
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.fragment.signUp.SignUpStudentFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TesiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TesiFragment extends Fragment {
    Relatore relatoreLoggato;

    public TesiFragment(Relatore relatoreLoggato) {
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tesi_relatore, container, false);

        // Get a reference to the button
        FloatingActionButton addButton = view.findViewById(R.id.aggiungiTesi);
        //Button editButton = view.findViewById(R.id.modificaTesi);

        // Set up a click listener for the button
        addButton.setOnClickListener(view1 -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.signUpcontainer, new GestioneTesiFragment(relatoreLoggato));
        });

        /*editButton.setOnClickListener(view1 -> {
            Fragment fragment = new GestioneTesiFragment(); //TODO Passare oggetto con tesi selezionata
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
        });*/

        // Return the view hierarchy
        return view;
    }


}