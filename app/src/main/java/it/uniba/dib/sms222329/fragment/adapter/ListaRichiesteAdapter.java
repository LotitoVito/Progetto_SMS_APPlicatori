package it.uniba.dib.sms222329.fragment.adapter;

import android.content.Context;
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
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.fragment.relatore.AccettaRichiestaTesiFragment;

public class ListaRichiesteAdapter extends BaseAdapter {

    private List<RichiestaTesi> richieste;
    private LayoutInflater inflater;
    private FragmentManager manager;

    public ListaRichiesteAdapter(Context context, List<RichiestaTesi> richieste, FragmentManager manager) {
        this.richieste = richieste;
        this.inflater = LayoutInflater.from(context);
        this.manager = manager;
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

        //Tesista
        TextView tesista = convertView.findViewById(R.id.titolo);
        tesista.setText(String.valueOf(richieste.get(i).getIdTesista()));    //Sostituire con nome e cognome Tesista

        //Data e orario
        TextView tesi = convertView.findViewById(R.id.descrizione);
        tesi.setText(String.valueOf(richieste.get(i).getIdTesi()));

        //Stato
        TextView messaggio = convertView.findViewById(R.id.sottotitolo);
        messaggio.setText(richieste.get(i).getMessaggio());

        //EditButton
        Button modifica = convertView.findViewById(R.id.modifica);
        modifica.setVisibility(View.GONE);

        //DetailsButton
        Button dettagli = convertView.findViewById(R.id.visualizza);
        dettagli.setOnClickListener(view -> {
            Utility.replaceFragment(manager, R.id.container, new AccettaRichiestaTesiFragment(richieste.get(i)));
        });

        return convertView;
    }
}
