package it.uniba.dib.sms222329.fragment.tesiscelta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaRichiesteTesiDatabase;
import it.uniba.dib.sms222329.database.ListaTesiScelteDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaRichiesteTesiAdapter;
import it.uniba.dib.sms222329.fragment.adapter.ListaTesiScelteAdapter;


public class TesiSceltaListaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    //View Items
    private ListView listView;
    private TabLayout tabLayout;
    public TesiSceltaListaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi_scelta_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Init();

        tabLayout = getActivity().findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch ( tab.getPosition()){
                    case 0:
                        if(Utility.accountLoggato == Utility.RELATORE){
                            CaricaListaRichiesteRelatore();
                        } else {
                            CaricaListaRichiesteCorelatore();
                        }
                        break;
                    case 1:
                        if(Utility.accountLoggato == Utility.RELATORE) {
                            CaricaListaTesistiRelatore();
                        } else {
                            CaricaListaTesistiCorelatore();
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.richieste)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tesisti)));
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init(){
        db = new Database(getActivity().getApplicationContext());
        listView = getView().findViewById(R.id.tesistiList);
    }

    /**
     * Carica la lista delle richieste di tesi del relatore loggato
     */
    private void CaricaListaRichiesteRelatore(){
        List<RichiestaTesi> lista = ListaRichiesteTesiDatabase.ListaRichiesteTesiRelatore(db, Utility.relatoreLoggato.getIdRelatore());
        ListaRichiesteTesiAdapter adapter = new ListaRichiesteTesiAdapter(getActivity(), lista, getActivity().getSupportFragmentManager());
        listView.setAdapter(adapter);
    }

    /**
     * Carica la lista delle richieste di partecipazione a tesi scelte per il corelatore loggato
     */
    private void CaricaListaRichiesteCorelatore(){
        List<TesiScelta> lista = ListaTesiScelteDatabase.ListaRichiesteTesiCorelatore(db, Utility.coRelatoreLoggato.getIdCorelatore());
        ListaTesiScelteAdapter adapter = new ListaTesiScelteAdapter(getActivity(), lista, getActivity().getSupportFragmentManager(), true);
        listView.setAdapter(adapter);
    }

    /**
     * Carica la lista delle tesi scelte accettate del relatore loggato
     */
    private void CaricaListaTesistiRelatore(){
        List<TesiScelta> lista = ListaTesiScelteDatabase.ListaTesiScelteRelatoreDatabase(db, Utility.relatoreLoggato.getIdRelatore());
        ListaTesiScelteAdapter adapter = new ListaTesiScelteAdapter(getActivity(), lista, getActivity().getSupportFragmentManager());
        listView.setAdapter(adapter);
    }

    /**
     * Carica la lista delle tesi scelte accettate del corelatore loggato
     */
    private void CaricaListaTesistiCorelatore(){
        List<TesiScelta> lista = ListaTesiScelteDatabase.ListaTesiScelteCorelatore(db, Utility.coRelatoreLoggato.getIdCorelatore());
        ListaTesiScelteAdapter adapter = new ListaTesiScelteAdapter(getActivity(), lista, getActivity().getSupportFragmentManager());
        listView.setAdapter(adapter);
    }
}