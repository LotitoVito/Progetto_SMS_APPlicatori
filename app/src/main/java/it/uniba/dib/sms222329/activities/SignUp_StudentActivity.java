package it.uniba.dib.sms222329.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.Database;

public class SignUp_StudentActivity extends AppCompatActivity {
    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);
        String query = "SELECT Nome FROM Universita;";
        spinnerCreate(R.id.universita, query);
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
        EditText numeroEsamiMancanti = findViewById(R.id.numeroEsamiMancanti);
        Spinner universita = findViewById(R.id.universita);
        Spinner corsoStudi = findViewById(R.id.corsoDiStudi);

        GestisciSpinner(universita);

        registerButton.setOnClickListener(view -> {
            String idUniversita = RecuperaIdSpinner(universita, "Universita");
            String idCorsoStudio = RecuperaIdSpinner(corsoStudi,"CorsiStudio");

            Tesista account = new Tesista(matricola.getText().toString(), nome.getText().toString(),
                    cognome.getText().toString(), email.getText().toString(), password.getText().toString(),
                    Integer.parseInt(media.getText().toString()), Integer.parseInt(numeroEsamiMancanti.getText().toString()),
                    idUniversita, idCorsoStudio);

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
        idCursor = db.RicercaDato("ID", tabella,"Nome",spinner.getSelectedItem().toString());
        idCursor.moveToNext();
        return idCursor.getString(0);
    }

    private void GestisciSpinner(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idUniversita = RecuperaIdSpinner(spinner, "Universita");

                Cursor risultato = db.RicercaDato("IDCorsoStudio", "UniversitaCorsistudio", "IDUniversita", idUniversita);
                List<String> idRisultati = new ArrayList<>();
                while(risultato.moveToNext()){
                    idRisultati.add(risultato.getString(0));
                }

                String query = "SELECT Nome FROM CorsiStudio WHERE ID IN (" + idRisultati.toString().replace("[", "").replace("]", "") + ");";
                spinnerCreate(R.id.corsoDiStudi, query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}