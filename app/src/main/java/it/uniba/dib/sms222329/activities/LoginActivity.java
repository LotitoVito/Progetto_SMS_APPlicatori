package it.uniba.dib.sms222329.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.UtenteRegistratoDatabase;

public class LoginActivity extends AppCompatActivity {

    //Variabili e Oggetti
    private Database db;

    //View Items
    private Button signInButton;
    private Button forgotPasswordButton;
    private EditText emailIns;
    private EditText editTextTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utility.Clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(R.string.iscriviti);

        Init();

        signInButton.setOnClickListener(view -> {
            UtenteRegistrato Utente = new UtenteRegistrato(emailIns.getText().toString().trim(), editTextTextPassword.getText().toString().trim());

            if (UtenteRegistratoDatabase.LoginStatus(Utente, db)) {
                Intent UtenteLoggato = new Intent(getApplicationContext(), LoggedActivity.class);
                UtenteLoggato.putExtra("utentePassato", Utente);
                startActivity(UtenteLoggato);
            } else {
                Toast.makeText(this, "Accesso non riuscito", Toast.LENGTH_SHORT).show();
            }
        });

        forgotPasswordButton.setOnClickListener(view -> {
            Intent Recupero_password_activity = new Intent(getApplicationContext(), it.uniba.dib.sms222329.activities.Recupero_password_activity.class);
            startActivity(Recupero_password_activity);
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init(){
        db = new Database(this);
        signInButton = findViewById(R.id.signInButton);
        forgotPasswordButton = findViewById(R.id.textView3);
        emailIns = findViewById(R.id.email);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
    }
}



