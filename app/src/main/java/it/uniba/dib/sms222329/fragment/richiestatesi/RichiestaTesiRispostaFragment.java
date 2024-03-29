package it.uniba.dib.sms222329.fragment.richiestatesi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.database.Database;

public class RichiestaTesiRispostaFragment extends Fragment {

    //Variabili ed Oggetti
    private RichiestaTesi richiesta;
    private Database db;

    //View Items
    private TextView titoloTesi;
    private TextView argomento;
    private MotionLabel labelTesista;
    private TextView tesista;
    private TextView tempistiche;
    private TextView esamiMancanti;
    private TextView capacitaRichiesta;
    private TextView capacitaEffettive;
    private TextView media;
    private TextView messaggioTesista;
    private TextView labelRispostaRelatore;
    private TextView rispostaRelatore;
    private Button accetta;
    private Button rifiuta;


    public RichiestaTesiRispostaFragment(RichiestaTesi richiesta) {
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

        accetta.setOnClickListener(view -> {
            if(!db.VerificaDatoEsistente("SELECT * FROM " + Database.TESISCELTA + " WHERE " + Database.TESISCELTA_TESISTAID + "=" + richiesta.getIdTesista() + ";")){
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.conferma)
                        .setMessage(R.string.richiesta_accetta_conferma)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AccettaRichiestaTesi();
                            }
                        })
                        .setNegativeButton(R.string.indietro, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.richiesta_tesista_errore, Toast.LENGTH_SHORT).show();
            }

        });

        rifiuta.setOnClickListener(view -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.conferma)
                    .setMessage(R.string.richiesta_rifiuta_conferma)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RifiutaRichiestaTesi();
                        }
                    })
                    .setNegativeButton(R.string.indietro, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init(){
        db = new Database(getActivity().getApplicationContext());
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomento = getView().findViewById(R.id.argomentoTesi);
        labelTesista = getView().findViewById(R.id.label_nome);
        tesista = getView().findViewById(R.id.nome);
        tempistiche = getView().findViewById(R.id.tempistiche);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        capacitaEffettive = getView().findViewById(R.id.capacitaEffettive);
        media = getView().findViewById(R.id.media);
        messaggioTesista = getView().findViewById(R.id.messaggio);
        labelRispostaRelatore = getView().findViewById(R.id.label_risposta_relatore);
        rispostaRelatore = getView().findViewById(R.id.risposta);
        accetta = getView().findViewById(R.id.accetta);
        rifiuta = getView().findViewById(R.id.rifiuta);

        if(Utility.accountLoggato == Utility.RELATORE){
            labelRispostaRelatore.setVisibility(View.GONE);
        }
    }

    /**
     * Imposta il testo per ogni elemento della view
     */
    private void SetTextAll(){
        //Tesi
        Cursor cursorTesi = db.RicercaDato("SELECT * FROM " + Database.TESI + " WHERE " + Database.TESI_ID + "=" + richiesta.getIdTesi() + ";");
        cursorTesi.moveToFirst();
        Cursor cursorTesista = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + ", t." + Database.TESISTA_MEDIAVOTI + ", t." + Database.TESISTA_ESAMIMANCANTI + ", t." + Database.TESISTA_MATRICOLA +
                " FROM " + Database.TESISTA + " t, " + Database.UTENTI + " u " +
                " WHERE t." + Database.TESISTA_UTENTEID + "=u." + Database.UTENTI_ID + " " +
                " AND t." + Database.TESISTA_ID + "=" + richiesta.getIdTesista() + ";");
        cursorTesista.moveToFirst();
        titoloTesi.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TITOLO)));
        argomento.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_ARGOMENTO)));
        tempistiche.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TEMPISTICHE)));
        esamiMancanti.setText(getResources().getString(R.string.requisito_richiesto) + cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_ESAMINECESSARI)) +
                "\n" + getResources().getString(R.string.tesista) + ": " + cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.TESISTA_ESAMIMANCANTI)));
        media.setText(getResources().getString(R.string.requisito_richiesto) + cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_MEDIAVOTOMINIMA)) +
                "\n" + getResources().getString(R.string.tesista) + ": " + cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.TESISTA_MEDIAVOTI)));
        capacitaRichiesta.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_SKILLRICHIESTE)));

        //Tesista
        labelTesista.setText(getResources().getString(R.string.tesista));
        tesista.setText(cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.UTENTI_NOME)) + " " + cursorTesista.getString(cursorTesista.getColumnIndexOrThrow(Database.TESISTA_MATRICOLA)));

        //Richiesta
        capacitaEffettive.setText(richiesta.getCapacitàStudente());
        messaggioTesista.setText(richiesta.getMessaggio());
    }

    private void AccettaRichiestaTesi(){
        richiesta.setStato(RichiestaTesi.ACCETTATO);
        if(richiesta.AccettaRichiestaTesi(rispostaRelatore.getText().toString().trim(), db)){
            Toast.makeText(getActivity().getApplicationContext(), R.string.richiesta_accetta_successo, Toast.LENGTH_SHORT).show();
            Utility.goBack(getActivity());
        } else{
            Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
        }
    }

    private void RifiutaRichiestaTesi(){
        if(richiesta.RifiutaRichiestaTesi(rispostaRelatore.getText().toString().trim(), db)){
            Toast.makeText(getActivity().getApplicationContext(), R.string.richiesta_rifiuta_successo, Toast.LENGTH_SHORT).show();
            Utility.goBack(getActivity());
        } else{
            Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
        }
    }
}