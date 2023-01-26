package it.uniba.dib.sms222329.fragment.signUp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.activities.MainActivity;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RelatoreDatabase;
import it.uniba.dib.sms222329.database.UtenteRegistratoDatabase;

public class SignUpRelatoreFragment extends Fragment {
    Database db;
    UtenteRegistrato accountGenerale;

    public SignUpRelatoreFragment(Database db, UtenteRegistrato accountGenerale) {
        this.db = db;
        this.accountGenerale = accountGenerale;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_relatore, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        String query = "SELECT " + Database.UNIVERSITA_NOME + " FROM " + Database.UNIVERSITA + ";";
        spinnerCreate(R.id.universita, query);

        View registerButton = getActivity().findViewById(R.id.Signupbutton);
        EditText matricola = getActivity().findViewById(R.id.matricola);
        Spinner universita = getActivity().findViewById(R.id.universita);

        GestisciSpinner(universita);

        registerButton.setOnClickListener(view -> {
            String idUniversita = RecuperaIdSpinner(universita, Database.UNIVERSITA);
            List idCorsiSelezionati = RecuperaIdCorsi();
            ArrayList<Integer> corsiRelatore = RecuperaUniversitaCorso(idUniversita, idCorsiSelezionati);

            Relatore account = new Relatore(matricola.getText().toString(), accountGenerale.getNome(),
                    accountGenerale.getCognome(), accountGenerale.getCodiceFiscale(), accountGenerale.getEmail(),
                    accountGenerale.getPassword(), 2, corsiRelatore);

            if(!db.VerificaDatoEsistente("SELECT " + Database.RELATORE_MATRICOLA + " FROM " + Database.RELATORE + " WHERE " + Database.RELATORE_MATRICOLA + " = '"+ account.getMatricola() +"';")){

                    if(UtenteRegistratoDatabase.RegistrazioneUtente(account, db) && RelatoreDatabase.RegistrazioneRelatore(account, db)){
                        Intent mainActivity = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        startActivity(mainActivity);
                    } else{
                        Toast.makeText(getActivity().getApplicationContext(), "Registrazione non riuscita", Toast.LENGTH_SHORT).show();
                    }

            }else{
                Toast.makeText(getActivity().getApplicationContext(), "Matricola già esistente", Toast.LENGTH_SHORT).show();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, items);

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

    private List RecuperaIdCorsi(){
        ListView listView = getActivity().findViewById(R.id.corsiDiStudio);
        List<String> idCorsiSelezionati = new ArrayList<>();

        for (int i = 0; i < listView.getChildCount(); i++) {
            CheckBox checkBox = listView.getChildAt(i).findViewById(R.id.checkbox);
            if (checkBox.isChecked()) {
                String query = "SELECT " + Database.CORSOSTUDI_ID + " FROM " + Database.CORSOSTUDI + " WHERE " + Database.CORSOSTUDI_NOME + " LIKE '"+ checkBox.getText() +"';";
                Cursor risultati = db.RicercaDato(query);
                risultati.moveToNext();
                idCorsiSelezionati.add(risultati.getString(0));
            }
        }

        return idCorsiSelezionati;
    }

    private ArrayList<Integer> RecuperaUniversitaCorso(String idUniversita, List idCorsiSelezionati){
        Cursor idCursor;
        ArrayList<Integer> corsiRelatore = new ArrayList();
        for(int i=0; i< idCorsiSelezionati.size(); i++){
            idCursor = db.RicercaDato("SELECT " + Database.UNIVERSITACORSO_ID + " FROM " + Database.UNIVERSITACORSO + " WHERE " + Database.UNIVERSITACORSO_UNIVERSITAID + " = '"+ idUniversita +"' AND " + Database.UNIVERSITACORSO_CORSOID + " = '"+ idCorsiSelezionati.get(i) +"';");
            idCursor.moveToNext();
            corsiRelatore.add(idCursor.getInt(0));
        }
        return corsiRelatore;
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
                // Query the database for the data
                Cursor cursor = db.getReadableDatabase().rawQuery(query, null);

                // Create an array of strings using the data from the Cursor
                List<String> corsi = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String item = cursor.getString(0);
                    corsi.add(item);
                }
                cursor.close();

                ListView listView = getActivity().findViewById(R.id.corsiDiStudio);
                CorsiDiStudiAdapter adapter = new CorsiDiStudiAdapter(getActivity().getApplicationContext(), corsi);
                listView.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

}