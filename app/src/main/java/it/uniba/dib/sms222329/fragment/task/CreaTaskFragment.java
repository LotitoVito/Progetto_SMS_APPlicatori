package it.uniba.dib.sms222329.fragment.task;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.dib.sms222329.R;

public class CreaTaskFragment extends Fragment {

    public CreaTaskFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crea_task, container, false);
    }
}