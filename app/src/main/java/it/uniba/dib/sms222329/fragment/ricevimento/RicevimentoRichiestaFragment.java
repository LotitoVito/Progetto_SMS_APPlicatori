package it.uniba.dib.sms222329.fragment.ricevimento;

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

public class RicevimentoRichiestaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Ricevimento richiesta;

    //View Items
    private TextView messaggioTesista;
    private TextView taskRicevimento;
    private TextView dataRicevimento;
    private Button accetta;
    private Button rifiuta;
    private TextView cambiaRicevimento;

    public RicevimentoRichiestaFragment(Ricevimento richiesta) {
        this.richiesta = richiesta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ricevimento_richiesta_visualizza, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetTextAll();

        accetta.setOnClickListener(view -> {
            if(RicevimentoDatabase.AccettaRicevimento(db, richiesta)){
                Toast.makeText(getActivity().getApplicationContext(), "Ricevimento accettato", Toast.LENGTH_SHORT).show();
                Utility.goBack(getActivity());
            }else{
                Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
            }
        });

        rifiuta.setOnClickListener(view -> {
            if(RicevimentoDatabase.RifiutaRicevimento(db, richiesta)){
                Toast.makeText(getActivity().getApplicationContext(), "Ricevimento rifiutato", Toast.LENGTH_SHORT).show();
                Utility.goBack(getActivity());
            } else{
                Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
            }
        });

        cambiaRicevimento.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new RicevimentoCreaFragment(richiesta));
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        messaggioTesista = getView().findViewById(R.id.messaggio_tesista);
        taskRicevimento = getView().findViewById(R.id.task_ricevimento);
        dataRicevimento = getView().findViewById(R.id.data);
        accetta = getView().findViewById(R.id.accetta);
        rifiuta = getView().findViewById(R.id.rifiuta);
        cambiaRicevimento = getView().findViewById(R.id.riorganizza);

        if(Utility.accountLoggato == Utility.TESISTA){
            cambiaRicevimento.setVisibility(View.GONE);
        }

    }

    /**
     * Imposta il testo per ogni elemento della view
     */
    private void SetTextAll() {
        messaggioTesista.setText(richiesta.getMessaggio());
        Cursor cursor = db.RicercaDato("SELECT " + Database.TASK_TITOLO + ", " + Database.TASK_DESCRIZIONE + " FROM " + Database.TASK +
                " WHERE " + Database.TASK_ID + "=" + richiesta.getIdTask() + ";");
        cursor.moveToFirst();
        taskRicevimento.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.TASK_TITOLO)) + "\n" + cursor.getString(cursor.getColumnIndexOrThrow(Database.TASK_DESCRIZIONE)));
        dataRicevimento.setText(richiesta.getData().format(Utility.showDate) + " " + richiesta.getOrario());
    }
}