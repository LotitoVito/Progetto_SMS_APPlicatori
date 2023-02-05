package it.uniba.dib.sms222329.fragment.task;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TaskDatabase;
import it.uniba.dib.sms222329.fragment.relatore.TesistaRelatoreFragment;

public class CreaTaskFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private TesiScelta tesiScelta;
    private File file;

    //View Items
    private TextInputEditText titoloTask;
    private TextInputEditText descrizioneTask;
    private TextView materiale;
    private Button caricaMateriale;
    private Button creaTask;
    private EditText data;
    private EditText ora;
    private LocalDate dataSelezionata;
    private LocalTime orarioSelezionato;

    public CreaTaskFragment(TesiScelta tesiScelta) {
        this.tesiScelta = tesiScelta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crea_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data.setOnClickListener(view1 -> {
            MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
            materialDateBuilder.setTitleText("SELECT A DATE");
            final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

            materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                data.setText(materialDatePicker.getHeaderText());

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                dataSelezionata  = LocalDate.parse(format.format(calendar.getTime()));
            });

        });

        ora.setOnClickListener(view1 -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .build();

            materialTimePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_TIME_PICKER");

            materialTimePicker.addOnPositiveButtonClickListener(selection -> {
                ora.setText(materialTimePicker.getHour()+":"+materialTimePicker.getMinute());
                orarioSelezionato = LocalTime.parse(materialTimePicker.getHour() + ":" + materialTimePicker.getMinute());
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();

        creaTask.setOnClickListener(view -> {
            Task task = new Task(titoloTask.getText().toString(), descrizioneTask.getText().toString(), null, tesiScelta.getIdTesiScelta());
            if(TaskDatabase.CreaTask(db, task)){
                Toast.makeText(getActivity().getApplicationContext(), "Task creata con successo", Toast.LENGTH_SHORT).show();
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TesistaRelatoreFragment(tesiScelta));
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        titoloTask = getView().findViewById(R.id.nome_task);
        descrizioneTask = getView().findViewById(R.id.descrizione_task);
        materiale = getView().findViewById(R.id.materiale_nome);
        caricaMateriale = getView().findViewById(R.id.carica_materiale);
        creaTask = getView().findViewById(R.id.salva_task);
    }

    //fixare
    private boolean CheckStorage(){
        if(isExternalStorageAvailable() && isExternalStorageReadOnly()){
            if (CheckPermessi()){
                return true;
            } else{
                Toast.makeText(getActivity().getApplicationContext(), "Permessi bloccati", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(getActivity().getApplicationContext(), "Non è possibile accedere ai file", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean CheckPermessi(){
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission_group.STORAGE)){
                return false;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission_group.STORAGE}, Utility.PERMESSO_STORAGE);
                if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            String path = uri.getPath();
            file = new File(path);
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