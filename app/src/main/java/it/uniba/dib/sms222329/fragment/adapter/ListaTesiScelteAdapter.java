package it.uniba.dib.sms222329.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.TesiScelta;

public class ListaTesiScelteAdapter extends BaseAdapter {

    private List<TesiScelta> tesiScelte;
    private LayoutInflater inflater;

    public ListaTesiScelteAdapter(Context context, List<TesiScelta> tesiScelte) {
        this.tesiScelte = tesiScelte;
        this.inflater = LayoutInflater.from(context);
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

        return convertView;
    }

}