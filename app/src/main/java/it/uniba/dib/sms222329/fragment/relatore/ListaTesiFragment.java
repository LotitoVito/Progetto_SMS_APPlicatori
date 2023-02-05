package it.uniba.dib.sms222329.fragment.relatore;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaTesiDatabase;
import it.uniba.dib.sms222329.fragment.TesiFilterFragment;
import it.uniba.dib.sms222329.fragment.adapter.ListaTesiAdapter;

public class ListaTesiFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private ListaTesiAdapter adapterLista;

    //View Items
    private String queryFiltri;
    private TextView filtra;
    private FloatingActionButton addButton;
    private ListView listView;
    private SearchView barraRicerca;

    public ListaTesiFragment() {
        this.queryFiltri = "SELECT * FROM " + Database.TESI + " WHERE " + Database.TESI_RELATOREID + "=" + Utility.relatoreLoggato.getIdRelatore() + ";";
    }

    public ListaTesiFragment(String queryFiltri) {
        this.queryFiltri = queryFiltri;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tesi_relatore, container, false);

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
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new CreaModificaTesiFragment());
        });

        filtra.setOnClickListener(view1 -> {
            TesiFilterFragment bottomSheet = new TesiFilterFragment();
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

    private void Init() {
        db = new Database(getContext());
        addButton = getView().findViewById(R.id.aggiungiTesi);
        filtra = getView().findViewById(R.id.filtra);
        listView = getActivity().findViewById(R.id.tesiList);
        barraRicerca = getView().findViewById(R.id.search_view);
        barraRicerca.setQueryHint("Inserisci il titolo della tesi");
    }

    private void RefreshList(){
        List<Tesi> listaTesi = ListaTesiDatabase.ListaTesi(queryFiltri, db);
        adapterLista = new ListaTesiAdapter(getActivity().getApplicationContext(), listaTesi, getActivity().getSupportFragmentManager());
        listView.setAdapter(adapterLista);
    }
}