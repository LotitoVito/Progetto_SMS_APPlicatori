package it.uniba.dib.sms222329.fragment.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.relatore.TesistaRelatoreFragment;

public class ListaTesistiRelatoreAdapter extends BaseAdapter {

    //Variabili e Oggetti
    private List<TesiScelta> tesiScelte;
    private LayoutInflater inflater;
    private FragmentManager manager;
    private boolean richiesta;
    private ArrayList<TesiScelta> copiaRicerca;
    private Context context;

    /**
     * Costruttore standard
     * @param context
     * @param tesiScelte
     * @param manager
     */
    public ListaTesistiRelatoreAdapter(Context context, List<TesiScelta> tesiScelte, FragmentManager manager) {
        this.tesiScelte = tesiScelte;
        this.inflater = LayoutInflater.from(context);
        this.manager = manager;
        this.context = context;
        if(tesiScelte.size()!=0){
            this.copiaRicerca = new ArrayList<TesiScelta>();
            this.copiaRicerca.addAll(tesiScelte);
        }
    }

    /**
     * Costruttore usato per il Corelatore
     * @param context
     * @param tesiScelte
     * @param manager
     * @param richiesta
     */
    public ListaTesistiRelatoreAdapter(Context context, List<TesiScelta> tesiScelte, FragmentManager manager, boolean richiesta) {
        this.tesiScelte = tesiScelte;
        this.inflater = LayoutInflater.from(context);
        this.manager = manager;
        this.richiesta = richiesta;
        this.context = context;
        if(tesiScelte.size()!=0){
            this.copiaRicerca = new ArrayList<TesiScelta>();
            this.copiaRicerca.addAll(tesiScelte);
        }
    }

    @Override
    public int getCount() {
        return tesiScelte.size();
    }

    @Override
    public Object getItem(int i) {
        return tesiScelte.get(i);
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

        if(Utility.accountLoggato == Utility.GUEST){
            //Titolo tesi
            TextView titolo = convertView.findViewById(R.id.titolo);
            titolo.setText(String.valueOf(tesiScelte.get(i).getTitolo()));
        } else {
            //Tesista
            Cursor cursor = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + " " +
                    "FROM " + Database.TESISTA + " t, " + Database.UTENTI + " u " +
                    "WHERE t." + Database.TESISTA_UTENTEID + "=u." + Database.UTENTI_ID + " " +
                    "AND t." + Database.TESISTA_ID + "=" + tesiScelte.get(i).getIdTesista() + ";");
            cursor.moveToFirst();
            TextView idTesista = convertView.findViewById(R.id.titolo);
            idTesista.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursor.getString(cursor.getColumnIndexOrThrow(Database.UTENTI_NOME)));
        }

        //Tesi
        Cursor cursor = db.RicercaDato("SELECT " + Database.TESI_TITOLO + " " +
                "FROM " + Database.TESI + " " +
                "WHERE " + Database.TESI_ID + "=" + tesiScelte.get(i).getIdTesi() + ";");
        cursor.moveToFirst();
        TextView tesi = convertView.findViewById(R.id.descrizione);
        tesi.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.TESI_TITOLO)));


        if(Utility.accountLoggato == Utility.RELATORE){
            //Data pubblicazione
            TextView dataPubblicazione = convertView.findViewById(R.id.sottotitolo);
            if(tesiScelte.get(i).getDataPubblicazione() != null){
                dataPubblicazione.setText(String.valueOf(tesiScelte.get(i).getDataPubblicazione().format(Utility.showDate)));
            } else {
                dataPubblicazione.setVisibility(View.GONE);
            }
        } else {
            //Relatore
            Cursor cursorRelatore = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + " " +
                    "FROM " + Database.TESI + " t, " + Database.RELATORE + " r, " + Database.UTENTI + " u " +
                    "WHERE t." + Database.TESI_RELATOREID + "=r." + Database.RELATORE_ID + " AND r." + Database.RELATORE_UTENTEID + "=u." + Database.UTENTI_ID + " " +
                    "AND t." + Database.TESI_ID + "=" + tesiScelte.get(i).getIdTesi() + ";");
            cursorRelatore.moveToFirst();
            TextView relatore = convertView.findViewById(R.id.sottotitolo);
            relatore.setText(cursorRelatore.getString(cursorRelatore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorRelatore.getString(cursorRelatore.getColumnIndexOrThrow(Database.UTENTI_NOME)));
        }

        //EditButton
        Button modifica = convertView.findViewById(R.id.modifica);
        modifica.setVisibility(View.GONE);

        //DetailButton
        Button dettagli = convertView.findViewById(R.id.visualizza);
        dettagli.setOnClickListener(view -> {
            if(Utility.accountLoggato == Utility.RELATORE){
                Utility.replaceFragment(manager, R.id.container, new TesistaRelatoreFragment(tesiScelte.get(i)));
            } else {
                Utility.replaceFragment(manager, R.id.container, new TesistaRelatoreFragment(tesiScelte.get(i), richiesta));
            }

        });

        return convertView;
    }

    /**
     * Il metodo permette di filtrare e mostrare solo le tesi che hanno il titolo corrispondente alla stringa di ricerca
     * @param ricerca
     */
    public void filter(String ricerca){
        ricerca = ricerca.toLowerCase(Locale.getDefault());
        tesiScelte.clear();
        if (ricerca.length()==0 && copiaRicerca != null){
            tesiScelte.addAll(copiaRicerca);
        }
        else if (copiaRicerca != null){
            for (TesiScelta tesi : copiaRicerca){
                if (tesi.getTitolo().toLowerCase(Locale.getDefault())
                        .contains(ricerca)){
                    this.tesiScelte.add(tesi);
                }
            }
        }
        notifyDataSetChanged();
    }
}