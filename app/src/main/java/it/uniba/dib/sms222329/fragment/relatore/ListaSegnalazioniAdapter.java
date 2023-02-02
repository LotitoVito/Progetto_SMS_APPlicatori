package it.uniba.dib.sms222329.fragment.relatore;

import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.SegnalazioneChat;
import it.uniba.dib.sms222329.classi.Tesi;
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
            convertView = inflater.inflate(R.layout.generic_item_with_buttons, viewGroup, false);
        }

        TextView idSegnalazione = convertView.findViewById(R.id.titolo);
        idSegnalazione.setText(segnalazioni.get(i).getIdSegnalazioneChat());

        TextView oggetto = convertView.findViewById(R.id.descrizione);
        oggetto.setText(segnalazioni.get(i).getOggetto());

        TextView idTesiScelta = convertView.findViewById(R.id.sottotitolo);
        idSegnalazione.setText(segnalazioni.get(i).getIdTesi());

        /*
        Button editButton = convertView.findViewById(R.id.modifica);

        if(tesi.get(i).getIdRelatore() != relatoreLoggato.getIdRelatore()){
            editButton.setVisibility(View.GONE);
        }

        // Set up a click listener for the button
        editButton.setOnClickListener(view1 -> Utility.replaceFragment( this.fragmentManager, R.id.container, new GestioneTesiFragment(this.tesi.get(i), this.relatoreLoggato)));

        Button detailButton = convertView.findViewById(R.id.visualizza);

        // Set up a click listener for the button
        detailButton.setOnClickListener(view1 -> {
            // Create a new instance of the bottom sheet fragment
            VisualizzaTesiFragment bottomSheet = new VisualizzaTesiFragment(tesi.get(i));
            // Show the bottom sheet
            bottomSheet.show(this.fragmentManager, bottomSheet.getTag());
        }); */



        return convertView;
    }

}