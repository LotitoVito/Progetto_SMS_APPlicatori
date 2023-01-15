package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.Database;

public class SignUp_CorelatoreActivity extends AppCompatActivity {
    Database db = new Database(this);

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_corelatore);
    }

    @Override
    protected void onResume() {
        super.onResume();

        View registerButton = findViewById(R.id.Signupbutton);
        EditText nome = findViewById(R.id.nome);
        EditText cognome = findViewById(R.id.cognome);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        registerButton.setOnClickListener(view -> {
            CoRelatore account = new CoRelatore(nome.getText().toString(), cognome.getText().toString(),
                    email.getText().toString(), password.getText().toString());

            if(!db.VerificaDatoEsistente("SELECT email FROM utenti WHERE email = '" + account.getEmail() +"';")){

                if(account.RegistrazioneUtente(db, 3) && account.RegistrazioneCoRelatore(db)){
                    Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainActivity);
                } else{
                    Toast.makeText(this, "Registrazione non riuscita", Toast.LENGTH_SHORT).show();
                }

            } else{
                Toast.makeText(this, "Email già esistente", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}