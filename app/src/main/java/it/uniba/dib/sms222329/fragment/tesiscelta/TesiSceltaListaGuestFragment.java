package it.uniba.dib.sms222329.fragment.tesiscelta;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaTesiScelteDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaTesiScelteAdapter;

public class TesiSceltaListaGuestFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private ListaTesiScelteAdapter adapter;

    //View Items
    private ListView listaTesiScelte;
    private SearchView barraRicerca;

    public TesiSceltaListaGuestFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guest_tesi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        RefreshList();

        barraRicerca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    listaTesiScelte.clearTextFilter();
                }
                else {
                    adapter.filter(newText);
                }
                return true;
            }
        });

    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        listaTesiScelte = getView().findViewById(R.id.tesiList);
        barraRicerca = getView().findViewById(R.id.search_view);

        barraRicerca.setQueryHint("Inserisci il titolo della tesi");
    }

    /**
     * Il metodo Carica la lista delle tesiscelte
     */
    private void RefreshList(){
        List<TesiScelta> list = ListaTesiScelteDatabase.ListaTesiScelteCompletateDatabase(db);
        adapter = new ListaTesiScelteAdapter(getActivity().getApplicationContext(), list, getActivity().getSupportFragmentManager());
        listaTesiScelte.setAdapter(adapter);
    }
}