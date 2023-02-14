package it.uniba.dib.sms222329.fragment.tesi;

import android.database.Cursor;
import android.os.Bundle;

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

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.tesiscelta.TesiSceltaListaGuestFragment;

public class TesiFilterFragment extends BottomSheetDialogFragment {

    //Variabili e Oggetti
    private Database db;
    private boolean tesiCompletate;
    private String query;

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
    private Spinner universita;
    private Spinner corso;

    public TesiFilterFragment(boolean tesiCompletate) {
        this.tesiCompletate = tesiCompletate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi_filter, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        spinnerOrdinamentoCreate();
        spinnerCreate(universita, "SELECT " + Database.UNIVERSITA_NOME + " FROM " + Database.UNIVERSITA + ";");
        GestisciSpinner();

        avviaRicerca.setOnClickListener(view -> {
            if(tesiCompletate){
                query = "SELECT ts." + Database.TESISCELTA_ID + ", ts." + Database.TESISCELTA_DATAPUBBLICAZIONE + ", ts." + Database.TESISCELTA_ABSTRACT + ", ts." + Database.TESISCELTA_DOWNLOAD + ", ts." + Database.TESISCELTA_TESIID + ", ts." + Database.TESISCELTA_CORELATOREID + ", ts." + Database.TESISCELTA_STATOCORELATORE + ", ts." + Database.TESISCELTA_TESISTAID + ", ts." + Database.TESISCELTA_CAPACITATESISTA + ", t." + Database.TESI_TITOLO +
                        " FROM " + Database.TESISCELTA + " ts, " + Database.TESI + " t " +
                        " WHERE t." + Database.TESI_ID + "=ts." + Database.TESISCELTA_TESIID + " AND ";
            } else {
                query = "SELECT * FROM " + Database.TESI + " t WHERE ";
            }

            //Filtri
            if(!Utility.isEmptyTextbox(relatore)){
                AddToQueryRelatore();
            }
            if(!Utility.isEmptyTextbox(argomento)){
                AddToQueryArgomento();
            }
            if(!Utility.isEmptyTextbox(tempistiche)){
                AddToQueryTempistiche();
            }
            if(!Utility.isEmptyTextbox(media)){
                AddToQueryMedia();
            }
            if(!Utility.isEmptyTextbox(numeroEsamiMancanti)){
                AddToQueryNumeroEsamiMancanti();
            }
            AddToQueryUniversitaCorso();
            if(tesiCompletate){
                AddToQueryCompletata();
            } else {
                AddToQueryDisponibilita();
            }

            //Ordinamento
            AddToQueryOrderBy();
            AddToQueryOrderType();

            Log.d("test", query);

            this.dismiss();
            if (tesiCompletate) {
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TesiSceltaListaGuestFragment(query));
            } else {
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.content2, new TesiListaFragment(query));
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        relatore = getView().findViewById(R.id.relatore);
        argomento = getView().findViewById(R.id.argomento);
        tempistiche = getView().findViewById(R.id.tempistiche);
        media = getView().findViewById(R.id.media);
        numeroEsamiMancanti = getView().findViewById(R.id.numeroEsamiMancanti);
        disponibilita = getView().findViewById(R.id.disponibilita);
        campoOrdinamento = getView().findViewById(R.id.ordinaper);
        ordinaAscendente = getView().findViewById(R.id.ordina);
        universita = getView().findViewById(R.id.universita_filtro);
        corso = getView().findViewById(R.id.corso_filtro);
        avviaRicerca = getView().findViewById(R.id.avviaRicerca);

        if(Utility.accountLoggato == Utility.RELATORE){
            relatore.setText(String.valueOf(Utility.relatoreLoggato.getMatricola()), EditText.BufferType.EDITABLE);
        }
        if(tesiCompletate){
            disponibilita.setVisibility(View.GONE);
        }
        disponibilita.setChecked(true);
    }

    /**
     * Crea lo spinner per l'ordinamento della lista delle proposte di tesi
     */
    private void spinnerOrdinamentoCreate(){
        List<String> items = new ArrayList<>();
        items.add(getResources().getString(R.string.titolo));
        items.add(getResources().getString(R.string.tempistiche));
        items.add(getResources().getString(R.string.media));
        items.add(getResources().getString(R.string.numero_esami_mancanti));
        items.add(getResources().getString(R.string.visualizzazioni));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoOrdinamento.setAdapter(adapter);
    }

    /**
     * Crea lo spinner passato come parametro con i valori della query, usato per universita e corso
     * @param spinner
     * @param query
     */
    private void spinnerCreate(Spinner spinner, String query){
        //Recupera i nomi dalla query
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        List<String> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String item = cursor.getString(0);
            items.add(item);
        }
        cursor.close();

        //Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Gestisce lo spinner università cambiando i valori dello spinner corsi in base al valore selezionato
     */
    private void GestisciSpinner(){
        universita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int idUniversita = RecuperaIdSpinner(universita, Database.UNIVERSITA);

                //Recupera id corsi in base all'universita scelta
                Cursor risultato = db.RicercaDato("SELECT " + Database.UNIVERSITACORSO_CORSOID + " FROM " + Database.UNIVERSITACORSO + " WHERE " + Database.UNIVERSITACORSO_UNIVERSITAID + " = "+ idUniversita +";");
                List<String> idRisultati = new ArrayList<>();
                while(risultato.moveToNext()){
                    idRisultati.add(risultato.getString(0));
                }

                //Crea spinner corsi
                String query = "SELECT " + Database.CORSOSTUDI_NOME + " FROM " + Database.CORSOSTUDI + " WHERE " + Database.CORSOSTUDI_ID + " IN (" + idRisultati.toString().replace("[", "").replace("]", "") + ");";
                spinnerCreate(corso, query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    /**
     * Recupera l'id dal database del valore dello spinner selezionato
     * @param spinner
     * @param tabella
     * @return  Restituisce l'id del valore selezionato
     */
    private int RecuperaIdSpinner(Spinner spinner, String tabella){
        Cursor idCursor;
        idCursor = db.RicercaDato("SELECT id FROM "+ tabella +" WHERE nome = '"+ spinner.getSelectedItem().toString() +"';");
        idCursor.moveToNext();
        return idCursor.getInt(0);
    }

    /**
     * Il metodo aggiunge alla query il vincolo del relatore se esiste
     */
    private void AddToQueryRelatore(){
        try {
            Cursor cursor = db.RicercaDato("SELECT r." + Database.RELATORE_ID + " " +
                    "FROM " + Database.RELATORE + " r, " + Database.UTENTI + " u " +
                    " WHERE r." + Database.RELATORE_UTENTEID + "=u." + Database.UTENTI_ID +
                    " AND " + Database.UTENTI_EMAIL + " LIKE('" + relatore.getText().toString() + "');");
            cursor.moveToNext();

            query += " t." + Database.TESI_RELATOREID + "=" + cursor.getInt(0) + " AND";
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), R.string.relatore_non_esiste, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Il metodo aggiunge alla query il vincolo dell'argomento
     */
    private void AddToQueryArgomento(){
        query += " t." + Database.TESI_ARGOMENTO + " LIKE('" + argomento.getText().toString() + "') AND";
    }

    /**
     * Il metodo aggiunge alla query il vincolo delle tempistiche minime
     */
    private void AddToQueryTempistiche(){
        query += " t." + Database.TESI_TEMPISTICHE + ">=" + tempistiche.getText().toString() + " AND";
    }

    /**
     * Il metodo aggiunge alla query il vincolo della media minima
     */
    private void AddToQueryMedia(){
        query += " t." + Database.TESI_MEDIAVOTOMINIMA + ">=" + media.getText().toString() + " AND";
    }

    /**
     * Il metodo aggiunge alla query il vincolo del numero degli esami mancanti minimo
     */
    private void AddToQueryNumeroEsamiMancanti(){
        query += " t." + Database.TESI_ESAMINECESSARI + ">=" + numeroEsamiMancanti.getText().toString() + " AND";
    }

    /**
     * Il metodo aggiunge alla query il tipo  di ordinamento selezionato
     */
    private void AddToQueryUniversitaCorso() {
        int idUniversita = RecuperaIdSpinner(universita, Database.UNIVERSITA);
        int idCorso = RecuperaIdSpinner(corso,Database.CORSOSTUDI);
        Cursor cursor = db.RicercaDato("SELECT " + Database.UNIVERSITACORSO_ID + " FROM " + Database.UNIVERSITACORSO +
                " WHERE " + Database.UNIVERSITACORSO_CORSOID + "=" + idCorso + " AND " + Database.UNIVERSITACORSO_UNIVERSITAID + "=" + idUniversita + ";");
        if(cursor.moveToFirst()){
            query += " t." + Database.TESI_UNIVERSITACORSOID + "=" + cursor.getInt(cursor.getColumnIndexOrThrow(Database.UNIVERSITACORSO_ID)) + " AND ";
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Non esistono tesi di questo corso", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Il metodo aggiunge alla query il vincolo della disponibilità
     */
    private void AddToQueryDisponibilita(){
        if(disponibilita.isChecked()){
            query += " t." + Database.TESI_STATO + "=1 "; //TRUE
        } else {
            query += " t." + Database.TESI_STATO + "=0 "; //FALSE
        }
    }

    /**
     * Il metodo aggiunge alla query l'ordinamento in base al campo selezionato
     */
    private void AddToQueryOrderBy() {
        if (campoOrdinamento.getSelectedItem().toString().compareTo(getResources().getString(R.string.titolo)) == 0){
            query += " ORDER BY t." + Database.TESI_TITOLO;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo(getResources().getString(R.string.tempistiche)) == 0){
            query += " ORDER BY t." + Database.TESI_TEMPISTICHE;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo(getResources().getString(R.string.media)) == 0){
            query += " ORDER BY t." + Database.TESI_MEDIAVOTOMINIMA;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo(getResources().getString(R.string.numero_esami_mancanti)) == 0){
            query += " ORDER BY t." + Database.TESI_ESAMINECESSARI;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo(getResources().getString(R.string.visualizzazioni)) == 0){
            query += " ORDER BY t." + Database.TESI_VISUALIZZAZIONI;
        }
    }

    /**
     * Il metodo aggiunge alla query il vincolo di tesi completata
     */
    private void AddToQueryCompletata() {
        query += " ts." + Database.TESISCELTA_DATAPUBBLICAZIONE + "!='' ";
    }

    /**
     * Il metodo aggiunge alla query il tipo  di ordinamento selezionato
     */
    private void AddToQueryOrderType() {
        if(ordinaAscendente.isChecked()){
            query += " ASC;";
        } else {
            query += " DESC;";
        }
    }

}