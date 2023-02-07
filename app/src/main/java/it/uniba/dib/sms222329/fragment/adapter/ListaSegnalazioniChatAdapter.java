package it.uniba.dib.sms222329.fragment.adapter;

import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.SegnalazioneChat;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.SegnalazioneMessaggiFragment;

public class ListaSegnalazioniChatAdapter extends BaseAdapter {

    //Variabili e Oggetti
    private List<SegnalazioneChat> segnalazioni;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;
    private Context context;

    public ListaSegnalazioniChatAdapter(Context context, List<SegnalazioneChat> segnalazioni, FragmentManager fragmentManager) {
        this.segnalazioni = segnalazioni;
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @Override
    public int getCount() {
        return segnalazioni.size();
    }

    @Override
    public Object getItem(int i) {
        return segnalazioni.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.generic_item, viewGroup, false);
        }

        Database db = new Database(context);
        int idUtenteChat = 0;

        if(Utility.accountLoggato == Utility.TESISTA){
            idUtenteChat = Utility.tesistaLoggato.getIdUtente();
        } else if(Utility.accountLoggato == Utility.RELATORE){
            idUtenteChat = Utility.relatoreLoggato.getIdUtente();
        } else if(Utility.accountLoggato == Utility.CORELATORE){
            idUtenteChat = Utility.coRelatoreLoggato.getIdUtente();
        }


        Cursor cursor = db.RicercaDato("SELECT " + Database.MESSAGGISEGNALAZIONE_UTENTEID +
                " FROM " + Database.MESSAGGISEGNALAZIONE +
                " WHERE " + Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID + "=" + segnalazioni.get(i).getIdSegnalazioneChat() +
                " AND " + Database.MESSAGGISEGNALAZIONE_UTENTEID + "!=" + idUtenteChat + ";");
        cursor.moveToFirst();

        if(cursor.getCount() == 0){
            cursor = db.RicercaDato("SELECT r." + Database.RELATORE_UTENTEID +
                    " FROM " + Database.RELATORE + " r, " + Database.TESI + " t" +
                    " WHERE t." + Database.TESI_RELATOREID + "=r." + Database.RELATORE_ID +
                    " AND t." + Database.TESI_ID + "=" + segnalazioni.get(i).getIdTesi() + ";");
            cursor.moveToFirst();
        }

        //Utente
        Cursor cursorUtente = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", " + Database.UTENTI_NOME + " " +
                "FROM " + Database.UTENTI + " u " +
                "WHERE " + Database.UTENTI_ID + "=" + cursor.getInt(cursor.getColumnIndexOrThrow(Database.MESSAGGISEGNALAZIONE_UTENTEID)) + ";");
        cursorUtente.moveToFirst();
        TextView idSegnalazione = convertView.findViewById(R.id.titolo);
        idSegnalazione.setText(cursorUtente.getString(cursorUtente.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorUtente.getString(cursorUtente.getColumnIndexOrThrow(Database.UTENTI_NOME)));

        //Oggetto
        TextView oggettoSegnalazione = convertView.findViewById(R.id.descrizione);
        oggettoSegnalazione.setText(segnalazioni.get(i).getOggetto());

        //Terza riga
        TextView riga = convertView.findViewById(R.id.sottotitolo);
        riga.setVisibility(View.GONE);

        //Apri chat
        LinearLayout item = convertView.findViewById(R.id.segnalazione);
        item.setOnClickListener(view1 -> {
            Utility.replaceFragment(this.fragmentManager, R.id.container, new SegnalazioneMessaggiFragment(segnalazioni.get(i).getIdSegnalazioneChat()));
        });

        return convertView;
    }

}