package it.uniba.dib.sms222329.fragment.tesi;

import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.database.Database;

public class TesiFilterFragment extends BottomSheetDialogFragment {

    //Variabili e Oggetti

    //View Items
    private EditText relatore;
    private EditText argomento;
    private EditText tempistiche;
    private EditText media;
    private EditText numeroEsamiMancanti;
    private SwitchMaterial disponibilita;
    private Spinner campoOrdinamento;
    private SwitchMaterial ordinaAscendente;
    private Button avviaRicerca;

    public TesiFilterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi_filter, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        spinnerCreate(campoOrdinamento);

        avviaRicerca.setOnClickListener(view -> {
            Database db = new Database(getActivity().getApplicationContext());
            String query = "SELECT * FROM " + Database.TESI + " WHERE ";

            //Filtri
            if(!Utility.isEmptyTextbox(relatore)){
                query = AddToQueryRelatore(query, relatore, db);
            }
            if(!Utility.isEmptyTextbox(argomento)){
                query = AddToQueryArgomento(query, argomento);
            }
            if(!Utility.isEmptyTextbox(tempistiche)){
                query = AddToQueryTempistiche(query, tempistiche);
            }
            if(!Utility.isEmptyTextbox(media)){
                query = AddToQueryMedia(query, media);
            }
            if(!Utility.isEmptyTextbox(numeroEsamiMancanti)){
                query = AddToQueryNumeroEsamiMancanti(query, numeroEsamiMancanti);
            }
            query = AddToQueryDisponibilita(query, disponibilita);

            //Ordinamento
            query = AddToQueryOrderBy(query, campoOrdinamento);
            query = AddToQueryOrderType(query, ordinaAscendente);

            this.dismiss();
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.content2, new TesiListaFragment(query));
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        relatore = getView().findViewById(R.id.relatore);
        argomento = getView().findViewById(R.id.argomento);
        tempistiche = getView().findViewById(R.id.tempistiche);
        media = getView().findViewById(R.id.media);
        numeroEsamiMancanti = getView().findViewById(R.id.numeroEsamiMancanti);
        disponibilita = getView().findViewById(R.id.disponibilita);
        campoOrdinamento = getView().findViewById(R.id.ordinaper);
        ordinaAscendente = getView().findViewById(R.id.ordina);
        avviaRicerca = getView().findViewById(R.id.avviaRicerca); //ok

        if(Utility.accountLoggato == Utility.RELATORE){
            relatore.setText(String.valueOf(Utility.relatoreLoggato.getMatricola()), EditText.BufferType.EDITABLE);
        }
        disponibilita.setChecked(true);
    }

    /**
     * Crea lo spinner per l'ordinamento della lista delle proposte di tesi
     * @param spinner
     */
    private void spinnerCreate(Spinner spinner){
        List<String> items = new ArrayList<>();
        items.add("Titolo");
        items.add("Tempistiche");
        items.add("Media");
        items.add("Esami Necessari");
        items.add("Visualizzazioni");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Il metodo aggiunge alla query il vincolo del relatore se esiste
     * @param query
     * @param relatore
     * @param db
     * @return  Restituisce la query aggiornata
     */
    private String AddToQueryRelatore(String query, EditText relatore, Database db){
        try {
            Cursor cursor = db.RicercaDato("SELECT " + Database.RELATORE_ID + " FROM " + Database.RELATORE +
                    " WHERE " + Database.RELATORE_MATRICOLA + "=" + relatore.getText().toString() + ";");
            cursor.moveToNext();

            query += " " + Database.TESI_RELATOREID + "=" + cursor.getInt(0) + " AND";
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Relatore non esistente", Toast.LENGTH_SHORT).show();
        }
        return query;
    }

    /**
     * Il metodo aggiunge alla query il vincolo dell'argomento
     * @param query
     * @param argomento
     * @return  Restituisce la query aggiornata
     */
    private String AddToQueryArgomento(String query, EditText argomento){
        query += " " + Database.TESI_ARGOMENTO + "LIKE('" + argomento.getText().toString() + "') AND";
        return query;
    }

    /**
     * Il metodo aggiunge alla query il vincolo delle tempistiche minime
     * @param query
     * @param tempistiche
     * @return  Restituisce la query aggiornata
     */
    private String AddToQueryTempistiche(String query, EditText tempistiche){
        query += " " + Database.TESI_TEMPISTICHE + ">" + tempistiche.getText().toString() + " AND";
        return query;
    }

    /**
     * Il metodo aggiunge alla query il vincolo della media minima
     * @param query
     * @param media
     * @return  Restituisce la query aggiornata
     */
    private String AddToQueryMedia(String query, EditText media){
        query += " " + Database.TESI_MEDIAVOTOMINIMA + ">" + media.getText().toString() + " AND";
        return query;
    }

    /**
     * Il metodo aggiunge alla query il vincolo del numero degli esami mancanti minimo
     * @param query
     * @param numeroEsamiMancanti
     * @return  Restituisce la query aggiornata
     */
    private String AddToQueryNumeroEsamiMancanti(String query, EditText numeroEsamiMancanti){
        query += " " + Database.TESI_ESAMINECESSARI + ">" + numeroEsamiMancanti.getText().toString() + " AND";
        return query;
    }

    /**
     * Il metodo aggiunge alla query il vincolo della disponibilità
     * @param query
     * @param disponibilita
     * @return  Restituisce la query aggiornata
     */
    private String AddToQueryDisponibilita(String query, SwitchMaterial disponibilita){
        if(disponibilita.isChecked()){
            query += " " + Database.TESI_STATO + "=1 "; //TRUE
        } else {
            query += " " + Database.TESI_STATO + "=0 "; //FALSE
        }
        return query;
    }

    /**
     * Il metodo aggiunge alla query l'ordinamento in base al campo selezionato
     * @param query
     * @param campoOrdinamento
     * @return  Restituisce la query aggiornata
     */
    private String AddToQueryOrderBy(String query, Spinner campoOrdinamento) {
        if (campoOrdinamento.getSelectedItem().toString().compareTo("Titolo") == 0){
            query += " ORDER BY " + Database.TESI_TITOLO;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo("Tempistiche") == 0){
            query += " ORDER BY " + Database.TESI_TEMPISTICHE;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo("Media") == 0){
            query += " ORDER BY " + Database.TESI_MEDIAVOTOMINIMA;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo("Esami Necessari") == 0){
            query += " ORDER BY " + Database.TESI_ESAMINECESSARI;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo("Visualizzazioni") == 0){
            query += " ORDER BY " + Database.TESI_VISUALIZZAZIONI;
        }
        return query;
    }

    /**
     * Il metodo aggiunge alla query il tipo  di ordinamento selezionato
     * @param query
     * @param ordinaAscendente
     * @return
     */
    private String AddToQueryOrderType(String query, SwitchMaterial ordinaAscendente) {
        if(ordinaAscendente.isChecked()){
            query += " ASC;";
        } else {
            query += " DESC;";
        }
        return query;
    }
}