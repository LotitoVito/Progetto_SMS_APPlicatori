package it.uniba.dib.sms222329.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import it.uniba.dib.sms222329.R;

public class SegnalazioniFilterFragment extends BottomSheetDialogFragment {

    public SegnalazioniFilterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_segnalazioni_filter, container, false);
    }
}