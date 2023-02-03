package it.uniba.dib.sms222329.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

import it.uniba.dib.sms222329.R;

public class CreaRicevimentoFragment extends Fragment {


    EditText data;
    EditText ora;

    public CreaRicevimentoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crea_ricevimento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = getActivity().findViewById(R.id.data1);
        ora = getActivity().findViewById(R.id.ora);

        data.setOnClickListener(view12 -> {
            MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
            materialDateBuilder.setTitleText("SELECT A DATE");
            final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

            materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

            materialDatePicker.addOnPositiveButtonClickListener(
                    selection -> {
                        data.setText(materialDatePicker.getHeaderText());
                    });

        });

        ora.setOnClickListener(view1 -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .build();

            materialTimePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_TIME_PICKER");

            materialTimePicker.addOnPositiveButtonClickListener(
                    selection -> {
                        ora.setText(materialTimePicker.getHour()+":"+materialTimePicker.getMinute());
                    });
        });
    }
}