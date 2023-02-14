package it.uniba.dib.sms222329.fragment.richiestatesi;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private TextView capacitaRichieste;
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
            if (!IsEmpty(messaggio, capacitaTesista)) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.conferma)
                        .setMessage(R.string.richiesta_tesi_conferma)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RichiestaTesi();
                            }
                        })
                        .setNegativeButton(R.string.indietro, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.campi_vuoti_errore, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomentoTesi = getView().findViewById(R.id.argomentoTesi);
        relatore = getView().findViewById(R.id.relatore_nome);
        capacitaRichieste = getView().findViewById(R.id.capacita_richieste);
        messaggio = getView().findViewById(R.id.messaggio);
        capacitaTesista = getView().findViewById(R.id.skill_richieste);
        invia = getView().findViewById(R.id.invia);
    }
    /**
     * Imposta il testo per ogni elemento della view
     */
    private void SetTextAll(){
        titoloTesi.setText(tesi.getTitolo());
        argomentoTesi.setText(tesi.getArgomenti());
        capacitaRichieste.setText(tesi.getCapacitaRichieste());
        Cursor cursor = db.RicercaDato("SELECT " + Database.UTENTI_NOME + ", " + Database.UTENTI_COGNOME + " FROM " + Database.UTENTI + " u, " + Database.RELATORE + " r " +
                "WHERE u." + Database.UTENTI_ID + "=r." + Database.RELATORE_UTENTEID + " AND r." + Database.RELATORE_ID + "=" + tesi.getIdRelatore() + ";" );
        cursor.moveToFirst();
        relatore.setText(getActivity().getApplicationContext().getResources().getString(R.string.relatore) + ": " + cursor.getString(cursor.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursor.getString(cursor.getColumnIndexOrThrow(Database.UTENTI_NOME)));
    }

    /**
     * Metodo che verifica se i campi obbligatori sono vuoti, nel caso siano vuoti sono contrassegnati;
     * @param messaggio
     * @param capacitaTesista
     * @return  Il metodo restituisce true se almeno un campo è vuoto, restituisce false se tutti i campi non sono vuoti
     */
    private boolean IsEmpty(TextInputEditText messaggio, TextInputEditText capacitaTesista) {
        boolean risultato = false;

        if(Utility.isEmptyTextbox(messaggio)){
            risultato = true;
            messaggio.setError(getActivity().getApplicationContext().getResources().getString(R.string.campo_obbligatorio));
        }
        if(Utility.isEmptyTextbox(capacitaTesista)){
            risultato = true;
            capacitaTesista.setError(getActivity().getApplicationContext().getResources().getString(R.string.campo_obbligatorio));
        }

        return risultato;
    }

    /**
     * Metodo di richiesta della tesi da parte del tesista
     */
    private void RichiestaTesi(){
        RichiestaTesi richiesta = new RichiestaTesi(messaggio.getText().toString().trim(), capacitaTesista.getText().toString().trim(),
                tesi.getIdTesi(), Utility.tesistaLoggato.getIdTesista());
        if(RichiestaTesiDatabase.RichiestaTesi(richiesta, db)){
            Toast.makeText(getActivity().getApplicationContext(), R.string.richiesta_tesi_sucesso, Toast.LENGTH_SHORT).show();
            Utility.goBack(getActivity());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.operazione_fallita, Toast.LENGTH_SHORT).show();
        }
    }
}