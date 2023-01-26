package it.uniba.dib.sms222329.fragment.relatore;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RelatoreDatabase;
import it.uniba.dib.sms222329.fragment.signUp.CorsiDiStudiAdapter;

public class  ModificaProfiloRelatoreFragment extends Fragment {
    private Database db;
    private Relatore relatoreLoggato;
    private String oldSpinnerName;

    public ModificaProfiloRelatoreFragment(Database db, Relatore relatoreLoggato) {
        this.db = db;
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modifica_profilo_relatore, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Crea lo spinner
        String query = "SELECT " + Database.UNIVERSITA_NOME + " FROM " + Database.UNIVERSITA + ";";
        spinnerCreate(R.id.universita, query);

        //Reference agli elementi dell'interfaccia
        EditText nome = getView().findViewById(R.id.nome);
        EditText cognome = getView().findViewById(R.id.cognome);
        EditText mail = getView().findViewById(R.id.email);
        EditText password = getView().findViewById(R.id.password);
        EditText codFisc = getView().findViewById(R.id.codiceFiscale);
        EditText matricola = getView().findViewById(R.id.matricola);
        Spinner universita = getView().findViewById(R.id.universita);
        Button conferma = getView().findViewById(R.id.conferma);

        //Carica come suggerimenti i valori attuali del profilo
        nome.setHint(relatoreLoggato.getNome());
        cognome.setHint(relatoreLoggato.getCognome());
        mail.setHint(relatoreLoggato.getEmail());
        password.setHint(relatoreLoggato.getPassword());
        codFisc.setHint(relatoreLoggato.getCodiceFiscale());
        matricola.setHint(relatoreLoggato.getMatricola());

        //Gestisce lo spinner in caso di cambi
        GestisciSpinner(universita);

        conferma.setOnClickListener(view -> {
            //Riempie i campi non modificati con i valori esistenti
            fillIfEmpty(nome, relatoreLoggato.getNome());
            fillIfEmpty(cognome, relatoreLoggato.getCognome());
            fillIfEmpty(mail, relatoreLoggato.getEmail());
            fillIfEmpty(password, relatoreLoggato.getPassword());
            fillIfEmpty(codFisc, relatoreLoggato.getCodiceFiscale());
            fillIfEmpty(matricola, relatoreLoggato.getMatricola());

            //Recupera gli id dei corsi dei relatori
            String idUniversita = RecuperaIdSpinner(universita,Database.UNIVERSITA);
            List idCorsiSelezionati = RecuperaIdCorsi();
            ArrayList<Integer> corsiRelatore = RecuperaUniversitaCorso(idUniversita,idCorsiSelezionati);

            //Modifica
            relatoreLoggato.modRelatore(matricola.getText().toString(),nome.getText().toString(),
                    cognome.getText().toString(), codFisc.getText().toString(),mail.getText().toString(),
                    password.getText().toString(), corsiRelatore);
            if (RelatoreDatabase.modRelatore(relatoreLoggato, db)) {
                Toast.makeText(getActivity().getApplicationContext(),"modifica riuscita",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void spinnerCreate(int idSpinner, String query){
        Spinner spinner = getView().findViewById(idSpinner);

        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        List<String> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String item = cursor.getString(0);
            items.add(item);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Setta selezionata l'univerisità di cui il relatore fa parte
        cursor = db.RicercaDato("SELECT u." + Database.UNIVERSITA_NOME + " " +
                "FROM " + Database.UNIVERSITACORSO + " uc , " + Database.UNIVERSITA + " u " +
                "WHERE uc." + Database.UNIVERSITACORSO_UNIVERSITAID + "=u." + Database.UNIVERSITA_ID + " AND uc." + Database.UNIVERSITACORSO_ID + " = '"+ relatoreLoggato.getCorsiRelatore().get(0) +"';");
        if(cursor.moveToNext()){
            spinner.setSelection(adapter.getPosition(cursor.getString(0)));
            oldSpinnerName = spinner.getSelectedItem().toString();
        }
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
                String query = "SELECT " + Database.CORSOSTUDI_ID + " FROM " + Database.CORSOSTUDI +" WHERE " + Database.CORSOSTUDI_NOME + " LIKE '"+ checkBox.getText() +"';";
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
                boolean oldCheckBox;
                if(spinner.getSelectedItem().toString().compareTo(oldSpinnerName)==0){
                    oldCheckBox = true;
                } else{
                    oldCheckBox = false;
                }

                ListView listView = getActivity().findViewById(R.id.corsiDiStudio);
                CorsiDiStudiAdapter adapter = new CorsiDiStudiAdapter(getActivity().getApplicationContext(), corsi, relatoreLoggato, oldCheckBox);
                listView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    //Riempie il campo con il valore nel caso sia vuoto
    private void fillIfEmpty(EditText campo, String value){
        if(campo.getText().toString().matches("")){
            campo.autofill(AutofillValue.forText(value));
        }
    }
}