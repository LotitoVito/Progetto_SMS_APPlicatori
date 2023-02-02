package it.uniba.dib.sms222329.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.ListaTesi;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.relatore.GestioneTesiFragment;
import it.uniba.dib.sms222329.fragment.relatore.HomeFragment;
import it.uniba.dib.sms222329.fragment.relatore.TesiFragment;

public class TesiFilterFragment extends BottomSheetDialogFragment {
    private Relatore relatoreLoggato;

    public TesiFilterFragment(Relatore relatoreLoggato) {
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi_filter, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        EditText relatore = getView().findViewById(R.id.relatore);
        EditText argomento = getView().findViewById(R.id.argomento);
        EditText tempistiche = getView().findViewById(R.id.tempistiche);
        EditText media = getView().findViewById(R.id.media);
        EditText numeroEsamiMancanti = getView().findViewById(R.id.numeroEsamiMancanti);
        SwitchMaterial disponibilita = getView().findViewById(R.id.disponibilita);
        Spinner campoOrdinamento = getView().findViewById(R.id.ordinaper);
        SwitchMaterial ordinaAscendente = getView().findViewById(R.id.ordina);
        Button avviaRicerca = getView().findViewById(R.id.avviaRicerca); //ok
        spinnerCreate(campoOrdinamento); //ordinaper

        if(relatoreLoggato != null){
            relatore.setText(String.valueOf(relatoreLoggato.getIdRelatore()), EditText.BufferType.EDITABLE);
        }

        avviaRicerca.setOnClickListener(view -> {
            Database db = new Database(getActivity().getApplicationContext());
            String query = "SELECT * FROM " + Database.TESI + " WHERE";

            //Filtri
            if(!isEmptyTextbox(relatore)){
                query = AddToQueryRelatore(query, relatore, db);
            }
            if(!isEmptyTextbox(argomento)){
                query = AddToQueryArgomento(query, argomento);
            }
            if(!isEmptyTextbox(tempistiche)){
                query = AddToQueryTempistiche(query, tempistiche);
            }
            if(!isEmptyTextbox(media)){
                query = AddToQueryMedia(query, media);
            }
            if(!isEmptyTextbox(numeroEsamiMancanti)){
                query = AddToQueryNumeroEsamiMancanti(query, numeroEsamiMancanti);
            }
            query = AddToQueryDisponibilita(query, disponibilita);

            //Ordinamento
            query = AddToQueryOrderBy(query, campoOrdinamento);
            query = AddToQueryOrderType(query, ordinaAscendente);

            this.dismiss();
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.content2, new TesiFragment(relatoreLoggato, query));
        });
    }



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

    private boolean isEmptyTextbox(EditText textbox){
        if(textbox.getText().toString().trim().compareTo("")==0){
            return true;
        }
        return false;
    }

    private String EndQuery(String query){
        query += ";";
        return query;
    }

    private String AddToQueryRelatore(String query, EditText relatore, Database db){
        Cursor cursor = db.RicercaDato("SELECT " + Database.RELATORE_ID + " FROM " + Database.RELATORE +
                " WHERE " + Database.RELATORE_MATRICOLA + "=" + relatore.getText().toString() + ";");
        cursor.moveToNext();

        query += " " + Database.TESI_RELATOREID + "=" + cursor.getInt(0) + " AND";
        return query;
    }

    private String AddToQueryArgomento(String query, EditText argomento){
        query += " " + Database.TESI_ARGOMENTO + "LIKE('" + argomento.getText().toString() + "') AND";
        return query;
    }

    private String AddToQueryTempistiche(String query, EditText tempistiche){
        query += " " + Database.TESI_TEMPISTICHE + ">" + tempistiche.getText().toString() + " AND";
        return query;
    }

    private String AddToQueryMedia(String query, EditText media){
        query += " " + Database.TESI_MEDIAVOTOMINIMA + ">" + media.getText().toString() + " AND";
        return query;
    }

    private String AddToQueryNumeroEsamiMancanti(String query, EditText numeroEsamiMancanti){
        query += " " + Database.TESI_ESAMINECESSARI + ">" + numeroEsamiMancanti.getText().toString() + " AND";
        return query;
    }

    private String AddToQueryDisponibilita(String query, SwitchMaterial disponibilita){
        if(disponibilita.isChecked()){
            query += " " + Database.TESI_STATO + "=1 "; //TRUE
        } else {
            query += " " + Database.TESI_STATO + "=0 "; //FALSE
        }
        return query;
    }

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

    private String AddToQueryOrderType(String query, SwitchMaterial ordinaAscendente) {
        if(ordinaAscendente.isChecked()){
            query += " ASC;";
        } else {
            query += " DESC;";
        }
        return query;
    }
}