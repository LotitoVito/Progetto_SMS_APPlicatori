package it.uniba.dib.sms222329.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.journeyapps.barcodescanner.Util;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.SegnalazioneMessaggio;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaSegnalazioniMessaggiDatabase;
import it.uniba.dib.sms222329.database.SegnalazioneDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaSegnalazioniMessaggiAdapter;

public class SegnalazioneMessaggiFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private int idChat;

    //View Items
    private ListView listView;
    private EditText messaggio;
    private ImageButton sendMessage;

    public SegnalazioneMessaggiFragment(int idChat) {
        this.idChat = idChat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_segnalazione_messaggi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        refreshChat();

        sendMessage.setOnClickListener(view -> {
            if(!isEmptyTextbox(messaggio)){
                SegnalazioneMessaggio messaggioOggetto = null;
                if(Utility.accountLoggato == Utility.RELATORE) {
                    messaggioOggetto = new SegnalazioneMessaggio(idChat, messaggio.getText().toString(), Utility.relatoreLoggato.getIdUtente());
                } else if(Utility.accountLoggato == Utility.TESISTA){
                    messaggioOggetto = new SegnalazioneMessaggio(idChat, messaggio.getText().toString(), Utility.tesistaLoggato.getIdUtente());
                } else if(Utility.accountLoggato == Utility.CORELATORE){
                    messaggioOggetto = new SegnalazioneMessaggio(idChat, messaggio.getText().toString(), Utility.coRelatoreLoggato.getIdUtente());
                }
                SegnalazioneDatabase.MessaggioChat(db, messaggioOggetto);
                refreshChat();
            }
        });
    }

    private boolean isEmptyTextbox(EditText textbox){
        if(textbox.getText().toString().trim().compareTo("")==0){
            return true;
        }
        return false;
    }

    private void Init(){
        db = new Database(getContext());
        listView = getView().findViewById(R.id.chat_list);
        sendMessage = getView().findViewById(R.id.send_button);
        messaggio = getView().findViewById(R.id.chat_input);
    }

    private void refreshChat(){
        List<SegnalazioneMessaggio> listaSegnalazioni = ListaSegnalazioniMessaggiDatabase.ListaMessaggi(db, this.idChat);
        ListaSegnalazioniMessaggiAdapter adapterLista = null;
        if(Utility.accountLoggato == Utility.RELATORE){
            adapterLista = new ListaSegnalazioniMessaggiAdapter(getActivity().getApplicationContext(), listaSegnalazioni, Utility.relatoreLoggato.getIdUtente());
        } else if(Utility.accountLoggato == Utility.TESISTA){
            adapterLista = new ListaSegnalazioniMessaggiAdapter(getActivity().getApplicationContext(), listaSegnalazioni, Utility.tesistaLoggato.getIdUtente());
        } else if(Utility.accountLoggato == Utility.CORELATORE){
            adapterLista = new ListaSegnalazioniMessaggiAdapter(getActivity().getApplicationContext(), listaSegnalazioni, Utility.coRelatoreLoggato.getIdUtente());
        }
        listView.setAdapter(adapterLista);
    }
}