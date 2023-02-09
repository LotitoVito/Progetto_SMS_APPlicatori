package it.uniba.dib.sms222329.fragment.relatore;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.activities.MainActivity;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RelatoreDatabase;
import it.uniba.dib.sms222329.database.UtenteRegistratoDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaCorsiAdapter;

public class RelatoreRegistraFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private UtenteRegistrato accountGenerale;

    //View Items
    private Button registerButton;
    private EditText matricola;
    private Spinner universita;
    private ListView corsi;

    public RelatoreRegistraFragment(UtenteRegistrato accountGenerale) {
        this.accountGenerale = accountGenerale;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_relatore_registra, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        GestisciSpinner(universita);
        registerButton.setOnClickListener(view -> {
            String idUniversita = RecuperaIdSpinner();
            List idCorsiSelezionati = RecuperaIdCorsi();
            ArrayList<Integer> corsiRelatore = RecuperaUniversitaCorso(idUniversita, idCorsiSelezionati);

            if(!isEmpty(matricola) && corsiRelatore.size()!=0){
            Relatore account = new Relatore(matricola.getText().toString().trim(), accountGenerale.getNome(),
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
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Compilare tutti i campi obbligatori", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        registerButton = getActivity().findViewById(R.id.Signupbutton);
        matricola = getActivity().findViewById(R.id.matricola);
        universita = getActivity().findViewById(R.id.universita);
        corsi = getActivity().findViewById(R.id.corsiDiStudio);

        String query = "SELECT " + Database.UNIVERSITA_NOME + " FROM " + Database.UNIVERSITA + ";";
        spinnerCreate();
    }

    /**
     * Crea lo spinner università
     */
    private void spinnerCreate(){
        //Carica i nomi
        String query = "SELECT " + Database.UNIVERSITA_NOME + " FROM " + Database.UNIVERSITA + ";";
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        List<String> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String item = cursor.getString(0);
            items.add(item);
        }
        cursor.close();

        //Adapater Universita
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universita.setAdapter(adapter);
    }

    /**
     * Recupera l'id da database dell'università selezionata
     * @return  Restituisce l'id dell'università selezionata
     */
    private String RecuperaIdSpinner(){
        Cursor idCursor;
        idCursor = db.RicercaDato("SELECT id FROM "+ Database.UNIVERSITA +" WHERE nome = '"+ universita.getSelectedItem().toString() +"';");
        idCursor.moveToNext();
        return idCursor.getString(0);
    }

    /**
     * Recupera gli id dal database dei corsi selezionati con i checkbox
     * @return  Restituisce un array di id dei corsi selezionati
     */
    private List RecuperaIdCorsi(){
        List<String> idCorsiSelezionati = new ArrayList<>();
        for (int i = 0; i < corsi.getChildCount(); i++) {
            CheckBox checkBox = corsi.getChildAt(i).findViewById(R.id.checkbox);
            if (checkBox.isChecked()) {
                String query = "SELECT " + Database.CORSOSTUDI_ID + " FROM " + Database.CORSOSTUDI + " WHERE " + Database.CORSOSTUDI_NOME + " LIKE '"+ checkBox.getText() +"';";
                Cursor risultati = db.RicercaDato(query);
                risultati.moveToNext();
                idCorsiSelezionati.add(risultati.getString(0));
            }
        }

        return idCorsiSelezionati;
    }

    /**
     * Recupera l'id della coppia universita-corso dal database
     * @param idUniversita
     * @param idCorsiSelezionati
     * @return  Restituisce l'id della coppia università-corso
     */
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

    /**
     * Gestisce lo spinner università e cambia le checkbox dei corsi in bae all'università selezionata
     * @param spinner
     */
    private void GestisciSpinner(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idUniversita = RecuperaIdSpinner();

                //Recupera id dei orsi in base all'id dell'universita selezionata
                Cursor risultato = db.RicercaDato("SELECT " + Database.UNIVERSITACORSO_CORSOID + " FROM " + Database.UNIVERSITACORSO + " WHERE " + Database.UNIVERSITACORSO_UNIVERSITAID + " = '"+ idUniversita +"';");
                List<String> idRisultati = new ArrayList<>();
                while(risultato.moveToNext()){
                    idRisultati.add(risultato.getString(0));
                }

                //Recupera i nomi dei corsi in base agli id
                String query = "SELECT " + Database.CORSOSTUDI_NOME + " FROM " + Database.CORSOSTUDI + " WHERE " + Database.CORSOSTUDI_ID + " IN (" + idRisultati.toString().replace("[", "").replace("]", "") + ");";
                Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
                List<String> corsi = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String item = cursor.getString(0);
                    corsi.add(item);
                }
                cursor.close();

                //Adapter Corsi
                ListView listView = getActivity().findViewById(R.id.corsiDiStudio);
                ListaCorsiAdapter adapter = new ListaCorsiAdapter(getActivity().getApplicationContext(), corsi);
                listView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private boolean isEmpty(EditText textbox){
        boolean risultato = false;
        if(Utility.isEmptyTextbox(textbox)){
            textbox.setError("Obbligatorio");
            return true;
        }
        return false;
    }
}