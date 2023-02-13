package it.uniba.dib.sms222329.fragment.tesista;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import it.uniba.dib.sms222329.database.Database;

public class TesistaModificaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;

    //View Items
    private EditText nome;
    private EditText cognome;
    private EditText mail;
    private EditText password;
    private EditText codFisc;
    private EditText matricola;
    private TextInputEditText media;
    private TextInputEditText numeroEsamiMancanti;
    private Spinner spinnerUniversita;
    private Spinner spinnerCorsoStudi;
    private Button registerButton;

    public TesistaModificaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesista_modifica, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        FillAllEmpty();
        GestisciSpinner(spinnerUniversita);

        registerButton.setOnClickListener(view -> {
            FillAllEmpty();

            String idUniversita = RecuperaIdSpinner(spinnerUniversita, Database.UNIVERSITA);
            String idCorsoStudio = RecuperaIdSpinner(spinnerCorsoStudi,Database.CORSOSTUDI);
            int corso= RecuperaUniversitaCorso(idUniversita, idCorsoStudio);

            //Modifica
                if(Float.parseFloat(media.getText().toString())>=18 && Float.parseFloat(media.getText().toString())<=30){
                    if (Utility.tesistaLoggato.modTesista(matricola.getText().toString(), nome.getText().toString(),
                            cognome.getText().toString(), mail.getText().toString(),
                            password.getText().toString(),Float.parseFloat(media.getText().toString()),
                            Integer.parseInt(numeroEsamiMancanti.getText().toString()),
                            codFisc.getText().toString(), corso, db)){
                        Toast.makeText(getActivity().getApplicationContext(),R.string.modifica_successo,Toast.LENGTH_SHORT).show();
                        Utility.goBack(getActivity());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.media_errore, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        nome = getActivity().findViewById(R.id.nome);
        cognome = getActivity().findViewById(R.id.cognome);
        mail = getActivity().findViewById(R.id.email);
        password = getActivity().findViewById(R.id.password);
        codFisc = getActivity().findViewById(R.id.codiceFiscale);
        matricola = getActivity().findViewById(R.id.matricola);
        media = getActivity().findViewById(R.id.media);
        numeroEsamiMancanti = getActivity().findViewById(R.id.esamiMancanti);
        spinnerUniversita = getActivity().findViewById(R.id.universita);
        spinnerCorsoStudi = getActivity().findViewById(R.id.corsiDiStudio);
        registerButton = getActivity().findViewById(R.id.conferma);

        String queryValori = "SELECT " + Database.UNIVERSITA_NOME + " FROM " + Database.UNIVERSITA + ";";
        String queryValoreRegistrato = "SELECT u." + Database.UNIVERSITA_NOME + " " +
                "FROM " + Database.UNIVERSITACORSO + " uc , " + Database.UNIVERSITA + " u " +
                "WHERE uc." + Database.UNIVERSITACORSO_UNIVERSITAID + "=u." + Database.UNIVERSITA_ID + " AND uc." + Database.UNIVERSITACORSO_ID + " = '"+ Utility.tesistaLoggato.getIdUniversitaCorso() +"';";
        spinnerCreate(spinnerUniversita, queryValori, queryValoreRegistrato);
    }

    /**
     * Riempie i campi vuoti con il giusto valore se sono vuoti
     */
    private void FillAllEmpty(){
        Utility.fillIfEmpty(nome, Utility.tesistaLoggato.getNome());
        Utility.fillIfEmpty(cognome, Utility.tesistaLoggato.getCognome());
        Utility.fillIfEmpty(mail, Utility.tesistaLoggato.getEmail());
        Utility.fillIfEmpty(password, Utility.tesistaLoggato.getPassword());
        Utility.fillIfEmpty(codFisc, Utility.tesistaLoggato.getCodiceFiscale());
        Utility.fillIfEmpty(matricola, Utility.tesistaLoggato.getMatricola());
        Utility.fillIfEmpty(media, String.valueOf(Utility.tesistaLoggato.getMedia()));
        Utility.fillIfEmpty(numeroEsamiMancanti, String.valueOf(Utility.tesistaLoggato.getNumeroEsamiMancanti()));
    }

    /**
     * Crea lo spinner specifico passato per parametro e setta i valori e il valore da mostrare in base alle query
     * @param spinner
     * @param queryValori
     * @param queryValoriRegistrati
     */
    private void spinnerCreate(Spinner spinner, String queryValori, String queryValoriRegistrati){
        //Carica i nomi
        Cursor cursor = db.getReadableDatabase().rawQuery(queryValori,null);
        List<String> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String item = cursor.getString(0);
            items.add(item);
        }
        cursor.close();

        //Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Setta valori originali
        SettaValoreSpinnerRegistrato(spinner, queryValoriRegistrati, adapter);
    }

    /**
     * Setta il valore dello spinner da mostrare
     * @param spinner
     * @param query
     * @param adapter
     */
    private void SettaValoreSpinnerRegistrato(Spinner spinner, String query, ArrayAdapter<String> adapter){
        Cursor cursor = db.RicercaDato(query);
        if(cursor.moveToNext()){
            spinner.setSelection(adapter.getPosition(cursor.getString(0)));
        }
    }

    /**
     * Recupera l'id dal database del valore dello spinner selezionato
     * @param spinner
     * @param tabella
     * @return
     */
    private String RecuperaIdSpinner(Spinner spinner, String tabella){
        Cursor idCursor;
        idCursor = db.RicercaDato("SELECT id FROM "+ tabella +" WHERE nome = '"+ spinner.getSelectedItem().toString() +"';");
        idCursor.moveToNext();
        return idCursor.getString(0);
    }

    /**
     * Recupera l'id della coppia Universita-corso dal database
     * @param idUniversita
     * @param idCorso
     * @return
     */
    private int RecuperaUniversitaCorso(String idUniversita, String idCorso){
        Cursor idCursor;
        idCursor = db.RicercaDato("SELECT " + Database.UNIVERSITACORSO_ID + " FROM " + Database.UNIVERSITACORSO + " WHERE " + Database.UNIVERSITACORSO_UNIVERSITAID + " = '"+ idUniversita +"' AND " + Database.UNIVERSITACORSO_CORSOID + " = '"+ idCorso +"';");
        idCursor.moveToNext();
        return idCursor.getInt(idCursor.getColumnIndexOrThrow(Database.UNIVERSITACORSO_ID));
    }

    /**
     * Gestisce lo spinner dei corsi in base al valore dello spinner dell'università
     * @param spinner
     */
    private void GestisciSpinner(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idUniversita = RecuperaIdSpinner(spinner, Database.UNIVERSITA);

                //Recupera id dei corsi dell'universita scelta
                Cursor risultato = db.RicercaDato("SELECT " + Database.UNIVERSITACORSO_CORSOID + " FROM " + Database.UNIVERSITACORSO + " WHERE " + Database.UNIVERSITACORSO_UNIVERSITAID + " = '"+ idUniversita +"';");
                List<String> idRisultati = new ArrayList<>();
                while(risultato.moveToNext()){
                    idRisultati.add(risultato.getString(risultato.getColumnIndexOrThrow(Database.UNIVERSITACORSO_CORSOID)));
                }

                //Crea lo spinner dei corsi studio
                String query = "SELECT " + Database.CORSOSTUDI_NOME + " FROM " + Database.CORSOSTUDI + " WHERE " + Database.CORSOSTUDI_ID + " IN (" + idRisultati.toString().replace("[", "").replace("]", "") + ");";
                String queryValoreRegistrato = "SELECT cs." + Database.CORSOSTUDI_NOME + " " +
                        "FROM " + Database.UNIVERSITACORSO + " uc , " + Database.CORSOSTUDI + " cs " +
                        "WHERE uc." + Database.UNIVERSITACORSO_CORSOID + "=cs." + Database.CORSOSTUDI_ID + " AND uc." + Database.UNIVERSITACORSO_ID + " = '"+ Utility.tesistaLoggato.getIdUniversitaCorso() +"';";
                spinnerCreate(spinnerCorsoStudi, query, queryValoreRegistrato);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}