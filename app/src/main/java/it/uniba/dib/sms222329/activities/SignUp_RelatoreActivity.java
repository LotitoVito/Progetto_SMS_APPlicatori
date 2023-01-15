package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.Database;

public class SignUp_RelatoreActivity extends AppCompatActivity {
    Database db = new Database(this);

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_relatore);
        String query = "SELECT nome FROM universita;";
        spinnerCreate(R.id.universita, query);
    }

    @Override
    protected void onResume() {
        super.onResume();

        View registerButton = findViewById(R.id.Signupbutton);
        EditText matricola = findViewById(R.id.matricola);
        EditText cognome = findViewById(R.id.cognome);
        EditText nome = findViewById(R.id.nome);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        //Spinner universita = findViewById(R.id.universita);

        registerButton.setOnClickListener(view -> {
            //String idUniversita = RecuperaIdSpinner(universita, "Universita");

            Relatore account = new Relatore(matricola.getText().toString(), nome.getText().toString(),
                    cognome.getText().toString(), email.getText().toString(), password.getText().toString());

            if(!db.VerificaDatoEsistente("SELECT matricola FROM relatore WHERE matricola = '"+ account.getMatricola() +"';")){
                if(!db.VerificaDatoEsistente("SELECT email FROM utenti WHERE email = '"+ account.getEmail() +"';")){

                    if(account.RegistrazioneUtente(db, 2) && account.RegistrazioneRelatore(db)){
                        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainActivity);
                    } else{
                        Toast.makeText(this, "Registrazione non riuscita", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Email già esistente", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Matricola già esistente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void spinnerCreate(int idSpinner, String query){
        Spinner spinner = findViewById(idSpinner);

        // Query the database for the data
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);

        // Create an array of strings using the data from the Cursor
        List<String> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String item = cursor.getString(0);
            items.add(item);
        }
        cursor.close();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private String RecuperaIdSpinner(Spinner spinner, String tabella){
        Cursor idCursor;
        idCursor = db.RicercaDato("SELECT ID FROM "+ tabella +" WHERE Nome = '"+ spinner.getSelectedItem().toString() +"';");
        idCursor.moveToNext();
        return idCursor.getString(0);
    }*/
}