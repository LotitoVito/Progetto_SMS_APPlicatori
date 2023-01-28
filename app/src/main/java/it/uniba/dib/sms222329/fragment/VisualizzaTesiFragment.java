package it.uniba.dib.sms222329.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Tesi;


public class VisualizzaTesiFragment extends BottomSheetDialogFragment {
    Tesi tesi;

    public  VisualizzaTesiFragment(){}
    public VisualizzaTesiFragment(Tesi tesi) {
        this.tesi = tesi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_visualizza_tesi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView titolo = getView().findViewById(R.id.titoloTesi);
        TextView argomento = getView().findViewById(R.id.argomentoTesi);
        TextView tempistiche = getView().findViewById(R.id.tempistiche);
        TextView esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        TextView capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        TextView media = getView().findViewById(R.id.media);
        SwitchMaterial disponibilita = getView().findViewById(R.id.disponibilita);

        titolo.setText(tesi.getTitolo());
        argomento.setText(tesi.getArgomenti());
        tempistiche.setText(String.valueOf(tesi.getTempistiche()));
        esamiMancanti.setText(String.valueOf(tesi.getEsamiMancantiNecessari()));
        capacitaRichiesta.setText(tesi.getCapacitaRichieste());
        media.setText(String.valueOf(tesi.getMediaVotiMinima()));
        disponibilita.setChecked(tesi.getStatoDisponibilita());
    }
}