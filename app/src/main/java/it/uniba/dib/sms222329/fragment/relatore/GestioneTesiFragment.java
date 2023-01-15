package it.uniba.dib.sms222329.fragment.relatore;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;

public class GestioneTesiFragment extends Fragment {
    boolean operazioneModifica;
    Tesi tesi;
    Relatore relatoreLoggato;

    public GestioneTesiFragment(Relatore relatoreLoggato) {
        this.operazioneModifica = false;
        this.relatoreLoggato = relatoreLoggato;
    }

    public GestioneTesiFragment(Tesi tesi, Relatore relatoreLoggato) {
        this.tesi = tesi;
        this.operazioneModifica = true;
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gestione_tesi_relatore, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tesi_telatore, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        Context context = getActivity().getApplicationContext();
        Database db = new Database(context);
        TextInputEditText titolo = getView().findViewById(R.id.titoloTesi);
        TextInputEditText argomenti = getView().findViewById(R.id.argomenti_edit_text);
        TextInputEditText tempistiche = getView().findViewById(R.id.tempistiche_edit_text);
        TextInputEditText esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        TextInputEditText capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        TextInputEditText media = getView().findViewById(R.id.media_edit_text);
        SwitchMaterial statoDisponibilita = getView().findViewById(R.id.disponibilita);
        Button registra = getView().findViewById(R.id.add);

        registra.setOnClickListener(view -> {
            Tesi tesi = new Tesi(titolo.getText().toString(), argomenti.getText().toString(),
                    statoDisponibilita.isChecked(), relatoreLoggato.getIdRelatore(), tempistiche.getText().toString(),
                    Integer.parseInt(media.getText().toString()), Integer.parseInt(esamiMancanti.getText().toString()),
                    capacitaRichiesta.getText().toString());

            if(tesi.RegistrazioneTesi(db)){
                Toast.makeText(context, "Successo", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(context, "Registrazione fallita", Toast.LENGTH_SHORT).show();
            }
        });

    }
}