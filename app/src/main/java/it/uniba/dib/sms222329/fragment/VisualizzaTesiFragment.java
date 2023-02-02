package it.uniba.dib.sms222329.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiDatabase;


public class VisualizzaTesiFragment extends BottomSheetDialogFragment {

    //Variabili e Oggetti
    private Tesi tesi;
    private Database db;

    public  VisualizzaTesiFragment(){}
    public VisualizzaTesiFragment(Tesi tesi) {
        this.tesi = tesi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_visualizza_tesi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        db = new Database(getActivity().getApplicationContext());

        TextView titolo = getView().findViewById(R.id.titoloTesi);
        TextView argomento = getView().findViewById(R.id.argomentoTesi);
        TextView tempistiche = getView().findViewById(R.id.tempistiche);
        TextView esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        TextView capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        TextView media = getView().findViewById(R.id.media);
        SwitchMaterial disponibilita = getView().findViewById(R.id.disponibilita);
        ImageView qrCode = getView().findViewById(R.id.qrCode);
        Button share = getView().findViewById(R.id.condividi);

        titolo.setText(tesi.getTitolo());
        argomento.setText(tesi.getArgomenti());
        tempistiche.setText(String.valueOf(tesi.getTempistiche()));
        esamiMancanti.setText(String.valueOf(tesi.getEsamiMancantiNecessari()));
        capacitaRichiesta.setText(tesi.getCapacitaRichieste());
        media.setText(String.valueOf(tesi.getMediaVotiMinima()));
        disponibilita.setChecked(tesi.getStatoDisponibilita());
        qrCode.setImageBitmap(tesi.QRGenerator());

        share.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String sub = "Guarda questa Tesi!\nTitolo: " + tesi.getTitolo() +"\nArgomenti: " + tesi.getArgomenti() +"\nTempistiche in mesi: "
                    + tesi.getTempistiche() + "\nMedia: " + tesi.getMediaVotiMinima() + "\nEsami necessari: " + tesi.getEsamiMancantiNecessari() +
                    "\nCapacità richieste: " + tesi.getCapacitaRichieste();
            if(tesi.getStatoDisponibilita()){
                sub += "\nDisponibile";
            } else{
                sub += "\nDisponibile";
            }
            Database db = new Database(getActivity().getApplicationContext());
            Cursor cursor = db.RicercaDato("SELECT " + Database.RELATORE_MATRICOLA + " FROM " + Database.RELATORE + ";");
            cursor.moveToNext();
            sub += "\nMatricola Relatore: " + cursor.getString(0);
            intent.putExtra(Intent.EXTRA_TEXT, sub);
            startActivity(Intent.createChooser(intent, "Condividi con:"));
        });

        tesi.incrementaVisualizzazioni(db);
    }
}