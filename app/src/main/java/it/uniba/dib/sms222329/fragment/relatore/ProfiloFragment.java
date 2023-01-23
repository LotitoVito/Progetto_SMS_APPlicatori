package it.uniba.dib.sms222329.fragment.relatore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;

public class ProfiloFragment extends Fragment {
    private Relatore relatoreLoggato;

    public ProfiloFragment(Relatore relatoreLoggato) {
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if(selectedItemId != R.id.navigation_profile) {
            bottomNavigationView.getMenu().findItem(R.id.navigation_profile).setChecked(true);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profilo_relatore, container, false);
    }
}