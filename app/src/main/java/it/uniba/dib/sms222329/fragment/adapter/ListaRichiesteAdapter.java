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

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.relatore.AccettaRichiestaTesiFragment;
import it.uniba.dib.sms222329.fragment.tesista.DettagliRichiestaTesiFragment;

public class ListaRichiesteAdapter extends BaseAdapter {

    //Variabili e Oggetti
    private List<RichiestaTesi> richieste;
    private LayoutInflater inflater;
    private FragmentManager manager;
    private Context context;

    public ListaRichiesteAdapter(Context context, List<RichiestaTesi> richieste, FragmentManager manager) {
        this.richieste = richieste;
        this.inflater = LayoutInflater.from(context);
        this.manager = manager;
        this.context = context;
    }

    @Override
    public int getCount() {
        return richieste.size();
    }

    @Override
    public Object getItem(int i) {
        return richieste.get(i);
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

        if(Utility.accountLoggato == Utility.RELATORE){
            //Tesista
            Cursor cursorTesista = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + " " +
                    "FROM " + Database.TESISTA + " t, " + Database.UTENTI +  " u " +
                    "WHERE t." + Database.TESISTA_UTENTEID + "=u." + Database.UTENTI_ID + " " +
                    "AND t." + Database.TESISTA_ID + "=" + richieste.get(i).getIdTesista() + ";");
            cursorTesista.moveToFirst();
            TextView tesista = convertView.findViewById(R.id.titolo);
            tesista.setText(cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.UTENTI_NOME)));

            //Tesi
            Cursor cursorTesi = db.RicercaDato("SELECT " + Database.TESI_TITOLO + " " +
                    "FROM " + Database.TESI + " WHERE " + Database.TESI_ID + "=" + richieste.get(i).getIdTesi() + ";");
            cursorTesi.moveToFirst();
            TextView tesi = convertView.findViewById(R.id.descrizione);
            tesi.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TITOLO)));

            //Gone
            TextView gone = convertView.findViewById(R.id.sottotitolo);
            gone.setVisibility(View.GONE);

            //EditButton
            Button rispondi = convertView.findViewById(R.id.modifica);
            rispondi.setText("Rispondi");
            rispondi.setOnClickListener(view -> {
                Utility.replaceFragment(manager, R.id.container, new AccettaRichiestaTesiFragment(richieste.get(i)));
            });

            //DetailsButton
            Button dettagli = convertView.findViewById(R.id.visualizza);
            dettagli.setVisibility(View.GONE);
        } else if(Utility.accountLoggato == Utility.TESISTA){
            //Relatore
            Cursor cursorRelatore = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + " " +
                    "FROM " + Database.TESI + " t, " + Database.RELATORE + " r, " + Database.UTENTI + " u " +
                    "WHERE t." + Database.TESI_RELATOREID + "=r." + Database.RELATORE_ID + " AND r." + Database.RELATORE_UTENTEID + "=u." + Database.UTENTI_ID + " " +
                    "AND t." + Database.TESI_ID + "=" + richieste.get(i).getIdTesi() + ";");
            cursorRelatore.moveToFirst();
            TextView relatore = convertView.findViewById(R.id.titolo);
            relatore.setText(cursorRelatore.getString(cursorRelatore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorRelatore.getString(cursorRelatore.getColumnIndexOrThrow(Database.UTENTI_NOME)));

            //Tesi
            Cursor cursorTesi = db.RicercaDato("SELECT " + Database.TESI_TITOLO + " " +
                    "FROM " + Database.TESI + " WHERE " + Database.TESI_ID + "=" + richieste.get(i).getIdTesi() + ";");
            cursorTesi.moveToFirst();
            TextView tesi = convertView.findViewById(R.id.descrizione);
            tesi.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TITOLO)));

            //Stato
            TextView stato = convertView.findViewById(R.id.sottotitolo);
            if(richieste.get(i).getAccettata() == RichiestaTesi.IN_ATTESA){
                stato.setText("Richiesta in attesa di approvazione");
            } else if(richieste.get(i).getAccettata() == RichiestaTesi.RIFIUTATO) {
                stato.setText("Richiesta rifiutata");
            }

            //EditButton
            Button modifica = convertView.findViewById(R.id.modifica);
            modifica.setVisibility(View.GONE);

            //DetailsButton
            Button dettagli = convertView.findViewById(R.id.visualizza);
            dettagli.setOnClickListener(view -> {
                Utility.replaceFragment(manager, R.id.container, new DettagliRichiestaTesiFragment(richieste.get(i)));
            });
        }

        return convertView;
    }
}
