package it.uniba.dib.sms222329.fragment.relatore;

import static it.uniba.dib.sms222329.fragment.calendario.CalendarUtils.daysInMonthArray;
import static it.uniba.dib.sms222329.fragment.calendario.CalendarUtils.daysInWeekArray;
import static it.uniba.dib.sms222329.fragment.calendario.CalendarUtils.monthYearFromDate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.activities.LoggedActivity;
import it.uniba.dib.sms222329.classi.ListaClassificaRicevimento;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaRicevimentiDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaRicevimentiAdapter;
import it.uniba.dib.sms222329.fragment.calendario.CalendarAdapter;
import it.uniba.dib.sms222329.fragment.calendario.CalendarUtils;

public class HomeFragment extends Fragment implements CalendarAdapter.OnItemListener {

    //Variabili e Oggetto
    private Database db;

    //View Items
    private ListView listaRicevimenti;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Button previousWeek;
    private Button nextWeek;

    public  HomeFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_relatore, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        CalendarUtils.selectedDate = LocalDate.now();
        Init();
        setWeekView();
        RefreshList();

        previousWeek.setOnClickListener(view -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
            setWeekView();
        });

        nextWeek.setOnClickListener(view -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
            setWeekView();
        });

    }

    private void Init()
    {
        db = new Database(getActivity().getApplicationContext());
        previousWeek = getView().findViewById(R.id.precedente);
        nextWeek = getView().findViewById(R.id.successivo);
        listaRicevimenti = getView().findViewById(R.id.lista_ricevimenti);
        calendarRecyclerView = getView().findViewById(R.id.calendarRecyclerView);
        monthYearText = getView().findViewById(R.id.mese_corrente);
    }


    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            RefreshList();
            setWeekView();
        }
    }

    private void RefreshList(){
        if(Utility.accountLoggato == Utility.TESISTA){
            List<Ricevimento> lista = ListaRicevimentiDatabase.ListaRicevimentiTesista(db, CalendarUtils.selectedDate.toString(), Utility.tesistaLoggato.getIdTesista());
            ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listaRicevimenti.setAdapter(adapter);
        } else if(Utility.accountLoggato == Utility.RELATORE){
            List<Ricevimento> lista = ListaRicevimentiDatabase.ListaRicevimentiRelatore(db, CalendarUtils.selectedDate.toString(), Utility.relatoreLoggato.getIdRelatore());
            ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listaRicevimenti.setAdapter(adapter);
        } else if(Utility.accountLoggato == Utility.CORELATORE){
            List<Ricevimento> lista = ListaRicevimentiDatabase.ListaRicevimentiCorelatore(db, CalendarUtils.selectedDate.toString(), Utility.coRelatoreLoggato.getIdCorelatore());
            ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listaRicevimenti.setAdapter(adapter);
        }
    }
}