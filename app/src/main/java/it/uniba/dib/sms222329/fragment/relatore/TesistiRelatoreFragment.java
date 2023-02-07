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

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaRichiesteTesiDatabase;
import it.uniba.dib.sms222329.database.ListaTesiScelteDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaRichiesteAdapter;
import it.uniba.dib.sms222329.fragment.adapter.ListaTesistiRelatoreAdapter;


public class TesistiRelatoreFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private int defaultColor;
    private int u_defaultColor;

    //View Items
    private ListView listView;
    private TextView richieste;
    private TextView tesisti;
    private View u_richieste;
    private  View u_tesisti;

    public TesistiRelatoreFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesisti_relatore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Init();

        u_richieste = getActivity().findViewById(R.id.u_richieste);
        u_tesisti = getActivity().findViewById(R.id.u_tesisti);

        defaultColor = richieste.getTextColors().getDefaultColor();
        u_defaultColor = view.getDrawingCacheBackgroundColor();

        richieste.setTextColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));
        u_richieste.setBackgroundColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));

        if(Utility.accountLoggato == Utility.RELATORE){
            CaricaListaRichiesteRelatore();
        } else {
            CaricaListaRichiesteCorelatore();
        }

        richieste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettaRichieste();
                if(Utility.accountLoggato == Utility.RELATORE){
                    CaricaListaRichiesteRelatore();
                } else {
                    CaricaListaRichiesteCorelatore();
                }
            }
        });

        tesisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettaTesisti();
                if(Utility.accountLoggato == Utility.RELATORE) {
                    CaricaListaTesistiRelatore();
                } else {
                    CaricaListaTesistiCorelatore();
                }
            }
        });

    }

    private void Init(){
        db = new Database(getActivity().getApplicationContext());
        listView = getView().findViewById(R.id.tesistiList);
        richieste = getActivity().findViewById(R.id.richieste);
        tesisti = getActivity().findViewById(R.id.tesisti);
    }

    private void CaricaListaRichiesteRelatore(){
        List<RichiestaTesi> lista = ListaRichiesteTesiDatabase.ListaRichiesteTesiRelatore(db, Utility.relatoreLoggato.getIdRelatore());
        ListaRichiesteAdapter adapter = new ListaRichiesteAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
        listView.setAdapter(adapter);
    }

    private void CaricaListaRichiesteCorelatore(){
        List<TesiScelta> lista = ListaTesiScelteDatabase.ListaRichiesteTesiCorelatore(db, Utility.coRelatoreLoggato.getIdCorelatore());
        ListaTesistiRelatoreAdapter adapter = new ListaTesistiRelatoreAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager(), true);
        listView.setAdapter(adapter);
    }

    private void CaricaListaTesistiRelatore(){
        List<TesiScelta> lista = ListaTesiScelteDatabase.ListaTesiScelteRelatoreDatabase(db, Utility.relatoreLoggato.getIdRelatore());
        ListaTesistiRelatoreAdapter adapter = new ListaTesistiRelatoreAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
        listView.setAdapter(adapter);
    }

    private void CaricaListaTesistiCorelatore(){
        List<TesiScelta> lista = ListaTesiScelteDatabase.ListaTesiScelteCorelatore(db, Utility.coRelatoreLoggato.getIdCorelatore());
        ListaTesistiRelatoreAdapter adapter = new ListaTesistiRelatoreAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
        listView.setAdapter(adapter);
    }

    private void SettaRichieste(){
        tesisti.setTextColor(defaultColor);
        richieste.setTextColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));
        u_richieste.setBackgroundColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));
        u_tesisti.setBackgroundColor(u_defaultColor);
    }

    private void SettaTesisti(){
        tesisti.setTextColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));
        richieste.setTextColor(defaultColor);
        u_tesisti.setBackgroundColor(getResources().getColor(R.color.primaryColor, getActivity().getTheme()));
        u_richieste.setBackgroundColor(u_defaultColor);
    }
}