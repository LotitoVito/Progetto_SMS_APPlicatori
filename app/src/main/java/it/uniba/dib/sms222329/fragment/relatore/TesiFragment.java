package it.uniba.dib.sms222329.fragment.relatore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import it.uniba.dib.sms222329.classi.ListaTesi;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.fragment.TesiFilterFragment;

public class TesiFragment extends Fragment {
    private Relatore relatoreLoggato;
    private String queryFiltri;

    public TesiFragment(){}

    public TesiFragment(Relatore relatoreLoggato) {
        this.relatoreLoggato = relatoreLoggato;
        this.queryFiltri = "SELECT * FROM " + Database.TESI + " WHERE " + Database.TESI_RELATOREID + "=" + relatoreLoggato.getIdRelatore() + ";";
    }

    public TesiFragment(Relatore relatoreLoggato, String queryFiltri) {
        this.relatoreLoggato = relatoreLoggato;
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

        // Get a reference to the button
        FloatingActionButton addButton = view.findViewById(R.id.aggiungiTesi);

        // Set up a click listener for the button
        addButton.setOnClickListener(view1 -> Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new GestioneTesiFragment(relatoreLoggato)));

        TextView filtra = view.findViewById(R.id.filtra);

        filtra.setOnClickListener(view1 -> {
            // Create a new instance of the bottom sheet fragment
            TesiFilterFragment bottomSheet = new TesiFilterFragment(relatoreLoggato);
            // Show the bottom sheet
            bottomSheet.show(getActivity().getSupportFragmentManager(), bottomSheet.getTag());
        });

        Database db = new Database(getContext());
        ListView listView = getActivity().findViewById(R.id.tesiList);
        ListaTesi lista = new ListaTesi(db);
        List<Tesi> listaTesi = lista.vincoloConQuery(queryFiltri, db);
        ListaTesiAdapter adapterLista = new ListaTesiAdapter(getActivity().getApplicationContext(), listaTesi, getActivity().getSupportFragmentManager(), relatoreLoggato);
        listView.setAdapter(adapterLista);
    }

}