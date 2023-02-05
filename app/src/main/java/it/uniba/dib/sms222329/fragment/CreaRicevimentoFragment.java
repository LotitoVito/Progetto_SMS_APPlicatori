package it.uniba.dib.sms222329.fragment;


import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.relatore.HomeFragment;

public class CreaRicevimentoFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Ricevimento richiesta;
    private LocalDate dataSelezionata;
    private LocalTime orarioSelezionato;

    //View Items
    private EditText messaggio;
    private EditText data;
    private EditText ora;
    private Button invia;

    public CreaRicevimentoFragment() {}

    public CreaRicevimentoFragment(Ricevimento richiesta) {
        this.richiesta = richiesta;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crea_ricevimento, container, false);
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
                if(richiesta.ModificaRicevimento(db, dataSelezionata, orarioSelezionato)){
                    Toast.makeText(getActivity().getApplicationContext(), "Risposta inviata con successo", Toast.LENGTH_SHORT).show();
                    Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new HomeFragment());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "La data scelta è uguale o inferiore ad oggi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        messaggio = getView().findViewById(R.id.messaggio);
        data = getView().findViewById(R.id.data_richiesta);
        ora = getView().findViewById(R.id.ora_richiesta);
        invia = getView().findViewById(R.id.invia);

        if(Utility.accountLoggato==Utility.RELATORE){
            messaggio.setVisibility(View.GONE);
        }

        data.setText(String.valueOf(richiesta.getData()));
        ora.setText(String.valueOf(richiesta.getOrario()));
    }
}