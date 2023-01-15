package it.uniba.dib.sms222329.fragment.signUp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
            UtenteRegistrato account = new UtenteRegistrato(nome.getText().toString(), cognome.getText().toString(),
                    codiceFiscale.getText().toString(), email.getText().toString(), password.getText().toString());

            if (!db.VerificaDatoEsistente("SELECT codice_fiscale FROM utenti WHERE codice_fiscale = '" + account.getCodiceFiscale() + "';")){
                if (!db.VerificaDatoEsistente("SELECT email FROM utenti WHERE email = '" + account.getEmail() + "';")) {

                    loadNextFragment(account);

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Email già esistente", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(getActivity().getApplicationContext(), "Codice Fiscale già esistente", Toast.LENGTH_SHORT).show();
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
}