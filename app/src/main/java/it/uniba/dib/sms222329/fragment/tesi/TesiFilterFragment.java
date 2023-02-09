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
        spinnerCreate(campoOrdinamento);

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
            if(tesiCompletate){
                AddToQueryCompletata();
            } else {
                AddToQueryDisponibilita();
            }

            //Ordinamento
            AddToQueryOrderBy();
            AddToQueryOrderType();

            this.dismiss();
            if (tesiCompletate) {
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TesiSceltaListaGuestFragment(query));
            } else {
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TesiListaFragment(query));
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
        avviaRicerca = getView().findViewById(R.id.avviaRicerca); //ok

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
     */
    private void AddToQueryRelatore(){
        try {
            Cursor cursor = db.RicercaDato("SELECT " + Database.RELATORE_ID + " FROM " + Database.RELATORE +
                    " WHERE " + Database.RELATORE_MATRICOLA + "=" + relatore.getText().toString() + ";");
            cursor.moveToNext();

            query += " t." + Database.TESI_RELATOREID + "=" + cursor.getInt(0) + " AND";
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Relatore non esistente", Toast.LENGTH_SHORT).show();
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
        query += " t." + Database.TESI_TEMPISTICHE + ">" + tempistiche.getText().toString() + " AND";
    }

    /**
     * Il metodo aggiunge alla query il vincolo della media minima
     */
    private void AddToQueryMedia(){
        query += " t." + Database.TESI_MEDIAVOTOMINIMA + ">" + media.getText().toString() + " AND";
    }

    /**
     * Il metodo aggiunge alla query il vincolo del numero degli esami mancanti minimo
     */
    private void AddToQueryNumeroEsamiMancanti(){
        query += " t." + Database.TESI_ESAMINECESSARI + ">" + numeroEsamiMancanti.getText().toString() + " AND";
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
        if (campoOrdinamento.getSelectedItem().toString().compareTo("Titolo") == 0){
            query += " ORDER BY t." + Database.TESI_TITOLO;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo("Tempistiche") == 0){
            query += " ORDER BY t." + Database.TESI_TEMPISTICHE;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo("Media") == 0){
            query += " ORDER BY t." + Database.TESI_MEDIAVOTOMINIMA;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo("Esami Necessari") == 0){
            query += " ORDER BY t." + Database.TESI_ESAMINECESSARI;
        } else if (campoOrdinamento.getSelectedItem().toString().compareTo("Visualizzazioni") == 0){
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