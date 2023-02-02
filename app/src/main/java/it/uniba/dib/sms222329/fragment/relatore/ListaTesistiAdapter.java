package it.uniba.dib.sms222329.fragment.relatore;

import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;

public class ListaTesistiAdapter extends BaseAdapter {

    private List<Tesista> listaTesisti;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;
    private Relatore relatoreLoggato;

    public ListaTesistiAdapter(Context context, List<Tesista> listaTesisti, FragmentManager fragmentManager, Relatore relatoreLoggato) {
        this.listaTesisti = listaTesisti;
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public int getCount() {
        return listaTesisti.size();
    }

    @Override
    public Object getItem(int i) {
        return listaTesisti.get(i);
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

        TextView idTesista = convertView.findViewById(R.id.titolo);
        idTesista.setText(listaTesisti.get(i).getIdTesista()); //sostituisci con nome

        TextView matricola = convertView.findViewById(R.id.descrizione);
        matricola.setText(listaTesisti.get(i).getMatricola());

        TextView idUniversitàCorso = convertView.findViewById(R.id.sottotitolo);
        idUniversitàCorso.setText(listaTesisti.get(i).getIdUniversitaCorso());

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