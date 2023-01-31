package it.uniba.dib.sms222329.fragment.signUp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
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
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesistaDatabase;

public class ModificaProfiloStudenteFragment extends Fragment {
    private Database db;
    private Tesista tesista;

    public ModificaProfiloStudenteFragment(Database db, Tesista tesistaLoggato) {
        this.db = db;
        this.tesista = tesistaLoggato;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modifica_profilo_studente, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Crea lo spinner
        String query = "SELECT " + Database.UNIVERSITA_NOME + " FROM " + Database.UNIVERSITA + ";";
        String queryValoreRegistrato = "SELECT u." + Database.UNIVERSITA_NOME + " " +
                "FROM " + Database.UNIVERSITACORSO + " uc , " + Database.UNIVERSITA + " u " +
                "WHERE uc." + Database.UNIVERSITACORSO_UNIVERSITAID + "=u." + Database.UNIVERSITA_ID + " AND uc." + Database.UNIVERSITACORSO_ID + " = '"+ tesista.getIdUniversitaCorso() +"';";
        spinnerCreate(R.id.universita, query, queryValoreRegistrato);

        //Reference agli elementi dell'interfaccia
        EditText nome = getActivity().findViewById(R.id.nome);
        EditText cognome = getActivity().findViewById(R.id.cognome);
        EditText mail = getActivity().findViewById(R.id.email);
        EditText password = getActivity().findViewById(R.id.password);
        EditText codFisc = getActivity().findViewById(R.id.codiceFiscale);
        EditText matricola = getActivity().findViewById(R.id.matricola);
        TextInputEditText media = getActivity().findViewById(R.id.media);
        TextInputEditText numeroEsamiMancanti = getActivity().findViewById(R.id.esamiMancanti);
        Spinner spinnerUniversita = getActivity().findViewById(R.id.universita);
        Spinner spinnerCorsoStudi = getActivity().findViewById(R.id.corsiDiStudio);
        View registerButton = getActivity().findViewById(R.id.conferma);

        //Carica come suggerimenti i valori attuali del profilo
        nome.setHint(tesista.getNome());
        cognome.setHint(tesista.getCognome());
        mail.setHint(tesista.getEmail());
        password.setHint(tesista.getPassword());
        codFisc.setHint(tesista.getCodiceFiscale());
        matricola.setHint(tesista.getMatricola());
        media.setHint(String.valueOf(tesista.getMedia()));
        numeroEsamiMancanti.setHint(String.valueOf(tesista.getNumeroEsamiMancanti()));

        //Gestisci gli spinner
        GestisciSpinner(spinnerUniversita);

        registerButton.setOnClickListener(view -> {
            //Riempie i campi non modificati con i valori esistenti
            fillIfEmpty(nome, tesista.getNome());
            fillIfEmpty(cognome, tesista.getCognome());
            fillIfEmpty(mail, tesista.getEmail());
            fillIfEmpty(password, tesista.getPassword());
            fillIfEmpty(codFisc, tesista.getCodiceFiscale());
            fillIfEmpty(matricola, tesista.getMatricola());
            fillIfEmpty(media, String.valueOf(tesista.getMedia()));
            fillIfEmpty(numeroEsamiMancanti, String.valueOf(tesista.getNumeroEsamiMancanti()));

            //Recupera id coppia UniversitaCorso
            String idUniversita = RecuperaIdSpinner(spinnerUniversita, Database.UNIVERSITA);
            String idCorsoStudio = RecuperaIdSpinner(spinnerCorsoStudi,Database.CORSOSTUDI);
            int corso= RecuperaUniversitaCorso(idUniversita, idCorsoStudio);

            //Modifica
            tesista.modTesista(matricola.getText().toString(), nome.getText().toString(),
                    cognome.getText().toString(), mail.getText().toString(),
                    password.getText().toString(),Float.parseFloat(media.getText().toString()),
                    Integer.parseInt(numeroEsamiMancanti.getText().toString()),
                    codFisc.getText().toString(), corso);
            if (TesistaDatabase.modTesista(tesista, db)){
                Toast.makeText(getActivity().getApplicationContext(),"modifica riuscita",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void spinnerCreate(int idSpinner, String queryValori, String queryValoriRegistrati){
        Spinner spinner = getView().findViewById(idSpinner);

        Cursor cursor = db.getReadableDatabase().rawQuery(queryValori,null);

        // Create an array of strings using the data from the Cursor
        List<String> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String item = cursor.getString(0);
            items.add(item);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        SettaValoreSpinnerRegistrato(spinner, queryValoriRegistrati, adapter);
    }

    private void SettaValoreSpinnerRegistrato(Spinner spinner, String query, ArrayAdapter<String> adapter){
        Cursor cursor = db.RicercaDato(query);
        if(cursor.moveToNext()){
            spinner.setSelection(adapter.getPosition(cursor.getString(0)));
        }
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
                String queryValoreRegistrato = "SELECT cs." + Database.CORSOSTUDI_NOME + " " +
                        "FROM " + Database.UNIVERSITACORSO + " uc , " + Database.CORSOSTUDI + " cs " +
                        "WHERE uc." + Database.UNIVERSITACORSO_CORSOID + "=cs." + Database.CORSOSTUDI_ID + " AND uc." + Database.UNIVERSITACORSO_ID + " = '"+ tesista.getIdUniversitaCorso() +"';";
                spinnerCreate(R.id.corsiDiStudio, query, queryValoreRegistrato);
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