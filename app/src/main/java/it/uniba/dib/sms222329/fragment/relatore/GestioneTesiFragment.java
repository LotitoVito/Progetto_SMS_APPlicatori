package it.uniba.dib.sms222329.fragment.relatore;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiDatabase;

public class GestioneTesiFragment extends Fragment {
    boolean operazioneModifica;
    Tesi tesi;
    Relatore relatoreLoggato;

    public GestioneTesiFragment(){
    }

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

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if(selectedItemId != R.id.navigation_thesis) {
            bottomNavigationView.getMenu().findItem(R.id.navigation_thesis).setChecked(true);
        }

        return inflater.inflate(R.layout.fragment_gestione_tesi_relatore, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_actionbar, menu);
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
        Button salva = getView().findViewById(R.id.add);

        if(operazioneModifica){

            titolo.setHint(tesi.getTitolo());
            argomenti.setHint(tesi.getArgomenti());
            tempistiche.setHint(String.valueOf(tesi.getTempistiche()));
            esamiMancanti.setHint(String.valueOf(tesi.getEsamiMancantiNecessari()));
            capacitaRichiesta.setHint(tesi.getCapacitaRichieste());
            media.setHint(String.valueOf(tesi.getMediaVotiMinima()));
            statoDisponibilita.setChecked(tesi.getStatoDisponibilita());

            salva.setOnClickListener(view -> {
                fillIfEmpty(titolo, tesi.getTitolo());
                fillIfEmpty(argomenti, tesi.getArgomenti());
                fillIfEmpty(tempistiche, String.valueOf(tesi.getTempistiche()));
                fillIfEmpty(esamiMancanti, String.valueOf(tesi.getEsamiMancantiNecessari()));
                fillIfEmpty(capacitaRichiesta, tesi.getCapacitaRichieste());
                fillIfEmpty(media, String.valueOf(tesi.getMediaVotiMinima()));

                tesi.ModificaTesi(titolo.getText().toString(), argomenti.getText().toString(), statoDisponibilita.isChecked(),
                        Integer.parseInt(tempistiche.getText().toString()), Float.parseFloat(media.getText().toString()),
                        Integer.parseInt(esamiMancanti.getText().toString()), capacitaRichiesta.getText().toString());
                if(TesiDatabase.ModificaTesi(tesi, db)){
                    Toast.makeText(context, "Successo", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(context, "Modifica fallita", Toast.LENGTH_SHORT).show();
                }
            });

        } else{

            salva.setOnClickListener(view -> {
                Tesi tesi = new Tesi(titolo.getText().toString(), argomenti.getText().toString(),
                        statoDisponibilita.isChecked(), relatoreLoggato.getIdRelatore(), Integer.parseInt(tempistiche.getText().toString()),
                        Integer.parseInt(media.getText().toString()), Integer.parseInt(esamiMancanti.getText().toString()),
                        capacitaRichiesta.getText().toString());

                if(CheckEmpty(titolo, argomenti, tempistiche, media, esamiMancanti, capacitaRichiesta)) {
                    if (TesiDatabase.RegistrazioneTesi(tesi, db)) {
                        Toast.makeText(context, "Successo", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Registrazione fallita", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Compilare tutti i campi obbligatori", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void fillIfEmpty(TextInputEditText campo, String value){
        if(campo.getText().toString().matches("")){
            campo.autofill(AutofillValue.forText(value));
        }
    }

    private boolean CheckEmpty(EditText titolo, EditText argomenti, EditText tempistiche, EditText media, EditText esamiMancanti, EditText capacitaRichiesta){
        boolean risultato = true;

        if(isEmptyTextbox(titolo)){
            risultato = false;
        }
        if(isEmptyTextbox(argomenti)){
            risultato = false;
        }
        if(isEmptyTextbox(tempistiche)){
            risultato = false;
        }
        if(isEmptyTextbox(media)){
            risultato = false;
        }
        if(isEmptyTextbox(esamiMancanti)){
            risultato = false;
        }
        if(isEmptyTextbox(capacitaRichiesta)){
            risultato = false;
        }
        return risultato;
    }

    private boolean isEmptyTextbox(EditText textbox){
        if(textbox.getText().toString().trim().compareTo("")==0){
            textbox.setError("Obbligatorio");
            return false;
        }
        textbox.setError(null);
        return true;
    }
}