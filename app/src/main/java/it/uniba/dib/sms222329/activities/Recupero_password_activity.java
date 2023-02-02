package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.UtenteRegistratoDatabase;

public class Recupero_password_activity extends AppCompatActivity {

    //Variabili o Oggetti
    private Database db;

    //View Items
    private Button signInButton;
    private EditText emailIns1;
    private EditText emailIns2;
    private EditText passswordIns1;
    private EditText passswordIns2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupero_password);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Init();

        signInButton.setOnClickListener(view -> {
            if(!IsEmpty(emailIns1, emailIns2, passswordIns1, passswordIns2))  {
                if (UtenteRegistratoDatabase.ControlloMail(emailIns1.getText().toString().trim(), emailIns2.getText().toString().trim(), db)) {
                    if (UtenteRegistratoDatabase.ResetPassword(passswordIns1.getText().toString().trim(), passswordIns2.getText().toString().trim(), emailIns1.getText().toString().trim(), db)) {
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

    private boolean IsEmpty(EditText emailIns1, EditText emailIns2, EditText passswordIns1, EditText passswordIns2){
        boolean risultato = false;

        if(Utility.isEmptyTextbox(emailIns1)){
            emailIns1.setError("Obbligatorio");
            risultato = true;
        }
        if(Utility.isEmptyTextbox(emailIns2)){
            emailIns2.setError("Obbligatorio");
            risultato = true;
        }
        if(Utility.isEmptyTextbox(passswordIns1)){
            passswordIns1.setError("Obbligatorio");
            risultato = true;
        }
        if(Utility.isEmptyTextbox(passswordIns2)){
            passswordIns2.setError("Obbligatorio");
            risultato = true;
        }
        return risultato;
    }

    private void Init(){
        db = new Database(this);
        signInButton = findViewById(R.id.recoverPasswordButton);
        emailIns1 = findViewById(R.id.email);
        emailIns2 = findViewById(R.id.email2);
        passswordIns1 = findViewById(R.id.password);
        passswordIns2 = findViewById(R.id.password2);
    }
}