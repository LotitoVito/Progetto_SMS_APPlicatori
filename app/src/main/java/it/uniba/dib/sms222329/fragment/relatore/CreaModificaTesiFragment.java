package it.uniba.dib.sms222329.fragment.relatore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiDatabase;

public class CreaModificaTesiFragment extends Fragment {

    //Variabili e Oggetti
    private boolean operazioneModifica;
    private Tesi tesi;
    private Database db;

    //View Items
    private TextInputEditText titolo;
    private TextInputEditText argomenti;
    private TextInputEditText tempistiche;
    private TextInputEditText esamiMancanti;
    private TextInputEditText capacitaRichiesta;
    private TextInputEditText media;
    private SwitchMaterial statoDisponibilita;
    private Button salva;

    public CreaModificaTesiFragment() {
        this.operazioneModifica = false;
    }

    public CreaModificaTesiFragment(Tesi tesi) {
        this.tesi = tesi;
        this.operazioneModifica = true;
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

        Init();

        if(operazioneModifica){

            SetAllHint();
            salva.setOnClickListener(view -> {
                FillAllEmpty();
                if(tesi.ModificaTesi(titolo.getText().toString().trim(), argomenti.getText().toString().trim(), statoDisponibilita.isChecked(),
                        Integer.parseInt(tempistiche.getText().toString().trim()), Float.parseFloat(media.getText().toString().trim()),
                        Integer.parseInt(esamiMancanti.getText().toString().trim()), capacitaRichiesta.getText().toString().trim(), db)){
                    Toast.makeText(getActivity().getApplicationContext(), "Modifica effettuata con successo", Toast.LENGTH_SHORT).show();
                    Utility.goBack(getActivity());
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "Modifica fallita", Toast.LENGTH_SHORT).show();
                }
            });

        } else{

            salva.setOnClickListener(view -> {
                if(!IsEmpty(titolo, argomenti, tempistiche, media, esamiMancanti, capacitaRichiesta)) {
                Tesi tesi = new Tesi(titolo.getText().toString().trim(), argomenti.getText().toString().trim(),
                        statoDisponibilita.isChecked(), Utility.relatoreLoggato.getIdRelatore(), Integer.parseInt(tempistiche.getText().toString().trim()),
                        Integer.parseInt(media.getText().toString().trim()), Integer.parseInt(esamiMancanti.getText().toString().trim()),
                        capacitaRichiesta.getText().toString().trim());

                    if (TesiDatabase.RegistrazioneTesi(tesi, db)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Tesi registrata con successo", Toast.LENGTH_SHORT).show();
                        Utility.goBack(getActivity());
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Registrazione fallita", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Compilare tutti i campi obbligatori", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init(){
        db = new Database(getActivity().getApplicationContext());
        titolo = getView().findViewById(R.id.titoloTesi);
        argomenti = getView().findViewById(R.id.argomenti_edit_text);
        tempistiche = getView().findViewById(R.id.tempistiche_edit_text);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        media = getView().findViewById(R.id.media_edit_text);
        statoDisponibilita = getView().findViewById(R.id.disponibilita);
        salva = getView().findViewById(R.id.add);
    }

    /**
     * Imposta il testo per ogni elemento della view come suggerimento
     */
    private void SetAllHint(){
        titolo.setHint(tesi.getTitolo());
        argomenti.setHint(tesi.getArgomenti());
        tempistiche.setHint(String.valueOf(tesi.getTempistiche()));
        esamiMancanti.setHint(String.valueOf(tesi.getEsamiMancantiNecessari()));
        capacitaRichiesta.setHint(tesi.getCapacitaRichieste());
        media.setHint(String.valueOf(tesi.getMediaVotiMinima()));
        statoDisponibilita.setChecked(tesi.getStatoDisponibilita());
    }

    /**
     * Riempie i campi vuoti con il giusto valore se sono vuoti
     */
    private void FillAllEmpty(){
        Utility.fillIfEmpty(titolo, tesi.getTitolo());
        Utility.fillIfEmpty(argomenti, tesi.getArgomenti());
        Utility.fillIfEmpty(tempistiche, String.valueOf(tesi.getTempistiche()));
        Utility.fillIfEmpty(esamiMancanti, String.valueOf(tesi.getEsamiMancantiNecessari()));
        Utility.fillIfEmpty(capacitaRichiesta, tesi.getCapacitaRichieste());
        Utility.fillIfEmpty(media, String.valueOf(tesi.getMediaVotiMinima()));
    }

    /**
     * Metodo che verifica se i campi obbligatori sono vuoti, nel caso siano vuoti sono contrassegnati;
     * @param titolo
     * @param argomenti
     * @param tempistiche
     * @param media
     * @param esamiMancanti
     * @param capacitaRichiesta
     * @return  Il metodo restituisce true se almeno un campo è vuoto, restituisce false se tutti i campi non sono vuoti
     */
    private boolean IsEmpty(EditText titolo, EditText argomenti, EditText tempistiche, EditText media, EditText esamiMancanti, EditText capacitaRichiesta){
        boolean risultato = false;

        if(Utility.isEmptyTextbox(titolo)){
            risultato = true;
            titolo.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(argomenti)){
            risultato = true;
            argomenti.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(tempistiche)){
            risultato = true;
            tempistiche.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(media)){
            risultato = true;
            media.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(esamiMancanti)){
            risultato = true;
            esamiMancanti.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(capacitaRichiesta)){
            risultato = true;
            capacitaRichiesta.setError("Obbligatorio");
        }
        return risultato;
    }

}