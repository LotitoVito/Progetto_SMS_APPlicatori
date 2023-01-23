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
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.fragment.VisualizzaTesiFragment;

public class ListaTesiAdapter extends BaseAdapter {

    private List<Tesi> tesi;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;
    private Relatore relatoreLoggato;

    public ListaTesiAdapter(Context context, List<Tesi> tesi, FragmentManager fragmentManager, Relatore relatoreLoggato) {
        this.tesi = tesi;
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
            convertView = inflater.inflate(R.layout.relatore_tesi_item, viewGroup, false);
        }

        TextView titolo = convertView.findViewById(R.id.titoloTesi);
        titolo.setText(tesi.get(i).getTitolo());

        TextView argomento = convertView.findViewById(R.id.argomentoTesi);
        argomento.setText(tesi.get(i).getArgomenti());

        TextView statoTesi = convertView.findViewById(R.id.statoTesi);
        String disponibilita = "";
        if (tesi.get(i).getStatoDisponibilita()) disponibilita = "Disponibile";
        else disponibilita = "Non Disponibile";
        statoTesi.setText(disponibilita);

        Button editButton = convertView.findViewById(R.id.modificaTesi);

        // Set up a click listener for the button
        editButton.setOnClickListener(view1 -> Utility.replaceFragment( this.fragmentManager, R.id.container, new GestioneTesiFragment(this.tesi.get(i), this.relatoreLoggato)));

        Button detailButton = convertView.findViewById(R.id.visualizzaTesi);

        // Set up a click listener for the button
        detailButton.setOnClickListener(view1 -> {
            // Create a new instance of the bottom sheet fragment
            VisualizzaTesiFragment bottomSheet = new VisualizzaTesiFragment(tesi.get(i));
            // Show the bottom sheet
            bottomSheet.show(this.fragmentManager, bottomSheet.getTag());
        });



        return convertView;
    }

}