package it.uniba.dib.sms222329.fragment.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.ricevimento.RicevimentoRichiestaFragment;
import it.uniba.dib.sms222329.fragment.ricevimento.RicevimentoVisualizzaFragment;

public class ListaRicevimentiAdapter extends BaseAdapter {

    //Variabili e Oggetti
    private Context context;
    private List<Ricevimento> ricevimenti;
    private LayoutInflater inflater;
    private FragmentManager manager;

    public ListaRicevimentiAdapter(Context context, List<Ricevimento> ricevimenti, FragmentManager manager) {
        this.ricevimenti = ricevimenti;
        this.inflater = LayoutInflater.from(context);
        this.manager = manager;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ricevimenti.size();
    }

    @Override
    public Object getItem(int i) {
        return ricevimenti.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.generic_item_with_buttons, viewGroup, false);
        }
        Database db = new Database(context);


        if(Utility.accountLoggato != Utility.TESISTA){
            //Tesista
            TextView tesista = convertView.findViewById(R.id.titolo);
            Cursor cursor = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + " " +
                    "FROM " + Database.RICEVIMENTI +" r, " + Database.TASK + " t, " + Database.TESISCELTA + " ts, " + Database.TESISTA + " te, " + Database.UTENTI + " u " +
                    "WHERE r." + Database.RICEVIMENTI_TASKID + "=t." + Database.TASK_ID + " AND t." + Database.TASK_TESISCELTAID + "=ts." + Database.TESISCELTA_ID + " AND ts." + Database.TESISCELTA_TESISTAID + "=te." + Database.TESISTA_ID + " AND te." + Database.TESISTA_UTENTEID + "=u." + Database.UTENTI_ID + " " +
                    "AND r." + Database.RICEVIMENTI_ID + "=" + ricevimenti.get(i).getIdRicevimento() + ";");
            cursor.moveToFirst();
            tesista.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursor.getString(cursor.getColumnIndexOrThrow(Database.UTENTI_NOME)));
        } else {
            //Task
            TextView task = convertView.findViewById(R.id.titolo);
            Cursor cursor = db.RicercaDato("SELECT t." + Database.TASK_TITOLO + " " +
                    "FROM " + Database.RICEVIMENTI + " r, " + Database.TASK + " t " +
                    "WHERE r." + Database.RICEVIMENTI_TASKID + "=t." + Database.TASK_ID + " " +
                    "AND " + Database.RICEVIMENTI_TASKID + "=" + ricevimenti.get(i).getIdTask() + ";");
            cursor.moveToFirst();
            task.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.TASK_TITOLO)));
        }


        //Data e orario
        TextView dataOrario = convertView.findViewById(R.id.descrizione);
        dataOrario.setText(ricevimenti.get(i).getData().format(Utility.showDate) + " " + ricevimenti.get(i).getOrario());

        //Stato
        TextView stato = convertView.findViewById(R.id.sottotitolo);
        String disponibilita = "";
        if (ricevimenti.get(i).getStato()==Ricevimento.ACCETTATO){
            disponibilita = context.getResources().getString(R.string.ricevimento_accettato);
        } else if (ricevimenti.get(i).getStato()==Ricevimento.RIFIUTATO) {
            disponibilita = context.getResources().getString(R.string.ricevimento_rifiutato);
        } else if (ricevimenti.get(i).getStato()==Ricevimento.IN_ATTESA_RELATORE){
            disponibilita = context.getResources().getString(R.string.ricevimento_attesa_relatore);
        } else if (ricevimenti.get(i).getStato()==Ricevimento.IN_ATTESA_TESISTA){
            disponibilita = context.getResources().getString(R.string.ricevimento_attesa_tesista);
        }
        stato.setText(disponibilita);

        //EditButton
        Button modifica = convertView.findViewById(R.id.modifica);
        modifica.setText(context.getString(R.string.rispondi));
        if(Utility.accountLoggato == Utility.CORELATORE ||
                ricevimenti.get(i).getStato()==Ricevimento.ACCETTATO || ricevimenti.get(i).getStato()==Ricevimento.RIFIUTATO ||
                (Utility.accountLoggato == Utility.TESISTA && ricevimenti.get(i).getStato()==Ricevimento.IN_ATTESA_RELATORE) ||
                (Utility.accountLoggato == Utility.RELATORE && ricevimenti.get(i).getStato()==Ricevimento.IN_ATTESA_TESISTA)){
            modifica.setVisibility(View.GONE);
        }

        modifica.setOnClickListener(view -> {
            Utility.replaceFragment(manager, R.id.container, new RicevimentoRichiestaFragment(ricevimenti.get(i)));
        });

        //DetailButton
        Button dettagli = convertView.findViewById(R.id.visualizza);
        dettagli.setOnClickListener(view -> {
            Utility.replaceFragment(manager, R.id.container, new RicevimentoVisualizzaFragment(ricevimenti.get(i)));
        });

        return convertView;
    }

}