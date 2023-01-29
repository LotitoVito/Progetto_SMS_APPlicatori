package it.uniba.dib.sms222329.fragment.signUp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.database.CoRelatoreDatabase;
import it.uniba.dib.sms222329.database.Database;

public class ModificaProfiloCorelatoreFragment extends Fragment {
    private Database db;
    private CoRelatore corelatore;

    public ModificaProfiloCorelatoreFragment(Database db, CoRelatore corelatoreLoggato) {
        this.db = db;
        this.corelatore = corelatoreLoggato;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modifica_profilo_corelatore, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        TextInputEditText nome = getActivity().findViewById(R.id.nome);
        TextInputEditText cognome = getActivity().findViewById(R.id.cognome);
        TextInputEditText mail = getActivity().findViewById(R.id.email);
        TextInputEditText password = getActivity().findViewById(R.id.password);
        TextInputEditText codFisc = getActivity().findViewById(R.id.codiceFiscale);
        TextInputEditText org = getActivity().findViewById(R.id.organizzazione);
        Button editButton = getActivity().findViewById(R.id.conferma);

        editButton.setOnClickListener(view -> {
            corelatore.modCoRelatore(nome.getText().toString(), cognome.getText().toString(), mail.getText().toString(),
                    password.getText().toString(), codFisc.getText().toString(), org.getText().toString());
            if (CoRelatoreDatabase.modCoRelatore(corelatore, db)){
                Toast.makeText(getActivity().getApplicationContext(),"modifica riuscita",Toast.LENGTH_SHORT).show();
            }
        });
    }
}