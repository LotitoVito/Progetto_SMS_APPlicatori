package it.uniba.dib.sms222329.fragment.ricevimento;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaRicevimentiDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaRicevimentiAdapter;
import it.uniba.dib.sms222329.fragment.calendario.CalendarAdapter;

public class RicevimentiCalendarioFragment extends Fragment implements CalendarAdapter.OnItemListener {

    //Variabili e Oggetto
    private Database db;
    public static LocalDate selectedDate;

    //View Items
    private ListView listaRicevimenti;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Button previousWeek;
    private Button nextWeek;

    public RicevimentiCalendarioFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ricevimenti_calendario, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        selectedDate = LocalDate.now();
        Init();
        setWeekView();
        RefreshList();

        previousWeek.setOnClickListener(view -> {
            selectedDate = selectedDate.minusWeeks(1);
            setWeekView();
        });

        nextWeek.setOnClickListener(view -> {
            selectedDate = selectedDate.plusWeeks(1);
            setWeekView();
        });

    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init()
    {
        db = new Database(getActivity().getApplicationContext());
        previousWeek = getView().findViewById(R.id.precedente);
        nextWeek = getView().findViewById(R.id.successivo);
        listaRicevimenti = getView().findViewById(R.id.lista_ricevimenti);
        calendarRecyclerView = getView().findViewById(R.id.calendarRecyclerView);
        monthYearText = getView().findViewById(R.id.mese_corrente);
    }

    /**
     * Imposta la settimana a schermo
     */
    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    /**
     * Evento onclick della data, ricarica la lista dei ricevimenti imposta la settimana a schermo
     * @param position
     * @param date
     */
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            selectedDate = date;
            RefreshListDataSelezionata();
            setWeekView();
        }
    }

    /**
     * Ricarica la lista dei ricevimenti in base all'account loggato
     */
    private void RefreshListDataSelezionata(){
        if(Utility.accountLoggato == Utility.TESISTA){
            List<Ricevimento> lista = ListaRicevimentiDatabase.ListaRicevimentiGiornalieriTesista(db, selectedDate.toString(), Utility.tesistaLoggato.getIdTesista());
            ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listaRicevimenti.setAdapter(adapter);
        } else if(Utility.accountLoggato == Utility.RELATORE){
            List<Ricevimento> lista = ListaRicevimentiDatabase.ListaRicevimentiGiornalieriRelatore(db, selectedDate.toString(), Utility.relatoreLoggato.getIdRelatore());
            ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listaRicevimenti.setAdapter(adapter);
        } else if(Utility.accountLoggato == Utility.CORELATORE){
            List<Ricevimento> lista = ListaRicevimentiDatabase.ListaRicevimentiGiornalieriCorelatore(db, selectedDate.toString(), Utility.coRelatoreLoggato.getIdCorelatore());
            ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listaRicevimenti.setAdapter(adapter);
        }
    }

    private void RefreshList(){
        if(Utility.accountLoggato == Utility.TESISTA){
            List<Ricevimento> lista = ListaRicevimentiDatabase.ListaRicevimentiTesista(db, Utility.tesistaLoggato.getIdTesista());
            ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listaRicevimenti.setAdapter(adapter);
        } else if(Utility.accountLoggato == Utility.RELATORE){
            List<Ricevimento> lista = ListaRicevimentiDatabase.ListaRicevimentiRelatore(db, Utility.relatoreLoggato.getIdRelatore());
            ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listaRicevimenti.setAdapter(adapter);
        } else if(Utility.accountLoggato == Utility.CORELATORE){
            List<Ricevimento> lista = ListaRicevimentiDatabase.ListaRicevimentiCorelatore(db, Utility.coRelatoreLoggato.getIdCorelatore());
            ListaRicevimentiAdapter adapter = new ListaRicevimentiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
            listaRicevimenti.setAdapter(adapter);
        }
    }

    /**
     * Ricerca i giorni della settimana attuale e li inserisce in un array
     * @param selectedDate
     * @return  Restituisce l'array di giorni della settimana
     */
    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate)
    {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate))
        {
            days.add(current);
            current = current.plusDays(1);
        }
        return days;
    }

    /**
     * Setta la domenica della settimana
     * @param current
     * @return  Restituisce la giornata attuale se è entro domenica, altrimenti null
     */
    private static LocalDate sundayForDate(LocalDate current)
    {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo))
        {
            if(current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;

            current = current.minusDays(1);
        }

        return null;
    }

    /**
     * Converte la mese e anno in testo
     * @param date
     * @return  Restituisce la data convertita
     */
    public static String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }
}