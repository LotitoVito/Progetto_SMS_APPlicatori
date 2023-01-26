package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.UtenteRegistratoDatabase;

public class Recupero_password_activity extends AppCompatActivity {
    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recupero_password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button signInButton = findViewById(R.id.recoverPasswordButton);
        EditText emailIns1 = findViewById(R.id.email);
        EditText emailIns2 = findViewById(R.id.email2);
        EditText passswordIns1 = findViewById(R.id.password);
        EditText passswordIns2 = findViewById(R.id.password2);

        signInButton.setOnClickListener(view -> {
            if(CheckEmpty(emailIns1, emailIns2, passswordIns1, passswordIns2))  {
                if (UtenteRegistratoDatabase.ControlloMail(emailIns1.getText().toString(), emailIns2.getText().toString(), db)) {
                    if (UtenteRegistratoDatabase.ResetPassword(passswordIns1.getText().toString(), passswordIns2.getText().toString(), emailIns1.getText().toString(), db)) {
                        Toast.makeText(this, "Password ripristinata", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Le password non corrispondono", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Le email non corrispondono / email inesistente", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this, "Compilare tutti i campi obbligatori", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean CheckEmpty(EditText emailIns1, EditText emailIns2, EditText passswordIns1, EditText passswordIns2){
        boolean risultato = true;

        if(isEmptyTextbox(emailIns1)){
            risultato = false;
        }
        if(isEmptyTextbox(emailIns2)){
            risultato = false;
        }
        if(isEmptyTextbox(passswordIns1)){
            risultato = false;
        }
        if(isEmptyTextbox(passswordIns2)){
            risultato = false;
        }
        return risultato;
    }

    private boolean isEmptyTextbox(EditText textbox){
        if(textbox.getText().toString().trim().compareTo("")==0){
            textbox.setError("Obbligatorio");
            return false;
        }
        textbox.setError(null);
        return true;
    }
}