package it.uniba.dib.sms222329.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.ListaMessaggiSegnalazione;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.SegnalazioneMessaggio;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.SegnalazioneDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaSegnalazioniMessaggiAdapter;

public class SegnalazioneMessaggiFragment extends Fragment {
    private int idChat;
    private Relatore relatoreLoggato;
    private Tesista tesistaLoggato;

    public SegnalazioneMessaggiFragment(int idChat, Relatore relatoreLoggato) {
        this.idChat = idChat;
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_segnalazione_messaggi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Database db = new Database(getContext());
        ListView listView = getActivity().findViewById(R.id.chat_list);
        ListaMessaggiSegnalazione lista = new ListaMessaggiSegnalazione();
        List<SegnalazioneMessaggio> listaSegnalazioni = lista.listamessaggi(db, this.idChat);
        ListaSegnalazioniMessaggiAdapter adapterLista = new ListaSegnalazioniMessaggiAdapter(getActivity().getApplicationContext(), listaSegnalazioni);
        listView.setAdapter(adapterLista);

        ImageButton sendMessage = getView().findViewById(R.id.send_button);
        EditText messaggio = getView().findViewById(R.id.chat_input);
        sendMessage.setOnClickListener(view -> {
            if(!isEmptyTextbox(messaggio)){
                SegnalazioneMessaggio messaggioOggetto = new SegnalazioneMessaggio(idChat, messaggio.getText().toString(), relatoreLoggato.getIdUtente());
                SegnalazioneDatabase.MessaggioChat(db, messaggioOggetto);
            }
        });
    }

    private boolean isEmptyTextbox(EditText textbox){
        if(textbox.getText().toString().trim().compareTo("")==0){
            return true;
        }
        return false;
    }
}