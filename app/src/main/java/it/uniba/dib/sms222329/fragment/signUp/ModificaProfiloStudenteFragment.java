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

        EditText nome = getActivity().findViewById(R.id.nome);
        EditText cognome = getActivity().findViewById(R.id.cognome);
        EditText mail = getActivity().findViewById(R.id.email);
        EditText password = getActivity().findViewById(R.id.password);
        EditText codFisc = getActivity().findViewById(R.id.codiceFiscale);
        EditText matricola = getActivity().findViewById(R.id.matricola);
        TextInputEditText media = getActivity().findViewById(R.id.media);
        TextInputEditText numeroEsamiMancanti = getActivity().findViewById(R.id.esamiMancanti);
        Spinner spinnerUniversita = getActivity().findViewById(R.id.universita);
        Spinner spinnerCorsoStudi = getActivity().findViewById(R.id.corsoDiStudi);
        View registerButton = getActivity().findViewById(R.id.conferma);

        GestisciSpinner(spinnerUniversita);

        registerButton.setOnClickListener(view -> {
            String idUniversita = RecuperaIdSpinner(spinnerUniversita, Database.UNIVERSITA);
            String idCorsoStudio = RecuperaIdSpinner(spinnerCorsoStudi,Database.CORSOSTUDI);
            int corso= RecuperaUniversitaCorso(idUniversita, idCorsoStudio);

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

    private void spinnerCreate(int idSpinner,String query){
        Spinner spinner = getView().findViewById(idSpinner);

        // Query the database for the data
        Cursor cursor = db.getReadableDatabase().rawQuery(query,null);

        // Create an array of strings using the data from the Cursor
        List<String> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String item = cursor.getString(0);
            items.add(item);
        }
        cursor.close();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,items);

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
}