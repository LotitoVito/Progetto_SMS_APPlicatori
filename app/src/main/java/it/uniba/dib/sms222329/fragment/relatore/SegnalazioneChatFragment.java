package it.uniba.dib.sms222329.fragment.relatore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.ListaClassificaSegnalazione;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.SegnalazioneChat;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaSegnalazioniChatDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaSegnalazioniChatAdapter;
import it.uniba.dib.sms222329.fragment.SegnalazioniFilterFragment;

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

        return inflater.inflate(R.layout.fragment_segnalazione_chat_relatore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Init();
        RefreshList();

        filtra.setOnClickListener(view1 -> {
            SegnalazioniFilterFragment bottomSheet = new SegnalazioniFilterFragment();
            bottomSheet.show(getActivity().getSupportFragmentManager(), bottomSheet.getTag());
        });
    }

    private void Init() {
         db = new Database(getContext());
         listView = getActivity().findViewById(R.id.segnalazioniList);
         filtra = getView().findViewById(R.id.filtra);
    }

    private void RefreshList(){
        List<SegnalazioneChat> lista = ListaSegnalazioniChatDatabase.ListaSegnalazioniChat(db);
        ListaSegnalazioniChatAdapter adapterLista = new ListaSegnalazioniChatAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
        listView.setAdapter(adapterLista);
    }
}