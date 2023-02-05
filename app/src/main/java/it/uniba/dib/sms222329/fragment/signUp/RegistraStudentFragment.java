package it.uniba.dib.sms222329.fragment.signUp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.activities.MainActivity;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesistaDatabase;
import it.uniba.dib.sms222329.database.UtenteRegistratoDatabase;

public class RegistraStudentFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private UtenteRegistrato accountGenerale;

    //View Items
    private TextInputEditText matricola;
    private TextInputEditText media;
    private TextInputEditText numeroEsamiMancanti;
    private Spinner spinnerUniversita;
    private Spinner spinnerCorsoStudi;
    private Button registerButton;

    public RegistraStudentFragment(UtenteRegistrato accountGenerale) {
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

        Init();
        GestisciSpinner(spinnerUniversita);
        registerButton.setOnClickListener(view -> {
            String idUniversita = RecuperaIdSpinner(spinnerUniversita, Database.UNIVERSITA);
            String idCorsoStudio = RecuperaIdSpinner(spinnerCorsoStudi,Database.CORSOSTUDI);
            if(!IsEmpty(matricola, media, numeroEsamiMancanti)){
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

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        registerButton = getActivity().findViewById(R.id.Signupbutton);
        matricola = getActivity().findViewById(R.id.matricola);
        media = getActivity().findViewById(R.id.media);
        numeroEsamiMancanti = getActivity().findViewById(R.id.numeroEsamiMancanti);
        spinnerUniversita = getActivity().findViewById(R.id.universita);
        spinnerCorsoStudi = getActivity().findViewById(R.id.corsoDiStudi);

        String query = "SELECT " + Database.UNIVERSITA_NOME + " FROM " + Database.UNIVERSITA + ";";
        spinnerCreate(spinnerUniversita, query);
    }

    private void spinnerCreate(Spinner spinner, String query){
        //Recupera i nomi dalla query
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        List<String> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String item = cursor.getString(0);
            items.add(item);
        }
        cursor.close();

        //Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

                //Recupera id corsi in base all'universita scelta
                Cursor risultato = db.RicercaDato("SELECT " + Database.UNIVERSITACORSO_CORSOID + " FROM " + Database.UNIVERSITACORSO + " WHERE " + Database.UNIVERSITACORSO_UNIVERSITAID + " = '"+ idUniversita +"';");
                List<String> idRisultati = new ArrayList<>();
                while(risultato.moveToNext()){
                    idRisultati.add(risultato.getString(0));
                }

                //Crea spinner corsi
                String query = "SELECT " + Database.CORSOSTUDI_NOME + " FROM " + Database.CORSOSTUDI + " WHERE " + Database.CORSOSTUDI_ID + " IN (" + idRisultati.toString().replace("[", "").replace("]", "") + ");";
                spinnerCreate(spinnerCorsoStudi, query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private boolean IsEmpty(EditText matricola, EditText media, EditText numeroEsamiMancanti){
        boolean risultato = false;

        if(Utility.isEmptyTextbox(matricola)){
            risultato = true;
            matricola.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(media)){
            risultato = true;
            media.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(numeroEsamiMancanti)){
            risultato = true;
            numeroEsamiMancanti.setError("Obbligatorio");
        }
        return risultato;
    }
}