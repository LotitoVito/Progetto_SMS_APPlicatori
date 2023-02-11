package it.uniba.dib.sms222329.fragment.segnalazione;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class SegnalazioneCreaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Tesi tesi;
    private int idUtente;

    //View Items
    private TextInputEditText oggetto;
    private TextInputEditText messaggioTesto;
    private Button inviaMessaggio;

    public SegnalazioneCreaFragment(Tesi tesi) {
        this.tesi = tesi;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_segnalazione_chat_crea, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();

        inviaMessaggio.setOnClickListener(view -> {
            idUtente = getIdUtenteLoggato();
            if(idUtente != -1){
                if(!IsEmpty(oggetto, messaggioTesto)){
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.conferma)
                            .setMessage(R.string.segnalazione_crea_conferma)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CreaSegnalazioneChat();
                                }
                            })
                            .setNegativeButton(R.string.indietro, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.campi_vuoti_errore, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
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
            oggetto.setError(getActivity().getApplicationContext().getResources().getString(R.string.campo_obbligatorio));
        }
        if(Utility.isEmptyTextbox(messaggioTesto)){
            risultato = true;
            messaggioTesto.setError(getActivity().getApplicationContext().getResources().getString(R.string.campo_obbligatorio));
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

    /**
     * Metodo di creazione di una segnalazione
     */
    private void CreaSegnalazioneChat(){
        SegnalazioneChat chat = new SegnalazioneChat(oggetto.getText().toString().trim(), tesi.getIdTesi());
        SegnalazioneMessaggio messaggio = new SegnalazioneMessaggio(messaggioTesto.getText().toString().trim(), idUtente);
        if(SegnalazioneDatabase.AvviaChat(db, chat, messaggio)){
            Toast.makeText(getActivity().getApplicationContext(), R.string.segnalazione_crea_successo, Toast.LENGTH_SHORT).show();
            Utility.goBack(getActivity());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
        }
    }
}