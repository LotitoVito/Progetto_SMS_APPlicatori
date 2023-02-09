package it.uniba.dib.sms222329.fragment.corelatore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.activities.MainActivity;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.CoRelatoreDatabase;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.UtenteRegistratoDatabase;

public class CorelatoreRegistraFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private UtenteRegistrato accountGenerale;

    //View Items
    private Button registerButton;
    private TextInputEditText organizzaione;

    public CorelatoreRegistraFragment(UtenteRegistrato accountGenerale) {
        this.accountGenerale = accountGenerale;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_corelatore_registra, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        registerButton.setOnClickListener(view -> {
            if(!isEmpty(organizzaione)){
                CoRelatore account = new CoRelatore(accountGenerale.getNome(), accountGenerale.getCognome(), accountGenerale.getCodiceFiscale(),
                        accountGenerale.getEmail(), accountGenerale.getPassword(), 3, organizzaione.getText().toString().trim());
                if(UtenteRegistratoDatabase.RegistrazioneUtente(account, db) && CoRelatoreDatabase.RegistrazioneCoRelatore(account, db)){
                    Intent mainActivity = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(mainActivity);
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "Registrazione non riuscita", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Compilare tutti i campi obbligatori", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init(){
        db = new Database(getActivity().getApplicationContext());
        registerButton = getActivity().findViewById(R.id.Signupbutton);
        organizzaione = getActivity().findViewById(R.id.organizzazione);
    }

    /**
     * Metodo che verifica se il campo obbligatoro è vuoto, nel caso sia vuoto lo contrassegna;
     * @param textbox
     * @return  Il metodo restituisce true se il campo è vuoto, altrimenti false
     */
    private boolean isEmpty(EditText textbox){
        boolean risultato = false;
        if(Utility.isEmptyTextbox(textbox)){
            risultato = true;
            textbox.setError("Obbligatorio");
        }
        return risultato;
    }
}