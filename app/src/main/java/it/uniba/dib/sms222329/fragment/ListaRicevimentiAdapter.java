package it.uniba.dib.sms222329.fragment;

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
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.fragment.relatore.GestioneTesiFragment;

public class ListaRicevimentiAdapter extends BaseAdapter {

    private List<Ricevimento> ricevimenti;
    private LayoutInflater inflater;

    public ListaRicevimentiAdapter(Context context, List<Ricevimento> ricevimenti) {
        this.ricevimenti = ricevimenti;
        this.inflater = LayoutInflater.from(context);
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

        TextView tesista = convertView.findViewById(R.id.titolo);
        tesista.setText(ricevimenti.get(i).getIdTask());    //Sostituire con nome e cognome Tesista

        TextView dataOrario = convertView.findViewById(R.id.descrizione);
        dataOrario.setText(ricevimenti.get(i).getData().toString() + " " + ricevimenti.get(i).getOrario().toString());

        TextView stato = convertView.findViewById(R.id.sottotitolo);
        String disponibilita = "";
        if (ricevimenti.get(i).getAccettazione()==Ricevimento.ACCETTATO){
            disponibilita = "Disponibile";
        } else if (ricevimenti.get(i).getAccettazione()==Ricevimento.IN_ATTESA){
            disponibilita = "In Attesa";
        }
        stato.setText(disponibilita);

        return convertView;
    }

}