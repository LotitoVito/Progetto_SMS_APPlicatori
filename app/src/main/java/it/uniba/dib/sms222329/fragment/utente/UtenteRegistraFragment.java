package it.uniba.dib.sms222329.fragment.utente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.corelatore.CorelatoreRegistraFragment;
import it.uniba.dib.sms222329.fragment.relatore.RelatoreRegistraFragment;
import it.uniba.dib.sms222329.fragment.tesista.TesistaRegistraFragment;

public class UtenteRegistraFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;

    //View Items
    private TextInputEditText nome;
    private TextInputEditText cognome;
    private TextInputEditText codiceFiscale;
    private TextInputEditText email;
    private TextInputEditText password;
    private Button button;

    public UtenteRegistraFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utente_registra, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Init();
        button.setOnClickListener(v -> {
            if(!IsEmpty(nome, cognome, codiceFiscale, email, password)){
            UtenteRegistrato account = new UtenteRegistrato(nome.getText().toString().trim(), cognome.getText().toString().trim(),
                    codiceFiscale.getText().toString().trim(), email.getText().toString().trim(), password.getText().toString().trim());
                if (!db.VerificaDatoEsistente("SELECT " + Database.UTENTI_CODICEFISCALE + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_CODICEFISCALE + " = '" + account.getCodiceFiscale() + "';")){
                    if (!db.VerificaDatoEsistente("SELECT " + Database.UTENTI_EMAIL + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_EMAIL + " = '" + account.getEmail() + "';")) {
                        loadNextFragment(account);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), R.string.email_esistente, Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), R.string.codice_fiscale_esistente, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.campi_vuoti_errore, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Il metodo carica il fragment corretto in base alla scelta del tipo di account effettuata con i radio button
     * @param account
     */
    private void loadNextFragment(UtenteRegistrato account){
        RadioGroup radioGroup = getActivity().findViewById(R.id.radio_group);
        int checkedId = radioGroup.getCheckedRadioButtonId();
            switch (checkedId) {
                case R.id.radio_button_1:
                    Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.signUpcontainer, new TesistaRegistraFragment(account));
                    break;
                case R.id.radio_button_2:
                    Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.signUpcontainer, new RelatoreRegistraFragment(account));
                    break;
                case R.id.radio_button_3:
                    Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.signUpcontainer, new CorelatoreRegistraFragment(account));
                    break;
            }

    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init(){
        db = new Database(getActivity().getApplicationContext());
        button = getView().findViewById(R.id.next_button);
        nome = getActivity().findViewById(R.id.nome);
        cognome = getActivity().findViewById(R.id.cognome);
        codiceFiscale = getActivity().findViewById(R.id.codiceFiscale);
        email = getActivity().findViewById(R.id.email);
        password = getActivity().findViewById(R.id.password);
    }

    /**
     * Metodo che verifica se i campi obbligatori sono vuoti, nel caso siano vuoti sono contrassegnati;
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param email
     * @param password
     * @return  Il metodo restituisce true se almeno un campo è vuoto, restituisce false se tutti i campi non sono vuoti
     */
    private boolean IsEmpty(EditText nome, EditText cognome, EditText codiceFiscale, EditText email, EditText password){
        boolean risultato = false;

        if(Utility.isEmptyTextbox(nome)){
            risultato = true;
            nome.setError(getResources().getString(R.string.campo_obbligatorio));
        }
        if(Utility.isEmptyTextbox(cognome)){
            risultato = true;
            cognome.setError(getResources().getString(R.string.campo_obbligatorio));
        }
        if(Utility.isEmptyTextbox(codiceFiscale)){
            risultato = true;
            codiceFiscale.setError(getResources().getString(R.string.campo_obbligatorio));
        }
        if(Utility.isEmptyTextbox(email)){
            risultato = true;
            email.setError(getResources().getString(R.string.campo_obbligatorio));
        }
        if(Utility.isEmptyTextbox(password)){
            risultato = true;
            password.setError(getResources().getString(R.string.campo_obbligatorio));
        }
        return risultato;
    }
}