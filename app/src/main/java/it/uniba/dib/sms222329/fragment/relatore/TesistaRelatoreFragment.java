package it.uniba.dib.sms222329.fragment.relatore;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiSceltaDatabase;
import it.uniba.dib.sms222329.fragment.task.CreaTaskFragment;
import it.uniba.dib.sms222329.fragment.task.ListaTaskFragment;

public class TesistaRelatoreFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private TesiScelta tesiScelta;

    //View Items
    private TextView tesista;
    private TextView titoloTesi;
    private TextView argomentoTesi;
    private TextView riassunto;
    private TextView tempistiche;
    private TextView esamiMancanti;
    private TextView capacitaRichiesta;
    private TextView capacitaEffettive;
    private TextView dataConsegna;
    private TextView corelatore;
    private TextView emailCorelatore;
    private TextInputEditText richiestaCorelatore;
    private Button aggiungiCorelatore;
    private Button rimuoviCorelatore;
    private TextView creaTask;
    private TextView mostraTask;
    private Button scaricaTesi;
    private Button caricaTesi;

    public TesistaRelatoreFragment(TesiScelta tesiScelta) {
        this.tesiScelta = tesiScelta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tesi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        SetTextAll();

        aggiungiCorelatore.setOnClickListener(view -> {
            if(db.VerificaDatoEsistente("SELECT " + Database.UTENTI_EMAIL + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_EMAIL + "='" + richiestaCorelatore.getText().toString().trim() + "';")){
                if(tesiScelta.AggiungiCorelatore(db, richiestaCorelatore.getText().toString().trim())){
                    Toast.makeText(getActivity().getApplicationContext(), "Richiesta inviata con successo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Il corelatore inserito non esiste o errore imprevisto", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Il corelatore inserito non esiste", Toast.LENGTH_SHORT).show();
            }
        });

        rimuoviCorelatore.setOnClickListener(view -> {
            if(tesiScelta.RimuoviCorelatore(db)){
                Toast.makeText(getActivity().getApplicationContext(), "Corelatore rimosso con successo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
            }
        });

        creaTask.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new CreaTaskFragment(tesiScelta));
        });

        mostraTask.setOnClickListener(view -> {
            Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ListaTaskFragment(tesiScelta));
        });

        scaricaTesi.setOnClickListener(view -> {

        });

        caricaTesi.setOnClickListener(view -> {

        });
    }

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        tesista = getView().findViewById(R.id.nomeStudente);
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomentoTesi = getView().findViewById(R.id.argomentoTesi);
        riassunto = getView().findViewById(R.id.riassunto);
        tempistiche = getView().findViewById(R.id.tempistiche);
        esamiMancanti = getView().findViewById(R.id.esamiMancanti);
        capacitaRichiesta = getView().findViewById(R.id.capacitaRichiesta);
        capacitaEffettive = getView().findViewById(R.id.capacitaEffettive);
        dataConsegna = getView().findViewById(R.id.dataConsegna);
        corelatore = getView().findViewById(R.id.corelatore);
        emailCorelatore = getView().findViewById(R.id.email_corelatore);
        richiestaCorelatore = getView().findViewById(R.id.email_co);
        aggiungiCorelatore = getView().findViewById(R.id.aggiungi_corelatore);
        rimuoviCorelatore = getView().findViewById(R.id.rimuovi_corelatore);
        creaTask = getView().findViewById(R.id.crea_task);
        mostraTask = getView().findViewById(R.id.visualizza_task);
        scaricaTesi = getView().findViewById(R.id.scarica);
        caricaTesi = getView().findViewById(R.id.carica);

        if(tesiScelta.getStatoCorelatore() == TesiScelta.ACCETTATO || tesiScelta.getStatoCorelatore() == TesiScelta.IN_ATTESA){
            aggiungiCorelatore.setVisibility(View.GONE);
            rimuoviCorelatore.setVisibility(View.VISIBLE);
            richiestaCorelatore.setVisibility(View.GONE);
        } else{
            aggiungiCorelatore.setVisibility(View.VISIBLE);
            rimuoviCorelatore.setVisibility(View.GONE);
        }
    }

    private void SetTextAll(){


        //Corelatore
        Cursor cursore = db.RicercaDato("SELECT " + Database.UTENTI_NOME + ", " + Database.UTENTI_COGNOME + ", " + Database.UTENTI_EMAIL + " FROM " + Database.CORELATORE + " c, " + Database.UTENTI + " u " +
                "WHERE u." + Database.UTENTI_ID + "=c." + Database.CORELATORE_UTENTEID + " AND c." + Database.CORELATORE_ID + "=" + tesiScelta.getIdCorelatore() + ";");
        cursore.moveToFirst();
        if(tesiScelta.getStatoCorelatore()==TesiScelta.ACCETTATO){
            corelatore.setText(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_NOME)));
            emailCorelatore.setText(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_EMAIL)));
        } else if(tesiScelta.getStatoCorelatore()==TesiScelta.IN_ATTESA) {
            corelatore.setText("In attesa di approvazione");
            emailCorelatore.setText(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_EMAIL)));
        } else {
            corelatore.setVisibility(View.GONE);
            emailCorelatore.setVisibility(View.GONE);
        }
    }
}