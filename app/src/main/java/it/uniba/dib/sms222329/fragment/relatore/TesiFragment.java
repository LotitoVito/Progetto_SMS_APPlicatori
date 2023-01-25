package it.uniba.dib.sms222329.fragment.relatore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.ListaTesi;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.VisualizzaTesiFragment;

public class TesiFragment extends Fragment {
    Relatore relatoreLoggato;

    public TesiFragment(){}
    public TesiFragment(Relatore relatoreLoggato) {
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tesi_relatore, container, false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if(selectedItemId != R.id.navigation_thesis) {
            bottomNavigationView.getMenu().findItem(R.id.navigation_thesis).setChecked(true);
        }

        // Return the view hierarchy
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedIstanceState){
        super.onViewCreated(view, savedIstanceState);

        // Get a reference to the button
        FloatingActionButton addButton = view.findViewById(R.id.aggiungiTesi);

        // Set up a click listener for the button
        addButton.setOnClickListener(view1 -> Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new GestioneTesiFragment(relatoreLoggato)));

        ListView listView = getActivity().findViewById(R.id.tesiList);

        ListaTesi lista = new ListaTesi(new Database(getActivity().getApplicationContext()));
        Database db = new Database(getContext());
        List<Tesi> listaTesi = lista.vincoloRelatore(relatoreLoggato.getIdRelatore(), db);
        ListaTesiAdapter adapterLista = new ListaTesiAdapter(getActivity().getApplicationContext(), listaTesi, getActivity().getSupportFragmentManager(), relatoreLoggato);
        listView.setAdapter(adapterLista);
    }

}