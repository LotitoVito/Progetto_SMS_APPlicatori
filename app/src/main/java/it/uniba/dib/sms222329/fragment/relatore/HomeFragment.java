package it.uniba.dib.sms222329.fragment.relatore;

import static it.uniba.dib.sms222329.fragment.calendario.CalendarUtils.daysInMonthArray;
import static it.uniba.dib.sms222329.fragment.calendario.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.ListaClassificaRicevimento;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.ListaRicevimentiAdapter;
import it.uniba.dib.sms222329.fragment.calendario.CalendarAdapter;
import it.uniba.dib.sms222329.fragment.calendario.CalendarUtils;
import it.uniba.dib.sms222329.fragment.calendario.WeekViewActivity;

public class HomeFragment extends Fragment {

    private Relatore RelatoreLoggato;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    public  HomeFragment(){}

    public HomeFragment(Relatore relatore) {
        this.RelatoreLoggato = relatore;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
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

        Database db = new Database(getActivity().getApplicationContext());
        ListView listaRicevimenti = getView().findViewById(R.id.lista_ricevimenti);
        ListaClassificaRicevimento lista = new ListaClassificaRicevimento();
        List<Ricevimento> lista2 = lista.ListaRicevimenti(db);
        ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista2);
        listaRicevimenti.setAdapter(adapter);
    }

    private void initWidgets()
    {
        calendarRecyclerView = getView().findViewById(R.id.calendarRecyclerView);
        monthYearText = getView().findViewById(R.id.mese_corrente);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, (CalendarAdapter.OnItemListener) this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(getActivity().getApplicationContext(), WeekViewActivity.class));
    }
}