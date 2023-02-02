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
import it.uniba.dib.sms222329.fragment.adapter.ListaSegnalazioniChatAdapter;
import it.uniba.dib.sms222329.fragment.SegnalazioniFilterFragment;

public class SegnalazioneChatFragment extends Fragment {
    private Relatore relatoreLoggato;
    private String queryFiltri;

    public SegnalazioneChatFragment(Relatore relatoreLoggato) {
        this.queryFiltri = "SELECT * FROM " + Database.SEGNALAZIONECHAT + ";";
        this.relatoreLoggato = relatoreLoggato;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if(selectedItemId != R.id.navigation_messages) {
            bottomNavigationView.getMenu().findItem(R.id.navigation_messages).setChecked(true);
        }

        return inflater.inflate(R.layout.fragment_segnalazione_chat_relatore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Database db = new Database(getContext());
        ListView listView = getActivity().findViewById(R.id.segnalazioniList);
        ListaClassificaSegnalazione lista = new ListaClassificaSegnalazione();
        List<SegnalazioneChat> listaSegnalazioni = lista.ListaSegnalazioni(db);
        ListaSegnalazioniChatAdapter adapterLista = new ListaSegnalazioniChatAdapter(getActivity().getApplicationContext(), listaSegnalazioni, getActivity().getSupportFragmentManager(), relatoreLoggato);
        listView.setAdapter(adapterLista);

        TextView filtra = view.findViewById(R.id.filtra);

        filtra.setOnClickListener(view1 -> {
            // Create a new instance of the bottom sheet fragment
            SegnalazioniFilterFragment bottomSheet = new SegnalazioniFilterFragment();
            // Show the bottom sheet
            bottomSheet.show(getActivity().getSupportFragmentManager(), bottomSheet.getTag());
        });
    }
}