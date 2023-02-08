package it.uniba.dib.sms222329.fragment.utente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.fragment.relatore.RelatoreModificaFragment;
import it.uniba.dib.sms222329.fragment.corelatore.CorelatoreModificaFragment;
import it.uniba.dib.sms222329.fragment.tesista.TesistaModificaFragment;


public class UtenteProfiloFragment extends Fragment {

    //Variabili e Oggetti

    //View Items
    private TextView nome;
    private TextView cognome;
    private TextView codiceFiscale;
    private TextView email;
    private TextView ruoloTv;

    public UtenteProfiloFragment() {}

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

        if (Utility.accountLoggato == Utility.TESISTA){
            SetTextAll(Utility.tesistaLoggato, Utility.accountLoggato);
        } else if (Utility.accountLoggato == Utility.RELATORE){
            SetTextAll(Utility.relatoreLoggato, Utility.accountLoggato);
        } else if (Utility.accountLoggato == Utility.CORELATORE){
            SetTextAll(Utility.coRelatoreLoggato, Utility.accountLoggato);
        }

        Button button = getView().findViewById(R.id.modificaProfilo);
        button.setOnClickListener( view -> {
            if(Utility.accountLoggato == Utility.TESISTA){
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TesistaModificaFragment());
            } else if (Utility.accountLoggato == Utility.RELATORE){
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new RelatoreModificaFragment());
            }else if(Utility.accountLoggato == Utility.CORELATORE){
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new CorelatoreModificaFragment());
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        nome = getView().findViewById(R.id.tv_name);
        cognome = getView().findViewById(R.id.tv_surname);
        codiceFiscale = getView().findViewById(R.id.tv_tax_id);
        email = getView().findViewById(R.id.tv_email);
        ruoloTv = getView().findViewById(R.id.tv_role);
    }

    /**
     * Imposta il testo per ogni elemento della view in base ai casi
     */
    private void SetTextAll(UtenteRegistrato utente, int ruoloLoggato){
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