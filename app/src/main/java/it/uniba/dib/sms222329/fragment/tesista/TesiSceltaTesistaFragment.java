package it.uniba.dib.sms222329.fragment.tesista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.dib.sms222329.R;

public class TesiSceltaTesistaFragment extends Fragment {

    public TesiSceltaTesistaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi_scelta_tesistai, container, false);
    }
}