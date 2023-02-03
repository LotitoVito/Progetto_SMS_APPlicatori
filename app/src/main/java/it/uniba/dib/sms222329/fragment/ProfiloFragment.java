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

    //Variabili e Oggetti
    private Database db;
    private Relatore relatoreLoggato;
    private CoRelatore corelatoreLoggato;
    private Tesista tesistaLoggato;
    private int ruolo;

    //View Items
    private TextView nome;
    private TextView cognome;
    private TextView codiceFiscale;
    private TextView email;
    private TextView ruoloTv;

    public ProfiloFragment(int ruolo, Relatore relatoreLoggato) {
        this.ruolo = ruolo;
        this.relatoreLoggato = relatoreLoggato;
    }

    public ProfiloFragment(int ruolo, CoRelatore corelatoreLoggato) {
        this.ruolo = ruolo;
        this.corelatoreLoggato = corelatoreLoggato;
    }

    public ProfiloFragment(int ruolo, Tesista tesistaLoggato) {
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
        return inflater.inflate(R.layout.fragment_profilo, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();

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
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ModificaProfiloStudenteFragment(tesistaLoggato));
            } else if (ruolo == Utility.RELATORE){
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ModificaProfiloRelatoreFragment(relatoreLoggato));
            }else if(ruolo == Utility.CORELATORE){
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ModificaProfiloCorelatoreFragment(corelatoreLoggato));
            }
        });
    }

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        nome = getView().findViewById(R.id.tv_name);
        cognome = getView().findViewById(R.id.tv_surname);
        codiceFiscale = getView().findViewById(R.id.tv_tax_id);
        email = getView().findViewById(R.id.tv_email);
        ruoloTv = getView().findViewById(R.id.tv_role);
    }

    private void SetView(UtenteRegistrato utente, int ruoloLoggato){
        nome.setText(utente.getNome());
        cognome.setText(utente.getCognome());
        codiceFiscale.setText(utente.getCodiceFiscale());
        email.setText(utente.getEmail());
        if(ruoloLoggato == Utility.TESISTA){
            ruoloTv.setText("Tesista");
        } else if (ruoloLoggato == Utility.RELATORE){
            ruoloTv.setText("Relatore");
        }else if(ruoloLoggato == Utility.CORELATORE){
            ruoloTv.setText("Corelatore");
        }
    }
}