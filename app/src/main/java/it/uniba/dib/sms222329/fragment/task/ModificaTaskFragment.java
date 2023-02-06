package it.uniba.dib.sms222329.fragment.task;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MotionLabel;
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

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.activities.LoggedActivity;
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TaskDatabase;
import it.uniba.dib.sms222329.fragment.CreaRicevimentoFragment;
import it.uniba.dib.sms222329.fragment.relatore.TesistaRelatoreFragment;

public class ModificaTaskFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Task task;
    private TesiScelta tesiScelta;
    private LocalDate dataSelezionata;
    private File file;

    //View Items
    private TextInputEditText titoloTask;
    private TextInputEditText descrizioneTask;
    private TextInputEditText dataFine;
    private Slider sliderStato;
    private TextView testoStato;
    private TextView materiale;
    private Button caricaMateriale;
    private Button modificaTask;
    private MotionLabel labelDataFine;

    public ModificaTaskFragment(Task task) {
        this.task = task;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crea_task, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();

        dataFine.setOnClickListener(view1 -> {
            MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
            materialDateBuilder.setTitleText("SELECT A DATE");
            final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

            materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                dataFine.setText(materialDatePicker.getHeaderText());

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                dataSelezionata  = LocalDate.parse(format.format(calendar.getTime()));
            });
        });

        sliderStato.addOnChangeListener((slider, value, fromUser) -> {
            if(value==0){
                testoStato.setText("Assegnato");
            } else if(value==25){
                testoStato.setText("Iniziato");
            } else if(value==50){
                testoStato.setText("In completamento");
            } else if(value==75){
                testoStato.setText("In revisione");
            } else if(value==100){
                testoStato.setText("Completato");
            }
        });

        modificaTask.setOnClickListener(view -> {
            if(Utility.accountLoggato == Utility.RELATORE){
                if(task.ModificaTask(titoloTask.getText().toString(), descrizioneTask.getText().toString(), dataSelezionata, (int) (sliderStato.getValue()/25), db)){        //settare slider
                    Toast.makeText(getActivity().getApplicationContext(), "Modifica effettuata con successo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            }

            if(Utility.accountLoggato == Utility.TESISTA){
                if(task.ModificaTask((int) (sliderStato.getValue()/25), db)){        //settare slider
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
        dataFine = getView().findViewById(R.id.data_fine);
        sliderStato = getView().findViewById(R.id.slider);
        testoStato = getView().findViewById(R.id.stato);
        materiale = getView().findViewById(R.id.materiale_nome);
        caricaMateriale = getView().findViewById(R.id.carica_materiale);
        modificaTask = getView().findViewById(R.id.salva_task);
        labelDataFine = getView().findViewById(R.id.label_data_fine);

        if(Utility.accountLoggato == Utility.TESISTA){
            titoloTask.setVisibility(View.GONE);
            descrizioneTask.setVisibility(View.GONE);
            labelDataFine.setVisibility(View.GONE);
            dataFine.setVisibility(View.GONE);
        }

        sliderStato.setValue((float) (25.0 * task.getStato()));

        if(sliderStato.getValue()==0){
            testoStato.setText("Assegnato");
        } else if(sliderStato.getValue()==25){
            testoStato.setText("Iniziato");
        } else if(sliderStato.getValue()==50){
            testoStato.setText("In completamento");
        } else if(sliderStato.getValue()==75){
            testoStato.setText("In revisione");
        } else if(sliderStato.getValue()==100){
            testoStato.setText("Completato");
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