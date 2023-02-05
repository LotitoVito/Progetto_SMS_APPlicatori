package it.uniba.dib.sms222329.fragment.signUp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.CoRelatoreDatabase;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.ProfiloFragment;

public class ModificaProfiloCorelatoreFragment extends Fragment {

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

    public ModificaProfiloCorelatoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modifica_profilo_corelatore, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetAllHint();

        editButton.setOnClickListener(view -> {
            FillAllEmpty();

            if (Utility.coRelatoreLoggato.modCoRelatore(nome.getText().toString(), cognome.getText().toString(), mail.getText().toString(),
                    password.getText().toString(), codFisc.getText().toString(), org.getText().toString(), db)){
                Toast.makeText(getActivity().getApplicationContext(),"Modifica effettuata con successo",Toast.LENGTH_SHORT).show();
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ProfiloFragment());
            }
        });
    }

    private void SetAllHint() {
        nome.setHint(Utility.coRelatoreLoggato.getNome());
        cognome.setHint(Utility.coRelatoreLoggato.getCognome());
        mail.setHint(Utility.coRelatoreLoggato.getEmail());
        password.setHint(Utility.coRelatoreLoggato.getPassword());
        codFisc.setHint(Utility.coRelatoreLoggato.getCodiceFiscale());
        org.setHint(Utility.coRelatoreLoggato.getOrganizzazione());
    }

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

    private void FillAllEmpty(){
        Utility.fillIfEmpty(nome, Utility.coRelatoreLoggato.getNome());
        Utility.fillIfEmpty(cognome, Utility.coRelatoreLoggato.getCognome());
        Utility.fillIfEmpty(mail, Utility.coRelatoreLoggato.getEmail());
        Utility.fillIfEmpty(password, Utility.coRelatoreLoggato.getPassword());
        Utility.fillIfEmpty(codFisc, Utility.coRelatoreLoggato.getCodiceFiscale());
        Utility.fillIfEmpty(org, Utility.coRelatoreLoggato.getOrganizzazione());
    }
}