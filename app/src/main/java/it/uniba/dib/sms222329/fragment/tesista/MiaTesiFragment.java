package it.uniba.dib.sms222329.fragment.tesista;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaRichiesteTesiDatabase;
import it.uniba.dib.sms222329.database.RichiestaTesiDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaRichiesteAdapter;
import it.uniba.dib.sms222329.fragment.task.ListaTaskFragment;

public class MiaTesiFragment extends Fragment {

    //Variabili e Oggetti
    private Database db;
    private TesiScelta tesiScelta;

    //View Items
    private LinearLayout richieste;
    private ListView listaRichieste;
    private ScrollView tesiMia;
    private TextView relatore;
    private TextView titoloTesi;
    private TextView argomentoTesi;
    private TextView abTesi;
    private TextView tempistiche;
    private TextView dataConsegna;
    private TextView nomeCorelatore;
    private TextView emailCorelatore;
    private TextView visualizzaTask;
    private TextView ultimoCaricamento;
    private Button scarica;
    private Button carica;
    private Button salvaModifica;

    public MiaTesiFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mia_tesi, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Init();
        if(richieste.getVisibility()!=View.GONE){
            RefreshList();
        } else {
            SetTextAll();

            visualizzaTask.setOnClickListener(view -> {
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new ListaTaskFragment(tesiScelta));
            });

            scarica.setOnClickListener(view -> {
                //todo
            });

            carica.setOnClickListener(view -> {

            });

            salvaModifica.setOnClickListener(view -> {
                if(tesiScelta.ConsegnaTesiScelta(db, abTesi.getText().toString().trim())){
                    Toast.makeText(getActivity().getApplicationContext(), "Consegna avvenuta con successo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void Init() {
        db = new Database(getActivity().getApplicationContext());
        richieste = getView().findViewById(R.id.richieste);
        listaRichieste = getView().findViewById(R.id.tesistiList);
        tesiMia = getView().findViewById(R.id.mia_tesi);

        if(VerificaTesiScelta()){
            richieste.setVisibility(View.GONE);
            tesiMia.setVisibility(View.VISIBLE);
        }

        relatore = getView().findViewById(R.id.nomeRelatore);
        titoloTesi = getView().findViewById(R.id.titoloTesi);
        argomentoTesi = getView().findViewById(R.id.argomentoTesi);
        abTesi = getView().findViewById(R.id.ab_tesi);
        tempistiche = getView().findViewById(R.id.tempistiche);
        dataConsegna = getView().findViewById(R.id.dataConsegna);
        nomeCorelatore = getView().findViewById(R.id.nome_corelatore);
        emailCorelatore = getView().findViewById(R.id.email_corelatore);
        visualizzaTask = getView().findViewById(R.id.visualizza_task);
        ultimoCaricamento = getView().findViewById(R.id.ultimo_caricamento);
        scarica = getView().findViewById(R.id.scarica);
        carica = getView().findViewById(R.id.carica);
        salvaModifica = getView().findViewById(R.id.salvaModifica);
    }

    private void RefreshList(){
        List<RichiestaTesi> lista = ListaRichiesteTesiDatabase.ListaRichiesteTesiTesista(db, Utility.tesistaLoggato.getIdTesista());
        ListaRichiesteAdapter adapter = new ListaRichiesteAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
        listaRichieste.setAdapter(adapter);
    }

    private boolean VerificaTesiScelta(){
        Cursor cursor = db.RicercaDato("SELECT * FROM " + Database.TESISCELTA + " WHERE " + Database.TESISCELTA_TESISTAID + "=" + Utility.tesistaLoggato.getIdTesista() + ";");
        if(cursor.moveToNext()){
            String data = cursor.getString(cursor.getColumnIndexOrThrow(Database.TESISCELTA_DATAPUBBLICAZIONE));
            LocalDate dataPubblicazione = null;
            if(data != null){
                dataPubblicazione = LocalDate.parse(data, Utility.convertFromStringDate);
            }
            TesiScelta tesi = new TesiScelta(cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESISCELTA_TESIID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESISCELTA_ID)), cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESISCELTA_TESISTAID)), cursor.getString(cursor.getColumnIndexOrThrow(Database.TESISCELTA_CAPACITATESISTA)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESISCELTA_CORELATOREID)), cursor.getInt(cursor.getColumnIndexOrThrow(Database.TESISCELTA_STATOCORELATORE)),
                    dataPubblicazione, cursor.getString(cursor.getColumnIndexOrThrow(Database.TESISCELTA_ABSTRACT)));
            tesiScelta = tesi;
            return true;
        }
        return false;
    }

    private void SetTextAll() {
        Cursor cursorRelatore = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + " " +
                "FROM " + Database.UTENTI + " u, " + Database.RELATORE + " r, " + Database.TESI + " t, " + Database.TESISCELTA + " ts " +
                "WHERE ts." + Database.TESISCELTA_TESIID + "=t." + Database.TESI_ID + " AND t." + Database.TESI_RELATOREID + "=r." + Database.RELATORE_ID + " AND r." + Database.RELATORE_UTENTEID + "=u." + Database.UTENTI_ID + " " +
                "AND " + Database.TESISCELTA_TESIID + "=" + tesiScelta.getIdTesi() + ";");
        cursorRelatore.moveToFirst();
        relatore.setText(cursorRelatore.getString(cursorRelatore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorRelatore.getString(cursorRelatore.getColumnIndexOrThrow(Database.UTENTI_NOME)));

        Cursor cursorTesi = db.RicercaDato("SELECT t." + Database.TESI_TITOLO + ", t." + Database.TESI_ARGOMENTO + ", t." + Database.TESI_TEMPISTICHE + " " +
                "FROM " + Database.TESI + " t, " + Database.TESISCELTA +" ts " +
                "WHERE ts." + Database.TESISCELTA_TESIID + "=t." + Database.TESI_ID + " AND " + Database.TESISCELTA_TESIID + "=" + tesiScelta.getIdTesi() + ";");
        cursorTesi.moveToFirst();
        titoloTesi.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TITOLO)));
        argomentoTesi.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_ARGOMENTO)));
        tempistiche.setText(cursorTesi.getString(cursorTesi.getColumnIndexOrThrow(Database.TESI_TEMPISTICHE)));

        abTesi.setText(tesiScelta.getRiassunto());
        if(tesiScelta.getDataPubblicazione() != null){
            dataConsegna.setText(tesiScelta.getDataPubblicazione().format(Utility.showDate));
        } else {
            dataConsegna.setText("Tesi non ancora consegnata");
        }

        Cursor cursorCorelatore = db.RicercaDato("SELECT u." + Database.UTENTI_COGNOME + ", u." + Database.UTENTI_NOME + ", u." + Database.UTENTI_EMAIL + " " +
                "FROM " + Database.UTENTI + " u, " + Database.CORELATORE + " cr, " + Database.TESISCELTA + " ts " +
                "WHERE ts." + Database.TESISCELTA_CORELATOREID + "=cr." + Database.CORELATORE_ID + " AND cr." + Database.CORELATORE_UTENTEID+ "=u." + Database.UTENTI_ID + " " +
                "AND " + Database.TESISCELTA_CORELATOREID + "=" + tesiScelta.getIdCorelatore() + ";");
        if(cursorCorelatore.moveToNext()){
            nomeCorelatore.setText(cursorCorelatore.getString(cursorCorelatore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)) + " " + cursorCorelatore.getString(cursorCorelatore.getColumnIndexOrThrow(Database.UTENTI_NOME)));
            emailCorelatore.setText(cursorCorelatore.getString(cursorCorelatore.getColumnIndexOrThrow(Database.UTENTI_EMAIL)));
        } else {
            nomeCorelatore.setText("Non ancora disponibile");
            emailCorelatore.setVisibility(View.GONE);
        }
    }
}