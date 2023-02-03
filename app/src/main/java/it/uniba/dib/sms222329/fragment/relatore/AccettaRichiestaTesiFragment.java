package it.uniba.dib.sms222329.fragment.relatore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RichiestaTesiDatabase;

public class AccettaRichiestaTesiFragment extends Fragment {

    //Variabili ed Oggetti
    private RichiestaTesi richiesta;
    private Database db;

    //View Items
    private TextView titoloTesi;
    private TextView argomento;
    private TextView tesista;
    private TextView tempistiche;
    private TextView esamiMancanti;
    private TextView capacitaRichiesta;
    private TextView capacitaEffettive;
    private TextView media;
    private TextView messaggioTesista;
    private TextView rispostaRelatore;
    private Button accetta;
    private Button rifiuta;


    public AccettaRichiestaTesiFragment(RichiestaTesi richiesta) {
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

        accetta.setOnClickListener(view -> {
            if(!db.VerificaDatoEsistente("SELECT * FROM " + Database.TESISCELTA + " WHERE " + Database.TESISCELTA_TESISTAID + "=" + richiesta.getIdTesista() + ";")){
                richiesta.setAccettata(RichiestaTesi.ACCETTATO);
                if(richiesta.AccettaRichiestaTesi(rispostaRelatore.getText().toString().trim(), db)){
                    Toast.makeText(getActivity().getApplicationContext(), "Richiesta accettata con successo", Toast.LENGTH_SHORT).show();
                    Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TesistiRelatoreFragment());
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Il tesista è già registrato per una tesi", Toast.LENGTH_SHORT).show();
            }

        });

        rifiuta.setOnClickListener(view -> {
            if(richiesta.RifiutaRichiestaTesi(rispostaRelatore.getText().toString().trim(), db)){
                Toast.makeText(getActivity().getApplicationContext(), "Richiesta rifiutata con successo", Toast.LENGTH_SHORT).show();
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TesistiRelatoreFragment());
            } else{
                Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Init(){
        db = new Database(getActivity().getApplicationContext());
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomento = getView().findViewById(R.id.argomentoTesi);
        tesista = getView().findViewById(R.id.nome);
        tempistiche = getView().findViewById(R.id.tempistiche);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        capacitaEffettive = getView().findViewById(R.id.capacitaEffettive);
        media = getView().findViewById(R.id.media);
        messaggioTesista = getView().findViewById(R.id.messaggio);
        rispostaRelatore = getView().findViewById(R.id.risposta);
        accetta = getView().findViewById(R.id.accetta);
        rifiuta = getView().findViewById(R.id.rifiuta);
    }

    private void ViewItemsSetText(){
        titoloTesi.setText(String.valueOf(richiesta.getIdTesi()));
        argomento.setText(String.valueOf(richiesta.getIdTesi()));
        tesista.setText(String.valueOf(richiesta.getIdTesista()));
        tempistiche.setText(String.valueOf(richiesta.getIdTesi()));
        esamiMancanti.setText(String.valueOf(richiesta.getIdTesi()));
        capacitaRichiesta.setText(String.valueOf(richiesta.getIdTesi()));
        capacitaEffettive.setText(richiesta.getCapacitàStudente());
        media.setText(String.valueOf(richiesta.getIdTesi()));
        messaggioTesista.setText(richiesta.getMessaggio());
    }
}