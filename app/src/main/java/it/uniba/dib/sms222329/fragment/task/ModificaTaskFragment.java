package it.uniba.dib.sms222329.fragment.task;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;

import java.io.File;
import java.nio.file.Paths;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.activities.LoggedActivity;
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TaskDatabase;
import it.uniba.dib.sms222329.fragment.CreaRicevimentoFragment;
import it.uniba.dib.sms222329.fragment.relatore.TesistaRelatoreFragment;

public class ModificaTaskFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Task task;

    //View Items
    private TextView titoloTask;
    private TextView descrizioneTask;
    private TextView dateInizioFine;
    private RangeSlider sliderStato;
    private TextView testoStato;
    private ImageView materiale;
    private Button scaricaMateriale;
    private Button caricaMateriale;
    private TextView creaRicevimento;
    private Button modificaTask;

    public ModificaTaskFragment(Task task) {
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
        CheckFilesButton();

        modificaTask.setOnClickListener(view -> {
            if(Utility.accountLoggato == Utility.RELATORE){
                if(task.ModificaTask(titoloTask.getText().toString(), descrizioneTask.getText().toString(), 0, db)){        //settare slider
                    Toast.makeText(getActivity().getApplicationContext(), "Modifica effettuata con successo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            }

            if(Utility.accountLoggato == Utility.TESISTA){
                if(task.ModificaTask(0, db)){        //settare slider
                    Toast.makeText(getActivity().getApplicationContext(), "Modifica effettuata con successo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            }
        });

        caricaMateriale.setOnClickListener(view -> {
            if(Utility.CheckStorage(getActivity())){
                ScegliFile();
            }
        });
    }

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
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

        creaRicevimento.setVisibility(View.GONE);
    }

    private void SetTextAll() {
        titoloTask.setText(task.getTitolo());
        descrizioneTask.setText(task.getDescrizione());
        dateInizioFine.setText(task.getDataInizio() + " - " + task.getDataFine());
        testoStato.setText(String.valueOf(task.getStato()));
        //materiale.setText(String.valueOf(task.getLinkMateriale()));
    }

    private void CheckFilesButton(){
        if(task.getLinkMateriale() == null){
            scaricaMateriale.setVisibility(View.GONE);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            String path = uri.getPath();
            File file = new File(path);

            if(TaskDatabase.CaricaFile(db, file, task.getIdTask())){
                Toast.makeText(getActivity().getApplicationContext(), "Caricamento avvenuto con successo", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ScegliFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Seleziona un file"), 10);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}