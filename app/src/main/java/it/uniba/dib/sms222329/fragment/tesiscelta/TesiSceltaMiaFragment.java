package it.uniba.dib.sms222329.fragment.tesiscelta;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.List;

import it.uniba.dib.sms222329.R;
import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.ListaRichiesteTesiDatabase;
import it.uniba.dib.sms222329.fragment.adapter.ListaRichiesteTesiAdapter;
import it.uniba.dib.sms222329.fragment.task.TaskListaFragment;

public class TesiSceltaMiaFragment extends Fragment {

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
    private TextInputEditText abTesi;
    private TextView tempistiche;
    private TextView dataConsegna;
    private TextView nomeCorelatore;
    private TextView emailCorelatore;
    private TextView visualizzaTask;
    private TextView ultimoCaricamento;
    private Button scarica;
    private Button carica;
    private Button salvaModifica;

    public TesiSceltaMiaFragment() {}

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
                Utility.replaceFragment(getActivity().getSupportFragmentManager(), R.id.container, new TaskListaFragment(tesiScelta));
            });

            scarica.setOnClickListener(view -> {
                //todo
            });

            carica.setOnClickListener(view -> {

            });

            salvaModifica.setOnClickListener(view -> {
                if(!IsEmpty(abTesi)){
                    if(tesiScelta.ConsegnaTesiScelta(db, abTesi.getText().toString().trim())){
                        Toast.makeText(getActivity().getApplicationContext(), "Consegna avvenuta con successo", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Operazione fallita", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Compila i campi obbligatori", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    /**
     * Metodo di inizializzazione delle variabili
     */
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

    /**
     * Ricarica la lista delle richieste in base al tesista loggato
     */
    private void RefreshList(){
        List<RichiestaTesi> lista = ListaRichiesteTesiDatabase.ListaRichiesteTesiTesista(db, Utility.tesistaLoggato.getIdTesista());
        ListaRichiesteTesiAdapter adapter = new ListaRichiesteTesiAdapter(getActivity().getApplicationContext(), lista, getActivity().getSupportFragmentManager());
        listaRichieste.setAdapter(adapter);
    }

    /**
     * Verifica se il tesista loggato ha già una tesi scelta, nel caso sia vero imposta la view della tesi scelta
     * @return  Restituisce true se il tesista loggato ha una tesi scelta, altrimenti false
     */
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

    /**
     * Imposta il testo per ogni elemento della view in base ai casi
     */
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

    /**
     * Metodo che verifica se i campi obbligatori sono vuoti, nel caso siano vuoti sono contrassegnati;
     * @param abTesi
     * @return  Il metodo restituisce true se almeno un campo è vuoto, restituisce false se tutti i campi non sono vuoti
     */
    private boolean IsEmpty(EditText abTesi){
        boolean risultato = false;

        if(Utility.isEmptyTextbox(abTesi)){
            risultato = true;
            abTesi.setError("Obbligatorio");
        }
        return risultato;
    }
}