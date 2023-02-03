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
    private Relatore RelatoreLoggato;
    private Database db;

    //View Items
    private ListView listaRicevimenti;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Button previousWeek;
    private Button nextWeek;

    public  HomeFragment(){}

    public HomeFragment(Relatore relatore) {
        this.RelatoreLoggato = relatore;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_relatore, container, false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if(selectedItemId != R.id.navigation_home) {
            bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
        }

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
        List<Ricevimento> lista = ListaRicevimentiDatabase.ListaRicevimenti(db, CalendarUtils.selectedDate.toString());
        ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista);
        listaRicevimenti.setAdapter(adapter);
    }
}