package it.uniba.dib.sms222329.fragment.task;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.activities.LoggedActivity;
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.CreaRicevimentoFragment;

public class DettagliTaskFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Task task;

    //View Items
    private TextView titoloTask;
    private TextView descrizioneTask;
    private TextView dateInizioFine;
    private Slider sliderStato;
    private TextView testoStato;
    private TextView materiale;
    private Button scaricaMateriale;
    private Button caricaMateriale;
    private TextView creaRicevimento;
    private Button modificaTask;
    private MotionLabel label;

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
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new CreaRicevimentoFragment(task));
        });

        scaricaMateriale.setOnClickListener(view -> {
            if(Utility.CheckStorage(getActivity())){
                /*Cursor cursor = db.RicercaDato("SELECT " + Database.TASK_LINKMATERIALE + " FROM " + Database.TASK + " WHERE " + Database.TASK_ID + "=" + task.getIdTask() + ";");
                try{
                    File file = new File("/sdcard/a.png");
                    file.createNewFile();
                    file.setWritable(true);
                    OutputStream os = new FileOutputStream(file);
                    os.write(bitmap);
                } catch (Exception e){
                    e.printStackTrace();
                }*/
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Utility.PERMESSO_STORAGE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity().getApplicationContext(), "Permesso concesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Permesso non concesso", Toast.LENGTH_SHORT).show();
            }
        }
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
        label = getView().findViewById(R.id.label_ricevimento);

        caricaMateriale.setVisibility(View.GONE);
        modificaTask.setVisibility(View.GONE);
        if(Utility.accountLoggato == Utility.RELATORE || Utility.accountLoggato == Utility.CORELATORE){
            label.setVisibility(View.GONE);
            creaRicevimento.setVisibility(View.GONE);
        }

        sliderStato.setValue((float) (25.0 * task.getStato()));
        sliderStato.setEnabled(false);
    }

    private void SetTextAll() {
        titoloTask.setText(task.getTitolo());
        descrizioneTask.setText(task.getDescrizione());
        dateInizioFine.setText("Data inizio: " + task.getDataInizio().format(Utility.showDate) + " - Data fine: " + task.getDataFine().format(Utility.showDate));

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

        materiale.setText(String.valueOf(task.getLinkMateriale()));
    }

}