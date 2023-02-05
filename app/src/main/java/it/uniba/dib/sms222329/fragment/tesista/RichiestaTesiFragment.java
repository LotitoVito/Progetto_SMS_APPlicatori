package it.uniba.dib.sms222329.fragment.tesista;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RichiestaTesiDatabase;


public class RichiestaTesiFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private Tesi tesi;

    //View Items
    private TextView titoloTesi;
    private TextView argomentoTesi;
    private TextView tempistiche;
    private TextView relatore;
    private TextInputEditText messaggio;
    private TextInputEditText capacitaTesista;
    private Button invia;

    public RichiestaTesiFragment(Tesi tesi) {
        this.tesi = tesi;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_richiesta_tesi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetTextAll();

        invia.setOnClickListener(view -> {
            RichiestaTesi richiesta = new RichiestaTesi(messaggio.getText().toString().trim(), capacitaTesista.getText().toString().trim(),
                    tesi.getIdTesi(), Utility.tesistaLoggato.getIdTesista());
            if(RichiestaTesiDatabase.RichiestaTesi(richiesta, db)){
                Toast.makeText(getActivity().getApplicationContext(), "Richiesta inviata con successo", Toast.LENGTH_SHORT).show();
                Utility.closeFragment(getActivity());
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomentoTesi = getView().findViewById(R.id.argomentoTesi);
        tempistiche = getView().findViewById(R.id.tempistiche);
        relatore = getView().findViewById(R.id.nome_relatore);
        messaggio = getView().findViewById(R.id.messaggio);
        capacitaTesista = getView().findViewById(R.id.skill_richieste);
        invia = getView().findViewById(R.id.invia);
    }

    private void SetTextAll(){
        titoloTesi.setText(tesi.getTitolo());
        argomentoTesi.setText(tesi.getArgomenti());
        tempistiche.setText("Mesi: " + tesi.getTempistiche());
        Cursor cursor = db.RicercaDato("SELECT " + Database.UTENTI_NOME + ", " + Database.UTENTI_COGNOME + " FROM " + Database.UTENTI + " u, " + Database.RELATORE + " r " +
                "WHERE u." + Database.UTENTI_ID + "=r." + Database.RELATORE_UTENTEID + " AND r." + Database.RELATORE_ID + "=" + tesi.getIdRelatore() + ";" );
        cursor.moveToFirst();
        relatore.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursor.getString(cursor.getColumnIndexOrThrow(Database.UTENTI_NOME)));
    }
}