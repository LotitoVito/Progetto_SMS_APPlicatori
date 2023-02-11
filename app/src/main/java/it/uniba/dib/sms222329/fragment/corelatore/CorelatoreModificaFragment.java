package it.uniba.dib.sms222329.fragment.corelatore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.database.Database;

public class CorelatoreModificaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;

    //View Items
    private TextInputEditText nome;
    private TextInputEditText cognome;
    private TextInputEditText mail;
    private TextInputEditText password;
    private TextInputEditText codFisc;
    private TextInputEditText org;
    private Button editButton;

    public CorelatoreModificaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_corelatore_modifica, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        FillAllEmpty();

        editButton.setOnClickListener(view -> {
            FillAllEmpty();

            if (Utility.coRelatoreLoggato.modCoRelatore(nome.getText().toString(), cognome.getText().toString(), mail.getText().toString(),
                    password.getText().toString(), codFisc.getText().toString(), org.getText().toString(), db)){
                Toast.makeText(getActivity().getApplicationContext(),R.string.modifica_successo,Toast.LENGTH_SHORT).show();
                Utility.goBack(getActivity());
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        nome = getActivity().findViewById(R.id.nome);
        cognome = getActivity().findViewById(R.id.cognome);
        mail = getActivity().findViewById(R.id.email);
        password = getActivity().findViewById(R.id.password);
        codFisc = getActivity().findViewById(R.id.codiceFiscale);
        org = getActivity().findViewById(R.id.organizzazione);
        editButton = getActivity().findViewById(R.id.conferma);
    }

    /**
     * Riempie i campi vuoti con il giusto valore se sono vuoti
     */
    private void FillAllEmpty(){
        Utility.fillIfEmpty(nome, Utility.coRelatoreLoggato.getNome());
        Utility.fillIfEmpty(cognome, Utility.coRelatoreLoggato.getCognome());
        Utility.fillIfEmpty(mail, Utility.coRelatoreLoggato.getEmail());
        Utility.fillIfEmpty(password, Utility.coRelatoreLoggato.getPassword());
        Utility.fillIfEmpty(codFisc, Utility.coRelatoreLoggato.getCodiceFiscale());
        Utility.fillIfEmpty(org, Utility.coRelatoreLoggato.getOrganizzazione());
    }
}