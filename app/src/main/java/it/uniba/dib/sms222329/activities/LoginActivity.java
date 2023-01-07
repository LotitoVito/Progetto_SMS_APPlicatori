package it.uniba.dib.sms222329.activities;
import java.util.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;

public class LoginActivity extends AppCompatActivity {
    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Button signInButton = findViewById(R.id.signInButton);
        EditText emailIns = findViewById(R.id.email);   //assegno la casella email alla variabile
        EditText editTextTextPassword = findViewById(R.id.editTextTextPassword);

        TextView forgotPasswordButton = findViewById(R.id.textView3);

        signInButton.setOnClickListener(view -> {

            UtenteRegistrato Utente = new UtenteRegistrato();
            Utente.setEmail(emailIns.getText().toString());
            Utente.setPassword(editTextTextPassword.getText().toString());

            if (Utente.LoginStatus(db)) {
                Intent UtenteLoggato = new Intent(getApplicationContext(), UtenteLoggato.class);
                UtenteLoggato.putExtra("utentePassato", Utente);
                startActivity(UtenteLoggato);
            } else {
                Toast.makeText(this, "Accesso non riuscito", Toast.LENGTH_SHORT).show();
            };


        }); //fini signInButton

        forgotPasswordButton.setOnClickListener(view -> {

            // inserisci cosa avviare quando si preme il bottone
            //passa il db al metodo

        }); //fine forgotPassword







    };

}