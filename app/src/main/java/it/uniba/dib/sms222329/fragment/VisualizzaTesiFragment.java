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

import org.w3c.dom.Text;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiDatabase;


public class VisualizzaTesiFragment extends BottomSheetDialogFragment {

    //Variabili e Oggetti
    private Tesi tesi;
    private Database db;

    //View Items
    private TextView titolo;
    private TextView argomento;
    private TextView tempistiche;
    private TextView esamiMancanti;
    private TextView capacitaRichiesta;
    private TextView media;
    private SwitchMaterial disponibilita;
    private ImageView qrCode;
    private Button share;
    private TextView creaSegnalazione;

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

        Init();
        SetTextAll();
        tesi.incrementaVisualizzazioni(db);
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
            Cursor cursor = db.RicercaDato("SELECT " + Database.RELATORE_MATRICOLA + " FROM " + Database.RELATORE + ";");
            cursor.moveToNext();
            sub += "\nMatricola Relatore: " + cursor.getString(cursor.getColumnIndexOrThrow(Database.RELATORE_MATRICOLA));
            intent.putExtra(Intent.EXTRA_TEXT, sub);
            startActivity(Intent.createChooser(intent, "Condividi con:"));
        });

        creaSegnalazione.setOnClickListener(view -> {
            this.dismiss();
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new CreaSegnalazioneFragment(tesi));
        });
    }

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        titolo = getView().findViewById(R.id.titoloTesi);
        argomento = getView().findViewById(R.id.argomentoTesi);
        tempistiche = getView().findViewById(R.id.tempistiche);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        media = getView().findViewById(R.id.media);
        disponibilita = getView().findViewById(R.id.disponibilita);
        qrCode = getView().findViewById(R.id.qrCode);
        share = getView().findViewById(R.id.condividi);
        creaSegnalazione = getView().findViewById(R.id.crea_segnalazione);

        if(Utility.accountLoggato == Utility.GUEST){
            creaSegnalazione.setVisibility(View.GONE);
        }
    }

    private void SetTextAll(){
        titolo.setText(tesi.getTitolo());
        argomento.setText(tesi.getArgomenti());
        tempistiche.setText(String.valueOf(tesi.getTempistiche()));
        esamiMancanti.setText(String.valueOf(tesi.getEsamiMancantiNecessari()));
        capacitaRichiesta.setText(tesi.getCapacitaRichieste());
        media.setText(String.valueOf(tesi.getMediaVotiMinima()));
        disponibilita.setChecked(tesi.getStatoDisponibilita());
        qrCode.setImageBitmap(tesi.QRGenerator());
    }
}