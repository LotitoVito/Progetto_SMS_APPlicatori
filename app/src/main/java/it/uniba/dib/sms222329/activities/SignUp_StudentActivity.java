package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.Database;

public class SignUp_StudentActivity extends AppCompatActivity {
    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);
    }

    @Override
    protected void onResume() {
        super.onResume();

        View registerButton = findViewById(R.id.Signupbutton);
        EditText matricola = findViewById(R.id.matricola);
        EditText nome = findViewById(R.id.nome);
        EditText cognome = findViewById(R.id.cognome);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText media = findViewById(R.id.media);
        EditText numeroEsamiSvolti = findViewById(R.id.numeroEsamiSvolti);

        registerButton.setOnClickListener(view -> {
            Tesista account = new Tesista(matricola.getText().toString(), nome.getText().toString(),
                    cognome.getText().toString(), email.getText().toString(), password.getText().toString(),
                    Integer.parseInt(media.getText().toString()), Integer.parseInt(numeroEsamiSvolti.getText().toString()));

            if (!db.VerificaDatoEsistente("Matricola", "Tesista", account.getMatricola())){
                if(!db.VerificaDatoEsistente("Email", "Utenti", account.getEmail())){

                    if(account.registrazione(db)){
                        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainActivity);
                    } else{
                        Toast.makeText(this, "Registrazione non riuscita", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Email già esistente", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this, "Matricola già esistente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}