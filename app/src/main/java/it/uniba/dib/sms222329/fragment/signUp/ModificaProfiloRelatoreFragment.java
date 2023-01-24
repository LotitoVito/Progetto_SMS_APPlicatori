package it.uniba.dib.sms222329.fragment.signUp;

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
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RelatoreDatabase;

public class ModificaProfiloRelatoreFragment extends Fragment {
    private Database db = new Database(getActivity().getApplicationContext());
    private Relatore relatoreLoggato;

    public ModificaProfiloRelatoreFragment(Relatore relatoreLoggato) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modifica_profilo_relatore, container, false);
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
        Spinner universita = getActivity().findViewById(R.id.universita);
        GestisciSpinner(universita);

        Button conferma = getActivity().findViewById(R.id.conferma);

        conferma.setOnClickListener(view -> {
            String idUniversita = RecuperaIdSpinner(universita,"Universita");
            List idCorsiSelezionati = RecuperaIdCorsi();
            ArrayList<Integer> corsiRelatore = RecuperaUniversitaCorso(idUniversita,idCorsiSelezionati);

            relatoreLoggato.modRelatore(nome.getText().toString(),cognome.getText().toString(),
                    mail.getText().toString(),password.getText().toString(),
                    codFisc.getText().toString(),matricola.getText().toString(),
                    corsiRelatore);
            if (RelatoreDatabase.modRelatore(relatoreLoggato, db)) {
                Toast.makeText(getActivity().getApplicationContext(),"modifica riuscita",Toast.LENGTH_SHORT).show();
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
        idCursor = db.RicercaDato("SELECT ID FROM "+ tabella +" WHERE Nome = '"+ spinner.getSelectedItem().toString() +"';");
        idCursor.moveToNext();
        return idCursor.getString(0);
    }

    private List RecuperaIdCorsi(){
        ListView listView = getActivity().findViewById(R.id.corsiDiStudio);
        List<String> idCorsiSelezionati = new ArrayList<>();

        for (int i = 0; i < listView.getChildCount(); i++) {
            CheckBox checkBox = listView.getChildAt(i).findViewById(R.id.checkbox);
            if (checkBox.isChecked()) {
                String query = "SELECT id FROM corsoStudi WHERE nome LIKE '"+ checkBox.getText() +"';";
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
            idCursor = db.RicercaDato("SELECT id FROM universitacorso WHERE universita_id = '"+ idUniversita +"' AND corso_id = '"+ idCorsiSelezionati.get(i) +"';");
            idCursor.moveToNext();
            corsiRelatore.add(idCursor.getInt(0));
        }
        return corsiRelatore;
    }

    private void GestisciSpinner(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idUniversita = RecuperaIdSpinner(spinner, "universita");

                Cursor risultato = db.RicercaDato("SELECT corso_id FROM universitacorso WHERE universita_id = '"+ idUniversita +"';");
                List<String> idRisultati = new ArrayList<>();
                while(risultato.moveToNext()){
                    idRisultati.add(risultato.getString(0));
                }

                String query = "SELECT nome FROM corsoStudi WHERE id IN (" + idRisultati.toString().replace("[", "").replace("]", "") + ");";
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