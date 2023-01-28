package it.uniba.dib.sms222329.fragment.signUp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.activities.MainActivity;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesistaDatabase;
import it.uniba.dib.sms222329.database.UtenteRegistratoDatabase;

public class SignUpStudentFragment extends Fragment {
    Database db;
    UtenteRegistrato accountGenerale;

    public SignUpStudentFragment(Database db, UtenteRegistrato accountGenerale) {
        this.db = db;
        this.accountGenerale = accountGenerale;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_student, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        String query = "SELECT " + Database.UNIVERSITA_NOME + " FROM " + Database.UNIVERSITA + ";";
        spinnerCreate(R.id.universita, query);

        View registerButton = getActivity().findViewById(R.id.Signupbutton);
        TextInputEditText matricola = getActivity().findViewById(R.id.matricola);
        TextInputEditText media = getActivity().findViewById(R.id.media);
        TextInputEditText numeroEsamiMancanti = getActivity().findViewById(R.id.numeroEsamiMancanti);
        Spinner spinnerUniversita = getActivity().findViewById(R.id.universita);
        Spinner spinnerCorsoStudi = getActivity().findViewById(R.id.corsoDiStudi);

        GestisciSpinner(spinnerUniversita);

        registerButton.setOnClickListener(view -> {
            String idUniversita = RecuperaIdSpinner(spinnerUniversita, Database.UNIVERSITA);
            String idCorsoStudio = RecuperaIdSpinner(spinnerCorsoStudi,Database.CORSOSTUDI);

            if(CheckEmpty(matricola, media, numeroEsamiMancanti)){
            Tesista account = new Tesista(matricola.getText().toString().trim(), accountGenerale.getNome(),
                    accountGenerale.getCognome(), accountGenerale.getCodiceFiscale(), accountGenerale.getEmail(),
                    accountGenerale.getPassword(), 1, Integer.parseInt(media.getText().toString().trim()),
                    Integer.parseInt(numeroEsamiMancanti.getText().toString().trim()), RecuperaUniversitaCorso(idUniversita, idCorsoStudio));

                if (!db.VerificaDatoEsistente("SELECT " + Database.TESISTA_MATRICOLA + " FROM " + Database.TESISTA + " WHERE " + Database.TESISTA_MATRICOLA + " = '"+ account.getMatricola() +"';")){

                    if(UtenteRegistratoDatabase.RegistrazioneUtente(account, db) && TesistaDatabase.RegistrazioneTesista(account, db)){
                        Intent mainActivity = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        startActivity(mainActivity);
                    } else{
                        Toast.makeText(getActivity().getApplicationContext(), "Registrazione non riuscita", Toast.LENGTH_SHORT).show();
                    }

                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "Matricola già esistente", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Compilare tutti i campi obbligatori", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void spinnerCreate(int idSpinner, String query){
        Spinner spinner = getView().findViewById(idSpinner);

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
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, items);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private String RecuperaIdSpinner(Spinner spinner, String tabella){
        Cursor idCursor;
        idCursor = db.RicercaDato("SELECT id FROM "+ tabella +" WHERE nome = '"+ spinner.getSelectedItem().toString() +"';");
        idCursor.moveToNext();
        return idCursor.getString(0);
    }

    private int RecuperaUniversitaCorso(String idUniversita, String idCorso){
        Cursor idCursor;
        idCursor = db.RicercaDato("SELECT " + Database.UNIVERSITACORSO_ID + " FROM " + Database.UNIVERSITACORSO + " WHERE " + Database.UNIVERSITACORSO_UNIVERSITAID + " = '"+ idUniversita +"' AND " + Database.UNIVERSITACORSO_CORSOID + " = '"+ idCorso +"';");
        idCursor.moveToNext();
        return idCursor.getInt(0);
    }

    private void GestisciSpinner(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idUniversita = RecuperaIdSpinner(spinner, Database.UNIVERSITA);

                Cursor risultato = db.RicercaDato("SELECT " + Database.UNIVERSITACORSO_CORSOID + " FROM " + Database.UNIVERSITACORSO + " WHERE " + Database.UNIVERSITACORSO_UNIVERSITAID + " = '"+ idUniversita +"';");
                List<String> idRisultati = new ArrayList<>();
                while(risultato.moveToNext()){
                    idRisultati.add(risultato.getString(0));
                }

                String query = "SELECT " + Database.CORSOSTUDI_NOME + " FROM " + Database.CORSOSTUDI + " WHERE " + Database.CORSOSTUDI_ID + " IN (" + idRisultati.toString().replace("[", "").replace("]", "") + ");";
                spinnerCreate(R.id.corsoDiStudi, query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private boolean CheckEmpty(EditText matricola, EditText media, EditText numeroEsamiMancanti){
        boolean risultato = true;

        if(isEmptyTextbox(matricola)){
            risultato = false;
        }
        if(isEmptyTextbox(media)){
            risultato = false;
        }
        if(isEmptyTextbox(numeroEsamiMancanti)){
            risultato = false;
        }
        return risultato;
    }

    private boolean isEmptyTextbox(EditText textbox){
        if(textbox.getText().toString().trim().compareTo("")==0){
            textbox.setError("Obbligatorio");
            return true;
        }
        textbox.setError(null);
        return false;
    }
}