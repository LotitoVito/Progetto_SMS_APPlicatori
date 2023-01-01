package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.Database;

public class SignUp_RelatoreActivity extends AppCompatActivity {

    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_relatore);
    }

    @Override
    protected void onResume() {
        super.onResume();

        View registerButton = findViewById(R.id.Signupbutton);
        EditText matricola = findViewById(R.id.matricola); //prendono al casella dell'app dalla quale tramite il gettext prenderò il valore inserito
        EditText nome = findViewById(R.id.nome);
        EditText cognome = findViewById(R.id.cognome);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        registerButton.setOnClickListener(view -> {
            Relatore account = new Relatore(matricola.getText().toString(), nome.getText().toString(),
                    cognome.getText().toString(), email.getText().toString(), password.getText().toString());
            if(account.registrazione(db)){ //in base al risultato dell boolean sceglie il ramo if
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
            } else{
                Toast.makeText(this, "Registrazione non riuscita", Toast.LENGTH_SHORT).show(); //metodo android
            }
        });
    }
}