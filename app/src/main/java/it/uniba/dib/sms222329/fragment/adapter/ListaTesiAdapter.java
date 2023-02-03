package it.uniba.dib.sms222329.fragment.adapter;

import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.fragment.VisualizzaTesiFragment;
import it.uniba.dib.sms222329.fragment.relatore.GestioneTesiFragment;

public class ListaTesiAdapter extends BaseAdapter {

    //variabili e Oggetti
    private List<Tesi> tesi;
    private ArrayList<Tesi> copiaRicerca;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;
    private Relatore relatoreLoggato;

    public ListaTesiAdapter(Context context, List<Tesi> tesi, FragmentManager fragmentManager, Relatore relatoreLoggato) {
        this.tesi = tesi;
        if(tesi.size()!=0){
            this.copiaRicerca = new ArrayList<Tesi>();
            this.copiaRicerca.addAll(tesi);
        }
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public int getCount() {
        return tesi.size();
    }

    @Override
    public Object getItem(int i) {
        return tesi.get(i);
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

        //Titolo
        TextView titolo = convertView.findViewById(R.id.titolo);
        titolo.setText(tesi.get(i).getTitolo());

        //Argomento
        TextView argomento = convertView.findViewById(R.id.descrizione);
        argomento.setText(tesi.get(i).getArgomenti());

        //Stato
        TextView statoTesi = convertView.findViewById(R.id.sottotitolo);
        String disponibilita = "";
        if (tesi.get(i).getStatoDisponibilita()) disponibilita = "Disponibile";
        else disponibilita = "Non Disponibile";
        statoTesi.setText(disponibilita);

        //Edit Button
        Button editButton = convertView.findViewById(R.id.modifica);

        if(tesi.get(i).getIdRelatore() != relatoreLoggato.getIdRelatore()){
            editButton.setVisibility(View.GONE);
        }

        editButton.setOnClickListener(view1 -> Utility.replaceFragment( this.fragmentManager, R.id.container, new GestioneTesiFragment(this.tesi.get(i), this.relatoreLoggato)));

        //Detail Button
        Button detailButton = convertView.findViewById(R.id.visualizza);

        detailButton.setOnClickListener(view1 -> {
            VisualizzaTesiFragment bottomSheet = new VisualizzaTesiFragment(tesi.get(i));
            bottomSheet.show(this.fragmentManager, bottomSheet.getTag());
        });

        return convertView;
    }

    public void filter(String ricerca){
        ricerca = ricerca.toLowerCase(Locale.getDefault());
        tesi.clear();
        if (ricerca.length()==0){
            tesi.addAll(copiaRicerca);
        }
        else {
            for (Tesi tesi : copiaRicerca){
                if (tesi.getTitolo().toLowerCase(Locale.getDefault())
                        .contains(ricerca)){
                    this.tesi.add(tesi);
                }
            }
        }
        notifyDataSetChanged();
    }
}