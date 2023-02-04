package it.uniba.dib.sms222329.fragment.relatore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.fragment.task.CreaTaskFragment;
import it.uniba.dib.sms222329.fragment.task.ListaTaskFragment;

public class TesistaRelatoreFragment extends Fragment {

    //Variabili e Oggetti
    private TesiScelta tesiScelta;

    //View Items
    private TextView tesista;
    private TextView titoloTesi;
    private TextView argomentoTesi;
    private TextView riassunto;
    private TextView tempistiche;
    private TextView esamiMancanti;
    private TextView capacitaRichiesta;
    private TextView capacitaEffettive;
    private TextView dataConsegna;
    private TextView corelatore;
    private TextView emailCorelatore;
    private Button aggiungiCorelatore;
    private Button rimuoviCorelatore;
    private TextView creaTask;
    private TextView mostraTask;
    private Button scaricaTesi;
    private Button caricaTesi;

    public TesistaRelatoreFragment(TesiScelta tesiScelta) {
        this.tesiScelta = tesiScelta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetTextAll();

        aggiungiCorelatore.setOnClickListener(view -> {

        });

        rimuoviCorelatore.setOnClickListener(view -> {

        });

        creaTask.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new CreaTaskFragment(tesiScelta));
        });

        mostraTask.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ListaTaskFragment(tesiScelta));
        });

        scaricaTesi.setOnClickListener(view -> {

        });

        caricaTesi.setOnClickListener(view -> {

        });
    }

    private void Init() {
        tesista = getView().findViewById(R.id.nomeStudente);
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomentoTesi = getView().findViewById(R.id.argomentoTesi);
        riassunto = getView().findViewById(R.id.riassunto);
        tempistiche = getView().findViewById(R.id.tempistiche);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        capacitaEffettive = getView().findViewById(R.id.capacitaEffettive);
        dataConsegna = getView().findViewById(R.id.dataConsegna);
        corelatore = getView().findViewById(R.id.corelatore);
        emailCorelatore = getView().findViewById(R.id.email_corelatore);
        aggiungiCorelatore = getView().findViewById(R.id.aggiungi_corelatore);
        rimuoviCorelatore = getView().findViewById(R.id.rimuovi_corelatore);
        creaTask = getView().findViewById(R.id.crea_task);
        mostraTask = getView().findViewById(R.id.visualizza_task);
        scaricaTesi = getView().findViewById(R.id.scarica);
        caricaTesi = getView().findViewById(R.id.carica);

        if(tesiScelta.getIdCorelatore() != 0){
            aggiungiCorelatore.setVisibility(View.GONE);
            rimuoviCorelatore.setVisibility(View.VISIBLE);
        } else{
            aggiungiCorelatore.setVisibility(View.VISIBLE);
            rimuoviCorelatore.setVisibility(View.GONE);
        }
    }

    private void SetTextAll(){
        //da fare
    }
}