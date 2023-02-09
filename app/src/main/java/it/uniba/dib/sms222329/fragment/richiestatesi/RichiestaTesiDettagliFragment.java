package it.uniba.dib.sms222329.fragment.richiestatesi;

import android.database.Cursor;
import android.os.Bundle;

import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.database.Database;


public class RichiestaTesiDettagliFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private RichiestaTesi richiesta;

    //View Items
    private TextView titoloTesi;
    private TextView argomento;
    private MotionLabel labelRelatore;
    private TextView relatore;
    private TextView tempistiche;
    private TextView esamiMancanti;
    private TextView capacitaRichiesta;
    private TextView capacitaEffettive;
    private TextView media;
    private TextView messaggioTesista;
    private TextView testoRispostaRelatore;
    private TextInputEditText rispostaRelatore;
    private Button accetta;
    private Button rifiuta;

    public RichiestaTesiDettagliFragment(RichiestaTesi richiesta) {
        this.richiesta = richiesta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_richiesta_tesi_dettagli_risposta, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetTextAll();

    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init(){
        db = new Database(getActivity().getApplicationContext());
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomento = getView().findViewById(R.id.argomentoTesi);
        labelRelatore = getView().findViewById(R.id.label_nome);
        relatore = getView().findViewById(R.id.nome);
        tempistiche = getView().findViewById(R.id.tempistiche);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        capacitaEffettive = getView().findViewById(R.id.capacitaEffettive);
        media = getView().findViewById(R.id.media);
        messaggioTesista = getView().findViewById(R.id.messaggio);
        testoRispostaRelatore = getView().findViewById(R.id.label_risposta_relatore);
        rispostaRelatore = getView().findViewById(R.id.risposta);
        accetta = getView().findViewById(R.id.accetta);
        rifiuta = getView().findViewById(R.id.rifiuta);

        accetta.setVisibility(View.GONE);
        rifiuta.setVisibility(View.GONE);
        rispostaRelatore.setVisibility(View.GONE);
    }

    /**
     * Imposta il testo per ogni elemento della view in base ai casi
     */
    private void SetTextAll(){
        //Tesi
        Cursor cursorTesi = db.RicercaDato("SELECT * FROM " + Database.TESI + " WHERE " + Database.TESI_ID + "=" + richiesta.getIdTesi() + ";");
        cursorTesi.moveToFirst();
        titoloTesi.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TITOLO)));
        argomento.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_ARGOMENTO)));
        tempistiche.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TEMPISTICHE)));
        esamiMancanti.setText("Requisito richiesto: " + cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_ESAMINECESSARI)) +
                "\nTesista: " + Utility.tesistaLoggato.getNumeroEsamiMancanti());
        capacitaRichiesta.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_SKILLRICHIESTE)));
        media.setText("Requisito richiesto: " + cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_MEDIAVOTOMINIMA)) +
                "\nTesista: " + Utility.tesistaLoggato.getMedia());

        //Relatore
        Cursor cursorRelatore = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + " " +
                "FROM " + Database.UTENTI + " u, " + Database.RELATORE + " r, " + Database.TESI + " t " +
                "WHERE t." + Database.TESI_RELATOREID + "=r." + Database.RELATORE_ID + " AND r." + Database.RELATORE_UTENTEID + "=u." + Database.UTENTI_ID + " " +
                "AND t." + Database.TESI_ID + "=" + richiesta.getIdTesi() + ";");
        cursorRelatore.moveToFirst();
        labelRelatore.setText("Relatore");
        relatore.setText(cursorRelatore.getString(cursorRelatore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorRelatore.getString(cursorRelatore.getColumnIndexOrThrow(Database.UTENTI_NOME)));

        capacitaEffettive.setText(richiesta.getCapacitàStudente());
        messaggioTesista.setText(richiesta.getMessaggio());
        if(richiesta.getRisposta() != null){
            testoRispostaRelatore.setText(richiesta.getRisposta());
        } else {
            testoRispostaRelatore.setText("In attesa della risposta del relatore");
        }
    }
}