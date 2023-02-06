package it.uniba.dib.sms222329.fragment.tesista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.relatore.TesistiRelatoreFragment;


public class DettagliRichiestaTesiFragment extends Fragment {

    //Variabili e Oggetti
    private RichiestaTesi richiesta;

    //View Items
    private TextView titoloTesi;
    private TextView argomento;
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

    public DettagliRichiestaTesiFragment(RichiestaTesi richiesta) {
        this.richiesta = richiesta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accetta_richiesta_tesi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        ViewItemsSetText();

    }

    private void Init(){
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomento = getView().findViewById(R.id.argomentoTesi);
        relatore = getView().findViewById(R.id.nome);
        tempistiche = getView().findViewById(R.id.tempistiche);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        capacitaEffettive = getView().findViewById(R.id.capacitaEffettive);
        media = getView().findViewById(R.id.media);
        messaggioTesista = getView().findViewById(R.id.messaggio);
        testoRispostaRelatore = getView().findViewById(R.id.risposta_relatore);
        rispostaRelatore = getView().findViewById(R.id.risposta);
        accetta = getView().findViewById(R.id.accetta);
        rifiuta = getView().findViewById(R.id.rifiuta);

        accetta.setVisibility(View.GONE);
        rifiuta.setVisibility(View.GONE);
        rispostaRelatore.setVisibility(View.GONE);
    }

    private void ViewItemsSetText(){
        titoloTesi.setText(String.valueOf(richiesta.getIdTesi()));
        argomento.setText(String.valueOf(richiesta.getIdTesi()));
        relatore.setText(String.valueOf(richiesta.getIdTesista()));
        tempistiche.setText(String.valueOf(richiesta.getIdTesi()));
        esamiMancanti.setText(String.valueOf(richiesta.getIdTesi()));
        capacitaRichiesta.setText(String.valueOf(richiesta.getIdTesi()));
        capacitaEffettive.setText(richiesta.getCapacitàStudente());
        media.setText(String.valueOf(richiesta.getIdTesi()));
        messaggioTesista.setText(richiesta.getMessaggio());
        testoRispostaRelatore.setText(richiesta.getRisposta());
    }
}