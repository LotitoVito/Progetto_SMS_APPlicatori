package it.uniba.dib.sms222329.fragment.tesi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaCorsiAdapter;

public class TesiCreaModificaFragment extends Fragment {

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
    private Spinner corsoStudi;
    private SwitchMaterial statoDisponibilita;
    private Button salva;

    public TesiCreaModificaFragment() {
        this.operazioneModifica = false;
    }

    public TesiCreaModificaFragment(Tesi tesi) {
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

        return inflater.inflate(R.layout.fragment_tesi_crea_modifica, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_actionbar, menu);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SpinnerCreate();

        if(operazioneModifica){

            FillAllEmpty();
            salva.setOnClickListener(view -> {
                if(Integer.parseInt(media.getText().toString())>=18 && Integer.parseInt(media.getText().toString())<=30){
                    FillAllEmpty();
                    if(tesi.ModificaTesi(titolo.getText().toString().trim(), argomenti.getText().toString().trim(), statoDisponibilita.isChecked(),
                            Integer.parseInt(tempistiche.getText().toString().trim()), Float.parseFloat(media.getText().toString().trim()),
                            Integer.parseInt(esamiMancanti.getText().toString().trim()), capacitaRichiesta.getText().toString().trim(),
                            RecuperaIdUniversitaCorso(), db)){
                        Toast.makeText(getActivity().getApplicationContext(), "Modifica effettuata con successo", Toast.LENGTH_SHORT).show();
                        Utility.goBack(getActivity());
                    } else{
                        Toast.makeText(getActivity().getApplicationContext(), "Modifica fallita", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "La media non è compresa tra 0 e 30", Toast.LENGTH_SHORT).show();
                }
            });

        } else {

            salva.setOnClickListener(view -> {
                if(!IsEmpty(titolo, argomenti, tempistiche, media, esamiMancanti, capacitaRichiesta)) {
                    if(Integer.parseInt(media.getText().toString())>=18 && Integer.parseInt(media.getText().toString())<=30){
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Conferma")
                                .setMessage("Creare la proposta di tesi?")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        CreaTesi();
                                    }
                                })
                                .setNegativeButton("Indietro", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "La media non è compresa tra 0 e 30", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Compilare tutti i campi obbligatori", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    /**
     * Metodo di creazione di una proposta di tesi
     */
    private void CreaTesi(){
        Tesi tesi = new Tesi(titolo.getText().toString().trim(), argomenti.getText().toString().trim(),
                statoDisponibilita.isChecked(), Utility.relatoreLoggato.getIdRelatore(), Integer.parseInt(tempistiche.getText().toString().trim()),
                Integer.parseInt(media.getText().toString().trim()), Integer.parseInt(esamiMancanti.getText().toString().trim()),
                capacitaRichiesta.getText().toString().trim(), RecuperaIdUniversitaCorso());

        if (TesiDatabase.RegistrazioneTesi(tesi, db)) {
            Toast.makeText(getActivity().getApplicationContext(), "Tesi registrata con successo", Toast.LENGTH_SHORT).show();
            Utility.goBack(getActivity());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Registrazione fallita", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo per la gestione dello spinner dei corsi di studio del relatore da impostare per la tesi
     */
    private void SpinnerCreate() {
        Cursor cursor = db.RicercaDato("SELECT cs." + Database.CORSOSTUDI_NOME +
                " FROM " + Database.CORSIRELATORE + " cr, " + Database.UNIVERSITACORSO + " uc, " + Database.CORSOSTUDI + " cs " +
                " WHERE cr." + Database.CORSIRELATORE_UNIVERSITACORSOID + "=uc." + Database.UNIVERSITACORSO_ID + " AND uc." + Database.UNIVERSITACORSO_CORSOID + "=cs." + Database.CORSOSTUDI_ID +
                " AND cr." + Database.CORSIRELATORE_RELATOREID + "=" + Utility.relatoreLoggato.getIdRelatore() + ";");

        ArrayList<String> nomiCorsi = new ArrayList<String>();
        while(cursor.moveToNext()){
            nomiCorsi.add(cursor.getString(cursor.getColumnIndexOrThrow(Database.CORSOSTUDI_NOME)));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item, nomiCorsi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        corsoStudi.setAdapter(adapter);

        if(operazioneModifica){
            Cursor cursorCorso = db.RicercaDato("SELECT cs." + Database.CORSOSTUDI_NOME +
                    " FROM " + Database.UNIVERSITACORSO + " uc, " + Database.CORSOSTUDI + " cs " +
                    " WHERE uc." + Database.UNIVERSITACORSO_CORSOID + "=cs." + Database.CORSOSTUDI_ID +
                    " AND uc." + Database.UNIVERSITACORSO_ID + "=" + tesi.getIdUniversitaCorso() + ";");
            cursorCorso.moveToFirst();
            corsoStudi.setSelection(adapter.getPosition(cursorCorso.getString(cursorCorso.getColumnIndexOrThrow(Database.CORSOSTUDI_NOME))));
        }
    }

    /**
     * Metodo per il recupero dell'id della coppia universita (del tesista) e corso selezionato
     * @return  restituisce l'id della coppia universita-corso
     */
    private int RecuperaIdUniversitaCorso(){
        //Recupero id universita
        Cursor cursorUniversita = db.RicercaDato("SELECT uc." + Database.UNIVERSITACORSO_UNIVERSITAID +
                " FROM " + Database.CORSIRELATORE + " cr, " + Database.UNIVERSITACORSO + " uc " +
                " WHERE cr." + Database.CORSIRELATORE_UNIVERSITACORSOID + "=uc." + Database.UNIVERSITACORSO_ID +
                " AND cr." + Database.CORSIRELATORE_RELATOREID + "=" + Utility.relatoreLoggato.getIdRelatore() + ";");
        cursorUniversita.moveToNext();

        //Recupero id universitacorso
        Cursor cursor = db.RicercaDato("SELECT uc." + Database.UNIVERSITACORSO_ID +
                " FROM " + Database.CORSOSTUDI + " cs, " + Database.UNIVERSITACORSO + " uc " +
                " WHERE cs." + Database.CORSOSTUDI_ID + "=uc." + Database.UNIVERSITACORSO_CORSOID +
                " AND cs." + Database.CORSOSTUDI_NOME + " LIKE ('" + corsoStudi.getSelectedItem().toString() + "') " +
                " AND uc." + Database.UNIVERSITACORSO_UNIVERSITAID + "=" + cursorUniversita.getInt(cursorUniversita.getColumnIndexOrThrow(Database.UNIVERSITACORSO_UNIVERSITAID)) + ";");
        cursor.moveToNext();
        return cursor.getInt(cursor.getColumnIndexOrThrow(Database.UNIVERSITACORSO_ID));
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
        corsoStudi = getView().findViewById(R.id.corso);
        statoDisponibilita = getView().findViewById(R.id.disponibilita);
        salva = getView().findViewById(R.id.add);
    }

    /**
     * Riempie i campi vuoti con il giusto valore se sono vuoti
     */
    private void FillAllEmpty(){
        Utility.fillIfEmpty(titolo, tesi.getTitolo());
        Utility.fillIfEmpty(argomenti, tesi.getArgomenti());
        Utility.fillIfEmpty(tempistiche, String.valueOf(tesi.getTempistiche()));
        Utility.fillIfEmpty(esamiMancanti, String.valueOf(tesi.getEsamiNecessari()));
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