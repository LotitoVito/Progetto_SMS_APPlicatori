package it.uniba.dib.sms222329.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.relatore.ModificaProfiloRelatoreFragment;
import it.uniba.dib.sms222329.fragment.signUp.ModificaProfiloCorelatoreFragment;
import it.uniba.dib.sms222329.fragment.signUp.ModificaProfiloStudenteFragment;


public class ProfiloFragment extends Fragment {

    private Database db;
    private Relatore relatoreLoggato;
    private CoRelatore corelatoreLoggato;
    private Tesista tesistaLoggato;
    private int ruolo;

    public ProfiloFragment(Database db, int ruolo, Relatore relatoreLoggato) {
        this.db = db;
        this.ruolo = ruolo;
        this.relatoreLoggato = relatoreLoggato;
    }

    public ProfiloFragment(Database db, int ruolo, CoRelatore corelatoreLoggato) {
        this.db = db;
        this.ruolo = ruolo;
        this.corelatoreLoggato = corelatoreLoggato;
    }

    public ProfiloFragment(Database db, int ruolo, Tesista tesistaLoggato) {
        this.db = db;
        this.ruolo = ruolo;
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

        if (ruolo == Utility.TESISTA){
            SetView(tesistaLoggato, ruolo);
        } else if (ruolo == Utility.RELATORE){
            SetView(relatoreLoggato, ruolo);
        } else if (ruolo == Utility.CORELATORE){
            SetView(corelatoreLoggato, ruolo);
        }

        Button button = getView().findViewById(R.id.modificaProfilo);
        button.setOnClickListener( view -> {
            if(ruolo == Utility.TESISTA){
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ModificaProfiloStudenteFragment(db, tesistaLoggato));
            } else if (ruolo == Utility.RELATORE){
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ModificaProfiloRelatoreFragment(db, relatoreLoggato));
            }else if(ruolo == Utility.CORELATORE){
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ModificaProfiloCorelatoreFragment(db, corelatoreLoggato));
            }
        });
    }

    private void SetView(UtenteRegistrato utente, int ruoloLoggato){
        TextView nome = getView().findViewById(R.id.tv_name);
        TextView cognome = getView().findViewById(R.id.tv_surname);
        TextView codiceFiscale = getView().findViewById(R.id.tv_tax_id);
        TextView email = getView().findViewById(R.id.tv_email);
        TextView ruolo = getView().findViewById(R.id.tv_role);

        nome.setText(utente.getNome());
        cognome.setText(utente.getCognome());
        codiceFiscale.setText(utente.getCodiceFiscale());
        email.setText(utente.getEmail());
        if(ruoloLoggato == Utility.TESISTA){
            ruolo.setText("Tesista");
        } else if (ruoloLoggato == Utility.RELATORE){
            ruolo.setText("Relatore");
        }else if(ruoloLoggato == Utility.CORELATORE){
            ruolo.setText("Corelatore");
        }
    }
}