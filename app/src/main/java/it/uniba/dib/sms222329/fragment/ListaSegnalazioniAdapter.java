package it.uniba.dib.sms222329.fragment;

import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.SegnalazioneChat;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.fragment.SegnalazioneFragment;
import it.uniba.dib.sms222329.fragment.VisualizzaTesiFragment;

public class ListaSegnalazioniAdapter extends BaseAdapter {

    private List<SegnalazioneChat> segnalazioni;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;
    private Relatore relatoreLoggato;

    public ListaSegnalazioniAdapter(Context context, List<SegnalazioneChat> segnalazioni, FragmentManager fragmentManager, Relatore relatoreLoggato) {
        this.segnalazioni = segnalazioni;
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        this.relatoreLoggato = relatoreLoggato;
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
            convertView = inflater.inflate(R.layout.segnalazioni_item, viewGroup, false);
        }

        TextView idSegnalazione = convertView.findViewById(R.id.nomeSegnalazione);
        idSegnalazione.setText(String.valueOf(segnalazioni.get(i).getIdSegnalazioneChat()));

        TextView oggettoSegnalazione = convertView.findViewById(R.id.descrizioneSegnalazione);
        oggettoSegnalazione.setText(segnalazioni.get(i).getOggetto());

        TextView idTesiScleta = convertView.findViewById(R.id.statoSegnalazione);
        idTesiScleta.setText(String.valueOf(segnalazioni.get(i).getIdTesi()));

        LinearLayout item = convertView.findViewById(R.id.segnalazione);

        // Set up a click listener for the button
        item.setOnClickListener(view1 -> {
            Utility.replaceFragment(this.fragmentManager, R.id.container, new SegnalazioneFragment());
        });


        return convertView;
    }

}