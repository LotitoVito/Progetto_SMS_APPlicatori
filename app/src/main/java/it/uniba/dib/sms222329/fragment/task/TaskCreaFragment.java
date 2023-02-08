package it.uniba.dib.sms222329.fragment.task;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TaskDatabase;

public class TaskCreaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private TesiScelta tesiScelta;
    private LocalDate dataSelezionata;
    private File file;

    //View Items
    private TextInputEditText titoloTask;
    private TextInputEditText descrizioneTask;
    private TextInputEditText dataFine;
    private Slider slider;
    private TextView materiale;
    private Button caricaMateriale;
    private Button creaTask;

    public TaskCreaFragment(TesiScelta tesiScelta) {
        this.tesiScelta = tesiScelta;
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

        creaTask.setOnClickListener(view -> {
            if(!IsEmpty(titoloTask, descrizioneTask, dataFine)){
                Task task = new Task(titoloTask.getText().toString().trim(), descrizioneTask.getText().toString().trim(), dataSelezionata, null, tesiScelta.getIdTesiScelta());
                if(TaskDatabase.CreaTask(db, task)){
                    Toast.makeText(getActivity().getApplicationContext(), "Task creata con successo", Toast.LENGTH_SHORT).show();
                    Utility.goBack(getActivity());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Compilare i campi obbligatori", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        titoloTask = getView().findViewById(R.id.titolo_task);
        descrizioneTask = getView().findViewById(R.id.descrizione_task);
        dataFine = getView().findViewById(R.id.data_fine);
        slider = getView().findViewById(R.id.slider);
        materiale = getView().findViewById(R.id.materiale_nome);
        caricaMateriale = getView().findViewById(R.id.carica_materiale);
        creaTask = getView().findViewById(R.id.salva_task);

        slider.setVisibility(View.GONE);
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

    /**
     * Metodo che verifica se i campi obbligatori sono vuoti, nel caso siano vuoti sono contrassegnati;
     * @param titolo
     * @param argomento
     * @param dataFine
     * @return  Il metodo restituisce true se almeno un campo è vuoto, restituisce false se tutti i campi non sono vuoti
     */
    private boolean IsEmpty(EditText titolo, EditText argomento, EditText dataFine){
        boolean risultato = false;

        if(Utility.isEmptyTextbox(titolo)){
            risultato = true;
            titolo.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(argomento)){
            risultato = true;
            argomento.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(dataFine)){
            risultato = true;
            dataFine.setError("Obbligatorio");
        }
        return risultato;
    }
}