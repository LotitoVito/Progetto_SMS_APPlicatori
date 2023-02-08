package it.uniba.dib.sms222329.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.SegnalazioneChat;
import it.uniba.dib.sms222329.classi.SegnalazioneMessaggio;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.SegnalazioneDatabase;

public class CreaSegnalazioneFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Tesi tesi;

    //View Items
    private TextInputEditText oggetto;
    private TextInputEditText messaggioTesto;
    private Button inviaMessaggio;

    public CreaSegnalazioneFragment(Tesi tesi) {
        this.tesi = tesi;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crea_segnalazione, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();

        inviaMessaggio.setOnClickListener(view -> {
            int idUtente = getIdUtenteLoggato();
            if(idUtente != -1){
                if(!IsEmpty(oggetto, messaggioTesto)){
                    SegnalazioneChat chat = new SegnalazioneChat(oggetto.getText().toString().trim(), tesi.getIdTesi());
                    SegnalazioneMessaggio messaggio = new SegnalazioneMessaggio(messaggioTesto.getText().toString().trim(), idUtente);
                    if(SegnalazioneDatabase.AvviaChat(db, chat, messaggio)){
                        Toast.makeText(getActivity().getApplicationContext(), "Segnalazione creata con successo", Toast.LENGTH_SHORT).show();
                        Utility.goBack(getActivity());
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Compila i campi obbligatori", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metodo che verifica se i campi obbligatori sono vuoti, nel caso siano vuoti sono contrassegnati;
     * @param oggetto
     * @param messaggioTesto
     * @return  Il metodo restituisce true se almeno un campo è vuoto, restituisce false se tutti i campi non sono vuoti
     */
    private boolean IsEmpty(TextInputEditText oggetto, TextInputEditText messaggioTesto) {
        boolean risultato = false;

        if(Utility.isEmptyTextbox(oggetto)){
            risultato = true;
            oggetto.setError("Obbligatorio");
        }
        if(Utility.isEmptyTextbox(messaggioTesto)){
            risultato = true;
            messaggioTesto.setError("Obbligatorio");
        }

        return risultato;
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        oggetto = getView().findViewById(R.id.titolo_segnalazione);
        messaggioTesto = getView().findViewById(R.id.messaggio_segnalazione);
        inviaMessaggio = getView().findViewById(R.id.invia_messaggio);
    }

    /**
     * Il metodo recupera l'idUtente dell'utente loggato
     * @return  Restituisce il risultato della ricerca
     */
    private int getIdUtenteLoggato() {
        int risultato = -1;
        if(Utility.accountLoggato==Utility.TESISTA){
            risultato = Utility.tesistaLoggato.getIdUtente();
        } else if(Utility.accountLoggato==Utility.RELATORE){
            risultato = Utility.relatoreLoggato.getIdUtente();
        } else if(Utility.accountLoggato==Utility.CORELATORE){
            risultato = Utility.coRelatoreLoggato.getIdUtente();
        }
        return risultato;
    }
}