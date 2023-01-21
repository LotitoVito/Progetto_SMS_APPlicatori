package it.uniba.dib.sms222329.fragment.signUp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.Database;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificaProfiloCorelatoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificaProfiloCorelatoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Database db;

    public ModificaProfiloCorelatoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificaProfiloCorelatoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificaProfiloCorelatoreFragment newInstance(String param1, String param2) {
        ModificaProfiloCorelatoreFragment fragment = new ModificaProfiloCorelatoreFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modifica_profilo_corelatore, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        View registerButton = getActivity().findViewById(R.id.button);
        EditText nome = getActivity().findViewById(R.id.nome);
        EditText cognome = getActivity().findViewById(R.id.cognome);
        EditText mail = getActivity().findViewById(R.id.email);
        EditText password = getActivity().findViewById(R.id.password);
        EditText codFisc = getActivity().findViewById(R.id.codiceFiscale);
        EditText org = getActivity().findViewById(R.id.organizzazione);

        registerButton.setOnClickListener(view -> {
            CoRelatore coRelatore=new CoRelatore();
            if (coRelatore.modCoRelatore(db, nome.getText().toString(), cognome.getText().toString(),
                                         mail.getText().toString(), password.getText().toString(),
                                         codFisc.getText().toString(), org.getText().toString())){
                Toast.makeText(getActivity().getApplicationContext(),"modifica riuscita",Toast.LENGTH_SHORT).show();
            }
        });
    }
}