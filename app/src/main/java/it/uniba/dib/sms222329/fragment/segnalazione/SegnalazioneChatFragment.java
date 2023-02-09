package it.uniba.dib.sms222329.fragment.segnalazione;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.SegnalazioneChat;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaSegnalazioniChatDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaSegnalazioniChatAdapter;

public class SegnalazioneChatFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;

    //View Items
    private ListView listView;
    private TextView filtra;

    public SegnalazioneChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_segnalazione_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Init();
        RefreshList();

        filtra.setOnClickListener(view1 -> {
            SegnalazioneFilterFragment bottomSheet = new SegnalazioneFilterFragment();
            bottomSheet.show(getActivity().getSupportFragmentManager(), bottomSheet.getTag());
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
         db = new Database(getContext());
         listView = getActivity().findViewById(R.id.segnalazioniList);
         filtra = getView().findViewById(R.id.filtra);
    }

    /**
     * Ricatica la lista delle chat in base all'account loggato
     */
    private void RefreshList(){
        if(Utility.accountLoggato == Utility.TESISTA){
            List<SegnalazioneChat> lista = ListaSegnalazioniChatDatabase.ListaSegnalazioniChat(db, Utility.tesistaLoggato.getIdUtente());
            ListaSegnalazioniChatAdapter adapterLista = new ListaSegnalazioniChatAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listView.setAdapter(adapterLista);
        } else  if(Utility.accountLoggato == Utility.RELATORE){
            List<SegnalazioneChat> lista = ListaSegnalazioniChatDatabase.ListaSegnalazioniChatRelatore(db, Utility.relatoreLoggato.getIdUtente(), Utility.relatoreLoggato.getIdRelatore());
            ListaSegnalazioniChatAdapter adapterLista = new ListaSegnalazioniChatAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listView.setAdapter(adapterLista);
        } else  if(Utility.accountLoggato == Utility.CORELATORE){
            List<SegnalazioneChat> lista = ListaSegnalazioniChatDatabase.ListaSegnalazioniChat(db, Utility.coRelatoreLoggato.getIdUtente());
            ListaSegnalazioniChatAdapter adapterLista = new ListaSegnalazioniChatAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listView.setAdapter(adapterLista);
        }

    }
}