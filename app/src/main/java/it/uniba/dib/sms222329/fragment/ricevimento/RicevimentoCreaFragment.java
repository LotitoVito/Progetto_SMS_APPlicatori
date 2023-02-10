package it.uniba.dib.sms222329.fragment.ricevimento;


import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RicevimentoDatabase;

public class RicevimentoCreaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Ricevimento richiesta;
    private Task task;
    private LocalDate dataSelezionata;
    private LocalTime orarioSelezionato;

    //View Items
    private EditText messaggio;
    private EditText data;
    private EditText ora;
    private Button invia;

    public RicevimentoCreaFragment(Task task) {
        this.task = task;
    }

    public RicevimentoCreaFragment(Ricevimento richiesta) {
        this.richiesta = richiesta;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ricevimento_crea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Init();

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

        invia.setOnClickListener(view1 -> {
            if(LocalDate.now().compareTo(dataSelezionata) < 0){
                if (Utility.accountLoggato != Utility.TESISTA) {
                    FillIfEmpty();
                    if(richiesta.ModificaRicevimento(db, dataSelezionata, orarioSelezionato)){
                        Toast.makeText(getActivity().getApplicationContext(), "Risposta inviata con successo", Toast.LENGTH_SHORT).show();
                        Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new RicevimentiCalendarioFragment());
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(!IsEmpty(data, ora, messaggio)){
                        Ricevimento richiesta = new Ricevimento(dataSelezionata, orarioSelezionato, task.getIdTask(), Ricevimento.IN_ATTESA_RELATORE, messaggio.getText().toString().trim());
                        if(RicevimentoDatabase.RichiestaRicevimento(db, richiesta)){
                            Toast.makeText(getActivity().getApplicationContext(), "Richiesta inviata con successo", Toast.LENGTH_SHORT).show();
                            Utility.goBack(getActivity());
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Compila i campi obbligatori", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "La data scelta è uguale o inferiore ad oggi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        messaggio = getView().findViewById(R.id.messaggio);
        data = getView().findViewById(R.id.data_richiesta);
        ora = getView().findViewById(R.id.ora_richiesta);
        invia = getView().findViewById(R.id.invia);

        if(Utility.accountLoggato==Utility.RELATORE){
            messaggio.setVisibility(View.GONE);
            FillIfEmpty();
        }
    }

    /**
     * Metodo che verifica se i campi obbligatori sono vuoti, nel caso siano vuoti sono contrassegnati;
     * @param data
     * @param ora
     * @param messaggio
     * @return  Il metodo restituisce true se almeno un campo è vuoto, restituisce false se tutti i campi non sono vuoti
     */
    private boolean IsEmpty(EditText data, EditText ora, EditText messaggio) {
        boolean risultato = false;

        if(Utility.isEmptyTextbox(data)){
            risultato = true;
            data.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(ora)){
            risultato = true;
            ora.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(messaggio)){
            risultato = true;
            messaggio.setError("Obbligatorio");
        }

        return risultato;
    }

    /**
     * Riempie i campi vuoti con il giusto valore se sono vuoti
     */
    private void FillIfEmpty() {
        Utility.fillIfEmpty(data, String.valueOf(richiesta.getData()));
        Utility.fillIfEmpty(ora, String.valueOf(richiesta.getOrario()));
    }
}