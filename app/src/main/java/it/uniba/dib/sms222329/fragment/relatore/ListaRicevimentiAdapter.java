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
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.fragment.VisualizzaTesiFragment;

public class ListaRicevimentiAdapter extends BaseAdapter {

    private List<Ricevimento> listaRicevimenti;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;
    private Relatore relatoreLoggato;

    public ListaRicevimentiAdapter(Context context, List<Ricevimento> listaRicevimenti, FragmentManager fragmentManager, Relatore relatoreLoggato) {
        this.listaRicevimenti = listaRicevimenti;
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public int getCount() {
        return listaRicevimenti.size();
    }

    @Override
    public Object getItem(int i) {
        return listaRicevimenti.get(i);
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

        /*NELLA VIEW HO SOLO TRE CAMPI, SECONDO ME ANDREBBERO VISUALIZZATI:
        ID RICEVIMENTO, DATA, OGGETTO E ACCETTAZIONE, IL RESTO INVECE ANDREBBE VISUALIZZATO UNA VOLTA
        SCHIACCIATO SU "DETTAGLI" con un VISUALIZZARICEVIMENTOFRAGMENT
         */

        TextView idRicevimenti = convertView.findViewById(R.id.titolo);
        idRicevimenti.setText(listaRicevimenti.get(i).getIdRicevimento());

        TextView argomento = convertView.findViewById(R.id.descrizione);
        argomento.setText(listaRicevimenti.get(i).getArgomento());

        TextView accettazione = convertView.findViewById(R.id.sottotitolo);
        String statoAccettazione = "";
        if(listaRicevimenti.get(i).getAccettazione() == 1){
            statoAccettazione = "ACCETTATO";
        }
        else if(listaRicevimenti.get(i).getAccettazione() == 2){
            statoAccettazione = "IN ATTESA";
        }
        else{
            statoAccettazione = "RIFIUTATO";
        }
        accettazione.setText(statoAccettazione);

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
            VisualizzaTesiFragment bottomSheet = new VisualizzaTesiFragment(listaRicevimenti.get(i));
            // Show the bottom sheet
            bottomSheet.show(this.fragmentManager, bottomSheet.getTag());
        });
        */


        return convertView;
    }

}