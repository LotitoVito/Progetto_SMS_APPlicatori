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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificaProfiloStudenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificaProfiloStudenteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Database db;

    public ModificaProfiloStudenteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificaProfiloStudenteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificaProfiloStudenteFragment newInstance(String param1, String param2) {
        ModificaProfiloStudenteFragment fragment = new ModificaProfiloStudenteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modifica_profilo_studente, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        View registerButton = getActivity().findViewById(R.id.button);
        EditText nome = getActivity().findViewById(R.id.nome);
        EditText cognome = getActivity().findViewById(R.id.cognome);
        EditText mail = getActivity().findViewById(R.id.email);
        EditText password = getActivity().findViewById(R.id.password);
        EditText codFisc = getActivity().findViewById(R.id.codiceFiscale);
        EditText matricola = getActivity().findViewById(R.id.matricola);
        TextInputEditText media = getActivity().findViewById(R.id.media);
        TextInputEditText numeroEsamiMancanti = getActivity().findViewById(R.id.numeroEsamiMancanti);
        Spinner spinnerUniversita = getActivity().findViewById(R.id.universita);
        Spinner spinnerCorsoStudi = getActivity().findViewById(R.id.corsoDiStudi);

        GestisciSpinner(spinnerUniversita);

        registerButton.setOnClickListener(view -> {
            String idUniversita = RecuperaIdSpinner(spinnerUniversita, "universita");
            String idCorsoStudio = RecuperaIdSpinner(spinnerCorsoStudi,"corsoStudi");
            int corso= RecuperaUniversitaCorso(idUniversita, idCorsoStudio);

            Tesista tesista=new Tesista();
            if (tesista.modTesista(db, matricola.getText().toString(), nome.getText().toString(),
                                   cognome.getText().toString(), mail.getText().toString(),
                                   password.getText().toString(), Float.parseFloat(media.getText().toString()),
                                   Integer.parseInt(numeroEsamiMancanti.getText().toString()),
                                   codFisc.getText().toString(), corso)){
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
        idCursor = db.RicercaDato("SELECT id FROM universitacorso WHERE universita_id = '"+ idUniversita +"' AND corso_id = '"+ idCorso +"';");
        idCursor.moveToNext();
        return idCursor.getInt(0);
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
                spinnerCreate(R.id.corsoDiStudi, query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}