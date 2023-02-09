package it.uniba.dib.sms222329.fragment.adapter;

import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.util.Log;
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
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.tesi.TesiVisualizzaFragment;
import it.uniba.dib.sms222329.fragment.tesi.TesiCreaModificaFragment;
import it.uniba.dib.sms222329.fragment.richiestatesi.RichiestaTesiFragment;

public class ListaTesiAdapter extends BaseAdapter {

    //variabili e Oggetti
    private List<Tesi> tesi;
    private ArrayList<Tesi> copiaRicerca;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;
    private Context context;

    public ListaTesiAdapter(Context context, List<Tesi> tesi, FragmentManager fragmentManager) {
        this.tesi = tesi;
        if(tesi.size()!=0){
            this.copiaRicerca = new ArrayList<Tesi>();
            this.copiaRicerca.addAll(tesi);
        }
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        this.context = context;
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

        Database db = new Database(context);

        //Titolo
        TextView titolo = convertView.findViewById(R.id.titolo);
        titolo.setText(tesi.get(i).getTitolo());

        //Argomento
        TextView argomento = convertView.findViewById(R.id.descrizione);
        argomento.setText(tesi.get(i).getArgomenti());

        //Stato
        TextView statoTesi = convertView.findViewById(R.id.sottotitolo);
        if (tesi.get(i).getStatoDisponibilita()){
            statoTesi.setText("Disponibile");
        } else {
            statoTesi.setText("Non Disponibile");
        }

        //Edit Button
        Button editButton = convertView.findViewById(R.id.modifica);
        if(Utility.accountLoggato == Utility.TESISTA && tesi.get(i).getStatoDisponibilita() == Tesi.DISPONIBILE &&
                !db.VerificaDatoEsistente("SELECT * FROM " + Database.TESISCELTA + " WHERE " + Database.TESISCELTA_TESISTAID + "=" + Utility.tesistaLoggato.getIdTesista() + ";") &&
                !db.VerificaDatoEsistente("SELECT * FROM " + Database.RICHIESTA + " WHERE " + Database.RICHIESTA_TESISTAID + "=" + Utility.tesistaLoggato.getIdTesista() + " AND " + Database.RICHIESTA_ACCETTATA + "=" + RichiestaTesi.IN_ATTESA + ";") &&
                db.VerificaDatoEsistente("SELECT * FROM " + Database.TESI + " WHERE " + Database.TESI_UNIVERSITACORSOID + "=" + Utility.tesistaLoggato.getIdUniversitaCorso() + ";")){
            editButton.setText("Richiedi");
            editButton.setOnClickListener(view -> {
                Utility.replaceFragment(this.fragmentManager, R.id.container, new RichiestaTesiFragment(tesi.get(i)));
            });
        } else if (Utility.accountLoggato == Utility.RELATORE && tesi.get(i).getIdRelatore() == Utility.relatoreLoggato.getIdRelatore()){
            editButton.setOnClickListener(view ->{
                Utility.replaceFragment( this.fragmentManager, R.id.container, new TesiCreaModificaFragment(this.tesi.get(i)));
            });
        } else {
            editButton.setVisibility(View.GONE);
        }

        //Detail Button
        Button detailButton = convertView.findViewById(R.id.visualizza);
        detailButton.setOnClickListener(view1 -> {
            TesiVisualizzaFragment bottomSheet = new TesiVisualizzaFragment(tesi.get(i));
            bottomSheet.show(this.fragmentManager, bottomSheet.getTag());
        });

        return convertView;
    }

    /**
     * Il metodo permette di filtrare e mostrare solo le tesi che hanno il titolo corrispondente alla stringa di ricerca
     * @param ricerca
     */
    public void filter(String ricerca){
        ricerca = ricerca.toLowerCase(Locale.getDefault());
        tesi.clear();
        if (ricerca.length()==0 && copiaRicerca != null){
            tesi.addAll(copiaRicerca);
        }
        else if (copiaRicerca != null){
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