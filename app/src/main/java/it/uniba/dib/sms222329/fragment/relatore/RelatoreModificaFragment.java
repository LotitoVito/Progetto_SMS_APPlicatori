package it.uniba.dib.sms222329.fragment.relatore;

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
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.adapter.ListaCorsiAdapter;

public class RelatoreModificaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private String oldSpinnerName;

    //View Items
    private EditText nome;
    private EditText cognome;
    private EditText mail;
    private EditText password;
    private EditText codFisc;
    private EditText matricola;
    private Spinner universita;
    private ListView corsiStudio;
    private Button conferma;

    public RelatoreModificaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_relatore_modifica, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetHintAll();
        GestisciSpinner(universita);

        conferma.setOnClickListener(view -> {
            FillAllEmpty();

            //Recupera gli id dei corsi dei relatori
            String idUniversita = RecuperaIdUniversita();
            List idCorsiSelezionati = RecuperaIdCorsi();
            ArrayList<Integer> corsiRelatore = RecuperaUniversitaCorso(idUniversita,idCorsiSelezionati);

            //Modifica
            if (Utility.relatoreLoggato.modRelatore(matricola.getText().toString().trim(),nome.getText().toString().trim(),
                    cognome.getText().toString().trim(), codFisc.getText().toString().trim(),mail.getText().toString().trim(),
                    password.getText().toString().trim(), corsiRelatore, db)) {
                Toast.makeText(getActivity().getApplicationContext(),"Modificata effettuata con successo",Toast.LENGTH_SHORT).show();
                Utility.goBack(getActivity());
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init(){
        db = new Database(getActivity().getApplicationContext());
        nome = getView().findViewById(R.id.nome);
        cognome = getView().findViewById(R.id.cognome);
        mail = getView().findViewById(R.id.email);
        password = getView().findViewById(R.id.password);
        codFisc = getView().findViewById(R.id.codiceFiscale);
        matricola = getView().findViewById(R.id.matricola);
        universita = getView().findViewById(R.id.universita);
        corsiStudio = getView().findViewById(R.id.corsiDiStudio);
        conferma = getView().findViewById(R.id.conferma);

        spinnerCreate();
    }

    /**
     * Imposta il testo per ogni elemento della view come suggerimento
     */
    private void SetHintAll(){
        nome.setHint(Utility.relatoreLoggato.getNome());
        cognome.setHint(Utility.relatoreLoggato.getCognome());
        mail.setHint(Utility.relatoreLoggato.getEmail());
        password.setHint(Utility.relatoreLoggato.getPassword());
        codFisc.setHint(Utility.relatoreLoggato.getCodiceFiscale());
        matricola.setHint(Utility.relatoreLoggato.getMatricola());
    }

    /**
     * Riempie i campi vuoti con il giusto valore se sono vuoti
     */
    private void FillAllEmpty(){
        Utility.fillIfEmpty(nome, Utility.relatoreLoggato.getNome());
        Utility.fillIfEmpty(cognome, Utility.relatoreLoggato.getCognome());
        Utility.fillIfEmpty(mail, Utility.relatoreLoggato.getEmail());
        Utility.fillIfEmpty(password, Utility.relatoreLoggato.getPassword());
        Utility.fillIfEmpty(codFisc, Utility.relatoreLoggato.getCodiceFiscale());
        Utility.fillIfEmpty(matricola, Utility.relatoreLoggato.getMatricola());
    }

    /**
     * Crea lo spinner e lo imposta al valore iniziale corretto
     */
    private void spinnerCreate(){
        //Carica Elementi
        String query = "SELECT " + Database.UNIVERSITA_NOME + " FROM " + Database.UNIVERSITA + ";";
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        List<String> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String item = cursor.getString(cursor.getColumnIndexOrThrow(Database.UNIVERSITA_NOME));
            items.add(item);
        }
        cursor.close();

        //Adapter Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universita.setAdapter(adapter);

        //Setta selezionata l'univerisità di cui il relatore fa parte
        cursor = db.RicercaDato("SELECT u." + Database.UNIVERSITA_NOME + " " +
                "FROM " + Database.UNIVERSITACORSO + " uc , " + Database.UNIVERSITA + " u " +
                "WHERE uc." + Database.UNIVERSITACORSO_UNIVERSITAID + "=u." + Database.UNIVERSITA_ID + " AND uc." + Database.UNIVERSITACORSO_ID + " = '"+ Utility.relatoreLoggato.getCorsiRelatore().get(0) +"';");
        if(cursor.moveToNext()){
            universita.setSelection(adapter.getPosition(cursor.getString(0)));
            oldSpinnerName = universita.getSelectedItem().toString();
        }
    }

    /**
     * Permette di recuperare l'id dell'università selezionata dallo spinner
     * @return  Restituisce l'id dellì'università selezionata
     */
    private String RecuperaIdUniversita(){
        Cursor idCursor;
        idCursor = db.RicercaDato("SELECT " + Database.UNIVERSITA_ID + " FROM "+ Database.UNIVERSITA +" WHERE nome = '"+ universita.getSelectedItem().toString() +"';");
        idCursor.moveToNext();
        return idCursor.getString(idCursor.getColumnIndexOrThrow(Database.UNIVERSITA_ID));
    }

    /**
     * Permette di recuperare gli id dei corsi selezionati con le checkbox
     * @return  restituisce un array di id dei corsi selezionati
     */
    private List RecuperaIdCorsi(){
        List<String> idCorsiSelezionati = new ArrayList<>();

        for (int i = 0; i < corsiStudio.getChildCount(); i++) {
            CheckBox checkBox = corsiStudio.getChildAt(i).findViewById(R.id.checkbox);
            String query = "SELECT " + Database.CORSOSTUDI_ID + " FROM " + Database.CORSOSTUDI +" WHERE " + Database.CORSOSTUDI_NOME + " LIKE '"+ checkBox.getText() +"';";
            if (checkBox.isChecked()) {
                Cursor risultati = db.RicercaDato(query);
                risultati.moveToNext();
                idCorsiSelezionati.add(risultati.getString(risultati.getColumnIndexOrThrow(Database.CORSOSTUDI_ID)));
            }
        }

        return idCorsiSelezionati;
    }

    /**
     * Permette di recuperare l'id della coppia università-corso dal database
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
            corsiRelatore.add(idCursor.getInt(idCursor.getColumnIndexOrThrow(Database.UNIVERSITACORSO_ID)));
        }
        return corsiRelatore;
    }

    /**
     * Permette di gestire i cambiati di valore dello spinner e di adattare le checkbox
     * @param spinner
     */
   private void GestisciSpinner(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idUniversita = RecuperaIdUniversita();

                //Recupero id di corsi dell'uni selezionata
                Cursor risultato = db.RicercaDato("SELECT " + Database.UNIVERSITACORSO_CORSOID + " FROM " + Database.UNIVERSITACORSO + " WHERE " + Database.UNIVERSITACORSO_UNIVERSITAID + " = '"+ idUniversita +"';");
                List<String> idRisultati = new ArrayList<>();
                while(risultato.moveToNext()){
                    idRisultati.add(risultato.getString(risultato.getColumnIndexOrThrow(Database.UNIVERSITACORSO_CORSOID)));
                }

                //Recupero i nomi dei corsi dagli id
                String query = "SELECT " + Database.CORSOSTUDI_NOME + " FROM " + Database.CORSOSTUDI + " WHERE " + Database.CORSOSTUDI_ID + " IN (" + idRisultati.toString().replace("[", "").replace("]", "") + ");";
                Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
                List<String> corsi = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String item = cursor.getString(0);
                    corsi.add(item);
                }
                cursor.close();

                //Verifico se il nome dello spinner è quello già registrato dal relatore
                boolean oldCheckBox;
                if(spinner.getSelectedItem().toString().compareTo(oldSpinnerName)==0){
                    oldCheckBox = true;
                } else{
                    oldCheckBox = false;
                }

                //Setto adapter corsi
                ListaCorsiAdapter adapter = new ListaCorsiAdapter(getActivity().getApplicationContext(), corsi, oldCheckBox);
                corsiStudio.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}