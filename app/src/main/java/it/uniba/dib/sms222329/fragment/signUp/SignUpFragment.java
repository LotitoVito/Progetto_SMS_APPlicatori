package it.uniba.dib.sms222329.fragment.signUp;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

public class SignUpFragment extends Fragment {
    Database db;

    public SignUpFragment(Database db) {
        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.next_button);
        TextInputEditText nome = getActivity().findViewById(R.id.nome);
        TextInputEditText cognome = getActivity().findViewById(R.id.cognome);
        TextInputEditText codiceFiscale = getActivity().findViewById(R.id.codiceFiscale);
        TextInputEditText email = getActivity().findViewById(R.id.email);
        TextInputEditText password = getActivity().findViewById(R.id.password);

        button.setOnClickListener(v -> {

            if(CheckEmpty(nome, cognome, codiceFiscale, email, password)){
            UtenteRegistrato account = new UtenteRegistrato(nome.getText().toString().trim(), cognome.getText().toString().trim(),
                    codiceFiscale.getText().toString().trim(), email.getText().toString().trim(), password.getText().toString().trim());

                if (!db.VerificaDatoEsistente("SELECT " + Database.UTENTI_CODICEFISCALE + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_CODICEFISCALE + " = '" + account.getCodiceFiscale() + "';")){

                    if (!db.VerificaDatoEsistente("SELECT " + Database.UTENTI_EMAIL + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_EMAIL + " = '" + account.getEmail() + "';")) {

                        loadNextFragment(account);

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Email già esistente", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "Codice Fiscale già esistente", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Compilare tutti i campi obbligatori", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNextFragment(UtenteRegistrato account){
        RadioGroup radioGroup = getActivity().findViewById(R.id.radio_group);
        int checkedId = radioGroup.getCheckedRadioButtonId();
            switch (checkedId) {
                case R.id.radio_button_1:
                    Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.signUpcontainer, new SignUpStudentFragment(db, account));
                    break;
                case R.id.radio_button_2:
                    Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.signUpcontainer, new SignUpRelatoreFragment(db, account));
                    break;
                case R.id.radio_button_3:
                    Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.signUpcontainer, new SignUpCoRelatoreFragment(db, account));
                    break;
            }

    }

    private boolean CheckEmpty(EditText nome, EditText cognome, EditText codiceFiscale, EditText email, EditText password){
        boolean risultato = true;

        if(isEmptyTextbox(nome)){
            risultato = false;
        }
        if(isEmptyTextbox(cognome)){
            risultato = false;
        }
        if(isEmptyTextbox(codiceFiscale)){
            risultato = false;
        }
        if(isEmptyTextbox(email)){
            risultato = false;
        }
        if(isEmptyTextbox(password)){
            risultato = false;
        }
        return risultato;
    }

    private boolean isEmptyTextbox(EditText textbox){
        if(textbox.getText().toString().trim().compareTo("")==0){
            textbox.setError("Obbligatorio");
            return true;
        }
        textbox.setError(null);
        return false;
    }
}