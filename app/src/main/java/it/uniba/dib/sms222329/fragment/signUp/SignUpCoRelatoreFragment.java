package it.uniba.dib.sms222329.fragment.signUp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.activities.MainActivity;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.CoRelatoreDatabase;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.UtenteRegistratoDatabase;

public class SignUpCoRelatoreFragment extends Fragment {
    Database db;
    UtenteRegistrato accountGenerale;

    public SignUpCoRelatoreFragment(Database db, UtenteRegistrato accountGenerale) {
        this.db = db;
        this.accountGenerale = accountGenerale;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_co_relatore, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        View registerButton = getActivity().findViewById(R.id.Signupbutton);
        TextInputEditText organizzaione = getActivity().findViewById(R.id.organizzazione);

        registerButton.setOnClickListener(view -> {

            if(!isEmptyTextbox(organizzaione)){
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

    private boolean isEmptyTextbox(EditText textbox){
        if(textbox.getText().toString().trim().compareTo("")==0){
            textbox.setError("Obbligatorio");
            return true;
        }
        textbox.setError(null);
        return false;
    }
}