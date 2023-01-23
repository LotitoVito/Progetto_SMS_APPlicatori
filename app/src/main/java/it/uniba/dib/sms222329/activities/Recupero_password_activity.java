package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

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
        EditText emailIns1 = findViewById(R.id.email2);
        EditText emailIns2 = findViewById(R.id.email);
        EditText passswordIns1 = findViewById(R.id.recoverPassword);
        EditText passswordIns2 = findViewById(R.id.editTextTextPassword);

        signInButton.setOnClickListener(view -> {
            if(UtenteRegistratoDatabase.ControlloMail(emailIns1.getText().toString(), emailIns2.getText().toString(), db)) {
                if(UtenteRegistratoDatabase.ResetPassword(passswordIns1.getText().toString(), passswordIns2.getText().toString(), emailIns1.getText().toString(), db)){
                    Toast.makeText(this, "Password ripristinata", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Le password non corrispondono", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Le email non corrispondono / email inesistente", Toast.LENGTH_SHORT).show();
            }
        });
    } // fine resume
}