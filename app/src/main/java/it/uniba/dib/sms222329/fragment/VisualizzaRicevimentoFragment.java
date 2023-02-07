package it.uniba.dib.sms222329.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RicevimentoDatabase;
import it.uniba.dib.sms222329.fragment.relatore.HomeFragment;

public class VisualizzaRicevimentoFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Ricevimento richiesta;

    //View Items
    private TextView messaggioTesista;
    private TextView taskRicevimento;
    private TextView dataRicevimento;
    private Button accetta;
    private Button rifiuta;
    private TextView stato;

    public VisualizzaRicevimentoFragment(Ricevimento richiesta) {
        this.richiesta = richiesta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_richiesta_ricevimento, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetTextAll();

    }


    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        messaggioTesista = getView().findViewById(R.id.messaggio_tesista);
        taskRicevimento = getView().findViewById(R.id.task_ricevimento);
        dataRicevimento = getView().findViewById(R.id.data);
        accetta = getView().findViewById(R.id.accetta);
        rifiuta = getView().findViewById(R.id.rifiuta);
        stato = getView().findViewById(R.id.riorganizza);

        accetta.setVisibility(View.GONE);
        rifiuta.setVisibility(View.GONE);
    }

    private void SetTextAll() {
        messaggioTesista.setText(richiesta.getMessaggio());
        Cursor cursor = db.RicercaDato("SELECT " + Database.TASK_TITOLO + ", " + Database.TASK_DESCRIZIONE + " FROM " + Database.TASK +
                " WHERE " + Database.TASK_ID + "=" + richiesta.getIdTask() + ";");
        cursor.moveToFirst();
        taskRicevimento.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.TASK_TITOLO)) + "\n" + cursor.getString(cursor.getColumnIndexOrThrow(Database.TESI_ARGOMENTO)));
        dataRicevimento.setText(richiesta.getData().format(Utility.showDate) + " " + richiesta.getOrario().format(Utility.showDate));

        if(richiesta.getAccettazione()==Ricevimento.ACCETTATO){
            stato.setText("Accettato");
        } else if(richiesta.getAccettazione()==Ricevimento.RIFIUTATO){
            stato.setText("Rifiutato");
        } else if(richiesta.getAccettazione()==Ricevimento.IN_ATTESA_RELATORE){
            stato.setText("In attesa risposta relatore");
        } else if(richiesta.getAccettazione()==Ricevimento.IN_ATTESA_TESISTA){
            stato.setText("In attesa risposta tesista");
        }
    }
}