package it.uniba.dib.sms222329.fragment.task;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.activities.LoggedActivity;
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.fragment.CreaRicevimentoFragment;

public class DettagliTaskFragment extends Fragment {

    //Variabili e Oggetti
    private Task task;

    //View Items
    private TextView titoloTask;
    private TextView descrizioneTask;
    private TextView dateInizioFine;
    private RangeSlider sliderStato;
    private TextView testoStato;
    private TextView materiale;
    private Button scaricaMateriale;
    private Button caricaMateriale;
    private TextView creaRicevimento;
    private Button modificaTask;

    public DettagliTaskFragment(Task task) {
        this.task = task;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dettagli_task, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetTextAll();

        creaRicevimento.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new CreaRicevimentoFragment());
        });

        scaricaMateriale.setOnClickListener(view -> {
            //da fare
        });
    }

    private void Init() {
        titoloTask = getView().findViewById(R.id.titolo_task);
        descrizioneTask = getView().findViewById(R.id.descrizione_task);
        dateInizioFine = getView().findViewById(R.id.date_inizio_fine);
        sliderStato = getView().findViewById(R.id.slider);
        testoStato = getView().findViewById(R.id.stato);
        materiale = getView().findViewById(R.id.materiale_nome);
        scaricaMateriale = getView().findViewById(R.id.scarica_materiale);
        caricaMateriale = getView().findViewById(R.id.carica_materiale);
        creaRicevimento = getView().findViewById(R.id.crea_ricevimento);
        modificaTask = getView().findViewById(R.id.modifica_task);

        sliderStato.setVisibility(View.GONE);
        caricaMateriale.setVisibility(View.GONE);
        modificaTask.setVisibility(View.GONE);
        if(LoggedActivity.accountLoggato == Utility.RELATORE){
            creaRicevimento.setVisibility(View.GONE);
        }
    }

    private void SetTextAll() {
        titoloTask.setText(task.getTitolo());
        descrizioneTask.setText(task.getDescrizione());
        dateInizioFine.setText(task.getDataInizio() + " - " + task.getDataFine());
        testoStato.setText(String.valueOf(task.getStato()));
        materiale.setText(String.valueOf(task.getLinkMateriale()));
    }
}