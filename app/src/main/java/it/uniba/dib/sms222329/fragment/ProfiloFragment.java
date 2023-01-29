package it.uniba.dib.sms222329.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.relatore.ModificaProfiloRelatoreFragment;


public class ProfiloFragment extends Fragment {


    private Database db;
    private Relatore relatoreLoggato;
    private CoRelatore corelatoreLoggato;
    private Tesista tesistaLoggato;

    public ProfiloFragment(Database db, Relatore relatoreLoggato) {
        this.db = db;
        this.relatoreLoggato = relatoreLoggato;
    }

    public ProfiloFragment(Database db, CoRelatore corelatoreLoggato) {
        this.db = db;
        this.corelatoreLoggato = corelatoreLoggato;
    }

    public ProfiloFragment(Database db, Tesista tesistaLoggato) {
        this.db = db;
        this.tesistaLoggato = tesistaLoggato;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profilo, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Button button = getView().findViewById(R.id.modificaProfilo);
        button.setOnClickListener( view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ModificaProfiloRelatoreFragment(db, relatoreLoggato));
        });
    }
}