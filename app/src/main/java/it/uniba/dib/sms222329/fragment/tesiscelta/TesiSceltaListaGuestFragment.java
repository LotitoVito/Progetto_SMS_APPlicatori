package it.uniba.dib.sms222329.fragment.tesiscelta;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaTesiScelteDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaTesiScelteAdapter;
import it.uniba.dib.sms222329.fragment.tesi.TesiFilterFragment;

public class TesiSceltaListaGuestFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private ListaTesiScelteAdapter adapter;
    private String query;

    //View Items
    private ListView listaTesiScelte;
    private SearchView barraRicerca;
    private TextView filtra;

    public TesiSceltaListaGuestFragment() {
        this.query =    "SELECT ts." + Database.TESISCELTA_ID + ", ts." + Database.TESISCELTA_DATAPUBBLICAZIONE + ", ts." + Database.TESISCELTA_ABSTRACT + ", ts." + Database.TESISCELTA_DOWNLOAD + ", ts." + Database.TESISCELTA_TESIID + ", ts." + Database.TESISCELTA_CORELATOREID + ", ts." + Database.TESISCELTA_STATOCORELATORE + ", ts." + Database.TESISCELTA_TESISTAID + ", ts." + Database.TESISCELTA_CAPACITATESISTA + ", t." + Database.TESI_TITOLO +
                        " FROM " + Database.TESISCELTA + " ts, " + Database.TESI + " t " +
                        " WHERE ts." + Database.TESISCELTA_TESIID + "=t." + Database.TESI_ID + " AND "+ Database.TESISCELTA_DATAPUBBLICAZIONE + "!='';";
    }

    public TesiSceltaListaGuestFragment(String query) {
        this.query = query;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi_scelta_lista_guest, container, false);
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

        filtra.setOnClickListener(view1 -> {
            TesiFilterFragment bottomSheet = new TesiFilterFragment(true);
            bottomSheet.show(getActivity().getSupportFragmentManager(), bottomSheet.getTag());
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        listaTesiScelte = getView().findViewById(R.id.tesiList);
        barraRicerca = getView().findViewById(R.id.search_view);
        filtra = getView().findViewById(R.id.filtra);

        barraRicerca.setQueryHint("Inserisci il titolo della tesi");
    }

    /**
     * Il metodo Carica la lista delle tesiscelte
     */
    private void RefreshList(){
        List<TesiScelta> list = ListaTesiScelteDatabase.ListaTesiScelteCompletateDatabase(db, query);
        adapter = new ListaTesiScelteAdapter(getActivity().getApplicationContext(), list, getActivity().getSupportFragmentManager());
        listaTesiScelte.setAdapter(adapter);
    }
}