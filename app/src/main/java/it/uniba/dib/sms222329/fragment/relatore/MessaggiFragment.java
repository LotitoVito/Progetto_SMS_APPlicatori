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
import it.uniba.dib.sms222329.classi.ListaTesi;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.SegnalazioneChat;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.ListaSegnalazioniAdapter;
import it.uniba.dib.sms222329.fragment.SegnalazioniFilterFragment;
import it.uniba.dib.sms222329.fragment.TesiFilterFragment;

public class MessaggiFragment extends Fragment {
    private Relatore relatoreLoggato;
    private String queryFiltri;

    public MessaggiFragment(Relatore relatoreLoggato) {
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

        return inflater.inflate(R.layout.fragment_messaggi_relatore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Database db = new Database(getContext());
        ListView listView = getActivity().findViewById(R.id.segnalazioniList);
        ListaClassificaSegnalazione lista = new ListaClassificaSegnalazione();
        List<SegnalazioneChat> listaSegnalazioni = lista.ListaSegnalazioni(db);
        ListaSegnalazioniAdapter adapterLista = new ListaSegnalazioniAdapter(getActivity().getApplicationContext(), listaSegnalazioni, getActivity().getSupportFragmentManager(), relatoreLoggato);
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