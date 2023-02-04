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
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.fragment.relatore.TesistaRelatoreFragment;

public class ListaTesistiRelatoreAdapter extends BaseAdapter {

    //Variabili e Oggetti
    private List<TesiScelta> tesiScelte;
    private LayoutInflater inflater;
    private FragmentManager manager;

    public ListaTesistiRelatoreAdapter(Context context, List<TesiScelta> tesiScelte, FragmentManager manager) {
        this.tesiScelte = tesiScelte;
        this.inflater = LayoutInflater.from(context);
        this.manager = manager;
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

        //Tesista
        TextView idTesista = convertView.findViewById(R.id.titolo);
        idTesista.setText(String.valueOf(tesiScelte.get(i).getIdTesista())); //sostituisci con nome

        //Tesi
        TextView matricola = convertView.findViewById(R.id.descrizione);
        matricola.setText(String.valueOf(tesiScelte.get(i).getIdTesi()));

        //Data pubblicazione
        TextView dataPubblicazione = convertView.findViewById(R.id.sottotitolo);
        if(tesiScelte.get(i).getDataPubblicazione() != null){
            dataPubblicazione.setText(String.valueOf(tesiScelte.get(i).getDataPubblicazione()));
        }

        //EditButton
        Button modifica = convertView.findViewById(R.id.modifica);
        modifica.setVisibility(View.GONE);

        //DetailButton
        Button dettagli = convertView.findViewById(R.id.visualizza);
        dettagli.setOnClickListener(view -> {
            Utility.replaceFragment(manager, R.id.container, new TesistaRelatoreFragment(tesiScelte.get(i)));
        });

        return convertView;
    }
}