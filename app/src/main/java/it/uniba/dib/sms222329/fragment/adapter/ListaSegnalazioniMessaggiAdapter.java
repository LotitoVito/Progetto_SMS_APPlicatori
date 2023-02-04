package it.uniba.dib.sms222329.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.SegnalazioneMessaggio;

public class ListaSegnalazioniMessaggiAdapter extends BaseAdapter {

    //Variabili e Oggetti
    private List<SegnalazioneMessaggio> messaggi;
    private LayoutInflater inflater;
    private int idUtenteLoggato;

    public ListaSegnalazioniMessaggiAdapter(Context context, List<SegnalazioneMessaggio> messaggi, int idUtenteLoggato) {
        this.messaggi = messaggi;
        this.inflater = LayoutInflater.from(context);
        this.idUtenteLoggato = idUtenteLoggato;
    }

    @Override
    public int getCount() {
        return messaggi.size();
    }

    @Override
    public Object getItem(int i) {
        return messaggi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.message_item, viewGroup, false);
        }

        //Messaggio Inviato e Ricevuto
        TextView inviato = convertView.findViewById(R.id.messaggio_inviato);
        TextView ricevuto = convertView.findViewById(R.id.messaggio_ricevuto);

        if(messaggi.get(i).getIdMittente()==idUtenteLoggato){
            inviato.setVisibility(View.VISIBLE);
            ricevuto.setVisibility(View.GONE);
            inviato.setText(messaggi.get(i).getMessaggio());
        } else {
            inviato.setVisibility(View.GONE);
            ricevuto.setVisibility(View.VISIBLE);
            ricevuto.setText(messaggi.get(i).getMessaggio());
        }

        //Timestamp
        /*TextView timestamp = convertView.findViewById(R.id.sottotitolo);
        LocalDateTime timestampValue = messaggi.get(i).getTimestamp();
        timestamp.setText(String.valueOf(timestampValue.format(Utility.showDateTime)));*/

        return convertView;
    }
}
