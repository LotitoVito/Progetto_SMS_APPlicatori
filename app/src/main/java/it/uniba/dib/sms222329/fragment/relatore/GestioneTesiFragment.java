package it.uniba.dib.sms222329.fragment.relatore;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GestioneTesiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GestioneTesiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GestioneTesiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GestioneTesiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GestioneTesiFragment newInstance(String param1, String param2) {
        GestioneTesiFragment fragment = new GestioneTesiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestione_tesi_relatore, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tesi_telatore, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        Context context = getActivity().getApplicationContext();
        Database db = new Database(context);
        TextInputEditText titolo = getView().findViewById(R.id.titoloTesi);
        TextInputEditText argomenti = getView().findViewById(R.id.argomenti_edit_text);
        TextInputEditText tempistiche = getView().findViewById(R.id.tempistiche_edit_text);
        TextInputEditText esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        TextInputEditText capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        TextInputEditText media = getView().findViewById(R.id.media_edit_text);
        SwitchMaterial statoDisponibilita = getView().findViewById(R.id.disponibilita);
        Button registra = getView().findViewById(R.id.add);

        registra.setOnClickListener(view -> {
            Tesi tesi = new Tesi(titolo.getText().toString(), argomenti.getText().toString(),
                    statoDisponibilita.isChecked(), "1", tempistiche.getText().toString(),
                    Integer.parseInt(media.getText().toString()), Integer.parseInt(esamiMancanti.getText().toString()),
                    capacitaRichiesta.getText().toString()); //Fixare idRelatore dall'activity

            if(tesi.RegistrazioneTesi(db)){
                Toast.makeText(context, "Successo", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(context, "Registrazione fallita", Toast.LENGTH_SHORT).show();
            }
        });

    }
}