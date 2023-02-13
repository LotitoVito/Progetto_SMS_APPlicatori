package it.uniba.dib.sms222329.fragment.tesi;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaTesiDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaTesiAdapter;

public class TesiListaFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private ListaTesiAdapter adapterLista;

    //View Items
    private String queryFiltri;
    private TextView filtra;
    private FloatingActionButton addButton;
    private ListView listView;
    private SearchView barraRicerca;

    public TesiListaFragment() {
        if(Utility.accountLoggato == Utility.RELATORE){
            this.queryFiltri = "SELECT * FROM " + Database.TESI + " WHERE " + Database.TESI_RELATOREID + "=" + Utility.relatoreLoggato.getIdRelatore() + ";";
        } else {
            this.queryFiltri = "SELECT * FROM " + Database.TESI + ";";
        }
    }

    public TesiListaFragment(String queryFiltri) {
        this.queryFiltri = queryFiltri;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tesi_lista, container, false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if(selectedItemId != R.id.navigation_thesis) {
            bottomNavigationView.getMenu().findItem(R.id.navigation_thesis).setChecked(true);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedIstanceState){
        super.onViewCreated(view, savedIstanceState);

        Init();
        RefreshList();

        addButton.setOnClickListener(view1 -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TesiCreaModificaFragment());
        });

        filtra.setOnClickListener(view1 -> {
            TesiFilterFragment bottomSheet = new TesiFilterFragment(false);
            bottomSheet.show(getActivity().getSupportFragmentManager(), bottomSheet.getTag());
        });

        barraRicerca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapterLista.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapterLista.filter(s);
                }
                return true;
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getContext());
        addButton = getView().findViewById(R.id.aggiungiTesi);
        filtra = getView().findViewById(R.id.filtra);
        listView = getActivity().findViewById(R.id.tesiList);
        barraRicerca = getView().findViewById(R.id.search_view);

        barraRicerca.setQueryHint(getActivity().getApplicationContext().getResources().getString(R.string.inserisci_titolo_tesi));
        if(Utility.accountLoggato != Utility.RELATORE){
            addButton.setVisibility(View.GONE);
        }
    }

    /**
     * Ricarica la lista delle tesi in base ai filtri passati
     */
    private void RefreshList(){
        List<Tesi> listaTesi = ListaTesiDatabase.ListaTesi(queryFiltri, db);
        adapterLista = new ListaTesiAdapter(getActivity().getApplicationContext(), listaTesi, getActivity().getSupportFragmentManager());
        listView.setAdapter(adapterLista);
    }
}