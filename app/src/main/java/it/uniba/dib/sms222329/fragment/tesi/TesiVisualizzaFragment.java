package it.uniba.dib.sms222329.fragment.tesi;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

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
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.segnalazione.SegnalazioneCreaFragment;


public class TesiVisualizzaFragment extends BottomSheetDialogFragment {

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
    private TextView universita;
    private TextView corso;
    private SwitchMaterial disponibilita;
    private ImageView qrCode;
    private Button share;
    private TextView creaSegnalazione;

    public TesiVisualizzaFragment(){}
    public TesiVisualizzaFragment(Tesi tesi) {
        this.tesi = tesi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi_visualizza, container, false);
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
            String sub = R.string.condividi_messaggio +
                    "\n" + R.string.titolo + " : " + tesi.getTitolo() +
                    "\n" + R.string.argomenti + " : " + tesi.getArgomenti() +
                    "\n" + R.string.tempistiche + " : " + tesi.getTempistiche() +
                    "\n" + R.string.media + " : " + tesi.getMediaVotiMinima() +
                    "\n" + R.string.numero_esami_mancanti + " : " + tesi.getEsamiNecessari() +
                    "\n" + R.string.capacitaRichiesta + " : " + tesi.getCapacitaRichieste();
            if(tesi.getStatoDisponibilita()){
                sub += "\n" + R.string.tesi_disponibile;
            } else{
                sub += "\n" + R.string.tesi_non_disponibile;
            }
            Cursor cursor = db.RicercaDato("SELECT " + Database.RELATORE_MATRICOLA + " FROM " + Database.RELATORE + ";");
            cursor.moveToNext();
            sub += "\n" + R.string.matricola_relatore + " : " + cursor.getString(cursor.getColumnIndexOrThrow(Database.RELATORE_MATRICOLA));
            intent.putExtra(Intent.EXTRA_TEXT, sub);
            startActivity(Intent.createChooser(intent, getActivity().getApplicationContext().getResources().getString(R.string.condividi_chooser)));
        });

        creaSegnalazione.setOnClickListener(view -> {
            this.dismiss();
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new SegnalazioneCreaFragment(tesi));
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        titolo = getView().findViewById(R.id.titoloTesi);
        argomento = getView().findViewById(R.id.argomentoTesi);
        tempistiche = getView().findViewById(R.id.tempistiche);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        media = getView().findViewById(R.id.media);
        universita = getView().findViewById(R.id.universita);
        corso = getView().findViewById(R.id.corso);
        disponibilita = getView().findViewById(R.id.disponibilita);
        qrCode = getView().findViewById(R.id.qrCode);
        share = getView().findViewById(R.id.condividi);
        creaSegnalazione = getView().findViewById(R.id.crea_segnalazione);

        if(Utility.accountLoggato == Utility.GUEST || (Utility.accountLoggato==Utility.RELATORE && Utility.relatoreLoggato.getIdRelatore() == tesi.getIdRelatore())){
            creaSegnalazione.setVisibility(View.GONE);
        }
    }

    /**
     * Imposta il testo per ogni elemento della view
     */
    private void SetTextAll(){
        titolo.setText(tesi.getTitolo());
        argomento.setText(tesi.getArgomenti());
        tempistiche.setText(String.valueOf(tesi.getTempistiche()));
        esamiMancanti.setText(String.valueOf(tesi.getEsamiNecessari()));
        capacitaRichiesta.setText(tesi.getCapacitaRichieste());
        media.setText(String.valueOf(tesi.getMediaVotiMinima()));

        Cursor cursorUniversita = db.RicercaDato("SELECT u." + Database.UNIVERSITA_NOME +
                " FROM " + Database.UNIVERSITACORSO + " uc, " + Database.UNIVERSITA + " u " +
                " WHERE uc." + Database.UNIVERSITACORSO_UNIVERSITAID + "=u." + Database.UNIVERSITA_ID +
                " AND uc." + Database.UNIVERSITACORSO_ID + "=" + tesi.getIdUniversitaCorso() + ";");
        cursorUniversita.moveToFirst();
        universita.setText(cursorUniversita.getString(cursorUniversita.getColumnIndexOrThrow(Database.UNIVERSITA_NOME)));
        Cursor cursorCorso = db.RicercaDato("SELECT cs." + Database.CORSOSTUDI_NOME +
                " FROM " + Database.UNIVERSITACORSO + " uc, " + Database.CORSOSTUDI + " cs " +
                " WHERE uc." + Database.UNIVERSITACORSO_CORSOID + "=cs." + Database.CORSOSTUDI_ID +
                " AND uc." + Database.UNIVERSITACORSO_ID + "=" + tesi.getIdUniversitaCorso() + ";");
        cursorCorso.moveToFirst();
        corso.setText(cursorCorso.getString(cursorCorso.getColumnIndexOrThrow(Database.CORSOSTUDI_NOME)));

        disponibilita.setChecked(tesi.getStatoDisponibilita());
        qrCode.setImageBitmap(tesi.QRGenerator());
    }
}