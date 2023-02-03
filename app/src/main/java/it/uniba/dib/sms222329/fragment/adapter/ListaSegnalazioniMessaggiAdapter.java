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

    public ListaSegnalazioniMessaggiAdapter(Context context, List<SegnalazioneMessaggio> messaggi) {
        this.messaggi = messaggi;
        this.inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.generic_item, viewGroup, false);
        }

        //Mittente
        TextView mittente = convertView.findViewById(R.id.titolo);
        mittente.setText(String.valueOf(messaggi.get(i).getIdMittente()));

        //Messaggio
        TextView messaggio = convertView.findViewById(R.id.descrizione);
        messaggio.setText(messaggi.get(i).getMessaggio());

        //Timestamp
        TextView timestamp = convertView.findViewById(R.id.sottotitolo);
        LocalDateTime timestampValue = messaggi.get(i).getTimestamp();
        timestamp.setText(String.valueOf(timestampValue.format(Utility.showDateTime)));

        return convertView;
    }
}
