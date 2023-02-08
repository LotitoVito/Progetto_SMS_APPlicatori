package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import it.uniba.dib.sms222329.classi.Ricevimento;
import it.uniba.dib.sms222329.classi.RichiestaTesi;


public class Database extends SQLiteOpenHelper {

    //Tabella Utenti
    public static final String UTENTI = "utenti";
    public static final String UTENTI_ID = "id";
    public static final String UTENTI_NOME = "nome";
    public static final String UTENTI_COGNOME = "cognome";
    public static final String UTENTI_EMAIL = "email";
    public static final String UTENTI_CODICEFISCALE = "codice_fiscale";
    public static final String UTENTI_PASSWORD = "password";
    public static final String UTENTI_RUOLOID = "ruolo_id";

    //Tabella Tesista
    public static final String TESISTA = "tesista";
    public static final String TESISTA_ID = "id";
    public static final String TESISTA_MATRICOLA = "matricola";
    public static final String TESISTA_MEDIAVOTI = "media_voti";
    public static final String TESISTA_ESAMIMANCANTI = "esami_mancanti";
    public static final String TESISTA_UTENTEID = "utente_id";
    public static final String TESISTA_UNIVERSITACORSOID = "universitacorso_id";


    //Tabella Relatore
    public static final String RELATORE = "relatore";
    public static final String RELATORE_ID = "id";
    public static final String RELATORE_MATRICOLA = "matricola";
    public static final String RELATORE_UTENTEID = "utente_id";

    //Tabella CoRelatore
    public static final String CORELATORE = "coRelatore";
    public static final String CORELATORE_ID = "id";
    public static final String CORELATORE_ORGANIZZAZIONE = "organizzazione";
    public static final String CORELATORE_UTENTEID = "utente_id";

    //Tabella Universita
    public static final String UNIVERSITA = "universita";
    public static final String UNIVERSITA_ID = "id";
    public static final String UNIVERSITA_NOME = "nome";

    //Tabella CorsoStudi
    public static final String CORSOSTUDI = "corsoStudi";
    public static final String CORSOSTUDI_ID = "id";
    public static final String CORSOSTUDI_NOME = "nome";

    //Tabella UniversitaCorso
    public static final String UNIVERSITACORSO = "universitacorso";
    public static final String UNIVERSITACORSO_ID = "id";
    public static final String UNIVERSITACORSO_UNIVERSITAID = "universita_id";
    public static final String UNIVERSITACORSO_CORSOID = "corso_id";

    //Tabella Ruoli
    public static final String RUOLI = "ruoli";
    public static final String RUOLI_ID = "id";
    public static final String RUOLI_RUOLO = "ruolo";

    //Tabella CorsiRelatore
    public static final String CORSIRELATORE = "corsiRelatore";
    public static final String CORSIRELATORE_ID = "id";
    public static final String CORSIRELATORE_RELATOREID = "relatore_id";
    public static final String CORSIRELATORE_UNIVERSITACORSOID = "universitacorso_id";

    //Tabella Tesi
    public static final String TESI = "tesi";
    public static final String TESI_ID = "id";
    public static final String TESI_TITOLO = "titolo";
    public static final String TESI_ARGOMENTO = "argomento";
    public static final String TESI_TEMPISTICHE = "tempistiche";
    public static final String TESI_MEDIAVOTOMINIMA = "media_voto_minima";
    public static final String TESI_ESAMINECESSARI = "esami_necessari";
    public static final String TESI_SKILLRICHIESTE = "skill_richieste";
    public static final String TESI_STATO = "stato";
    public static final String TESI_VISUALIZZAZIONI = "visualizzazioni";
    public static final String TESI_RELATOREID = "relatore_id";

    //Tabella Richiesta
    public static final String RICHIESTA = "richiesta";
    public static final String RICHIESTA_ID = "id";
    public static final String RICHIESTA_MESSAGGIO = "messaggio";
    public static final String RICHIESTA_CAPACITASTUDENTE = "capacita_studente";
    public static final String RICHIESTA_TESIID = "tesi_id";
    public static final String RICHIESTA_TESISTAID = "tesista_id";
    public static final String RICHIESTA_ACCETTATA = "accettata";
    public static final String RICHIESTA_RISPOSTA = "risposta";

    //Tabella TesiScelta
    public static final String TESISCELTA = "tesi_scelta";
    public static final String TESISCELTA_ID = "id";
    public static final String TESISCELTA_DATAPUBBLICAZIONE = "data_pubblicazione";
    public static final String TESISCELTA_ABSTRACT = "abstract";
    public static final String TESISCELTA_DOWNLOAD = "download";
    public static final String TESISCELTA_TESIID = "tesi_id";
    public static final String TESISCELTA_CORELATOREID = "corelatore_id";
    public static final String TESISCELTA_TESISTAID = "tesista_id";
    public static final String TESISCELTA_CAPACITATESISTA = "capacita_tesista";
    public static final String TESISCELTA_STATOCORELATORE = "stato_corelatore";

    //Tabella SegnalazioneChat
    public static final String SEGNALAZIONECHAT = "segnalazioneChat";
    public static final String SEGNALAZIONECHAT_ID = "id";
    public static final String SEGNALAZIONECHAT_OGGETTO = "oggetto";
    public static final String SEGNALAZIONECHAT_TESISCELTAID = "tesi_scelta_id";

    //Tabella MessaggiSegnalazione
    public static final String MESSAGGISEGNALAZIONE = "messaggiSegnalazione";
    public static final String MESSAGGISEGNALAZIONE_ID = "id";
    public static final String MESSAGGISEGNALAZIONE_MESSAGGIO = "messaggio";
    public static final String MESSAGGISEGNALAZIONE_TIMESTAMP = "timestamp";
    public static final String MESSAGGISEGNALAZIONE_UTENTEID = "utente_id";
    public static final String MESSAGGISEGNALAZIONE_SEGNALAZIONEID = "segnalazione_id";

    //Tabella Task
    public static final String TASK = "task";
    public static final String TASK_ID = "id";
    public static final String TASK_TITOLO = "titolo";
    public static final String TASK_DESCRIZIONE = "descrizione";
    public static final String TASK_DATAINIZIO = "data_inizio";
    public static final String TASK_DATAFINE = "data_fine";
    public static final String TASK_LINKMATERIALE = "link_materiale";
    public static final String TASK_STATO = "stato";
    public static final String TASK_TESISCELTAID = "tesi_scelta_id";

    //Tabella Ricevimenti
    public static final String RICEVIMENTI = "ricevimenti";
    public static final String RICEVIMENTI_ID = "id";
    public static final String RICEVIMENTI_DATA = "data";
    public static final String RICEVIMENTI_ORARIO = "orario";
    public static final String RICEVIMENTI_TASKID = "task_id";
    public static final String RICEVIMENTI_ACCETTAZIONE = "accettazione";
    public static final String RICEVIMENTI_MESSAGGIO = "messaggio";

    public Database(@Nullable Context context) {
        super(context, "Laureapp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable;
        //Utenti
        createTable =   "CREATE TABLE " + UTENTI + " (" +
                        UTENTI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        UTENTI_NOME + " VARCHAR(255) NOT NULL," +
                        UTENTI_COGNOME + " VARCHAR(255) NOT NULL," +
                        UTENTI_EMAIL + " VARCHAR(255) NOT NULL UNIQUE," +
                        UTENTI_CODICEFISCALE + " VARCHAR(255) NOT NULL UNIQUE," +
                        UTENTI_PASSWORD + " VARCHAR(255) NOT NULL," +
                        UTENTI_RUOLOID + " INT NOT NULL," +
                        "FOREIGN KEY (" + UTENTI_RUOLOID + ") REFERENCES " + RUOLI + "(" + RUOLI_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Tesista
        createTable =   "CREATE TABLE " + TESISTA + " (" +
                        TESISTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TESISTA_MATRICOLA + " VARCHAR(255) NOT NULL," +
                        TESISTA_MEDIAVOTI + " FLOAT NOT NULL," +
                        TESISTA_ESAMIMANCANTI + " INT NOT NULL," +
                        TESISTA_UTENTEID + " INT NOT NULL UNIQUE," +
                        TESISTA_UNIVERSITACORSOID + " INT NOT NULL," +
                        "FOREIGN KEY (" + TESISTA_UTENTEID + ") REFERENCES " + UTENTI + "(" + UTENTI_ID + ") ON DELETE CASCADE," +
                        "FOREIGN KEY (" + TESISTA_UNIVERSITACORSOID + ") REFERENCES " + UNIVERSITACORSO + "(" + UNIVERSITACORSO_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Relatore
        createTable =   "CREATE TABLE " + RELATORE + " (" +
                        RELATORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        RELATORE_MATRICOLA + " VARCHAR(255) NOT NULL," +
                        RELATORE_UTENTEID + " INT NOT NULL UNIQUE," +
                        "FOREIGN KEY (" + RELATORE_UTENTEID + ") REFERENCES " + UTENTI + "(" + UTENTI_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //CoRelatore
        createTable =   "CREATE TABLE " + CORELATORE + " (" +
                        CORELATORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CORELATORE_ORGANIZZAZIONE + " VARCHAR(255) NOT NULL," +
                        CORELATORE_UTENTEID + " INT NOT NULL UNIQUE," +
                        "FOREIGN KEY (" + CORELATORE_UTENTEID + ") REFERENCES " + UTENTI + "(" + UTENTI_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Universita
        createTable =   "CREATE TABLE " + UNIVERSITA + " (" +
                        UNIVERSITA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        UNIVERSITA_NOME + " VARCHAR(255) NOT NULL UNIQUE);";
        db.execSQL(createTable);
        //CorsoStudi
        createTable =   "CREATE TABLE " + CORSOSTUDI + " (" +
                        CORSOSTUDI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CORSOSTUDI_NOME + " VARCHAR(255) NOT NULL UNIQUE);";
        db.execSQL(createTable);
        //UniversitaCorso
        createTable =   "CREATE TABLE " + UNIVERSITACORSO + " (" +
                        UNIVERSITACORSO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        UNIVERSITACORSO_UNIVERSITAID + " INT NOT NULL," +
                        UNIVERSITACORSO_CORSOID + " INT NOT NULL," +
                        "FOREIGN KEY (" + UNIVERSITACORSO_UNIVERSITAID + ") REFERENCES " + UNIVERSITA + "(" + UNIVERSITA_ID + ") ON DELETE CASCADE," +
                        "FOREIGN KEY (" + UNIVERSITACORSO_CORSOID + ") REFERENCES " + CORSOSTUDI + "(" + CORSOSTUDI_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Ruoli
        createTable =   "CREATE TABLE " + RUOLI + " (" +
                        RUOLI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        RUOLI_RUOLO + " VARCHAR(255) NOT NULL);";
        db.execSQL(createTable);
        //CorsiRelatore
        createTable =   "CREATE TABLE " + CORSIRELATORE + " (" +
                        CORSIRELATORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CORSIRELATORE_RELATOREID + " INT NOT NULL," +
                        CORSIRELATORE_UNIVERSITACORSOID + " INT NOT NULL," +
                        "FOREIGN KEY (" + CORSIRELATORE_RELATOREID + ") REFERENCES " + RELATORE + "(" + RELATORE_ID + ") ON DELETE CASCADE," +
                        "FOREIGN KEY (" + CORSIRELATORE_UNIVERSITACORSOID + ") REFERENCES " + UNIVERSITACORSO + "(" + UNIVERSITACORSO_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Tesi
        createTable =   "CREATE TABLE " + TESI + " (" +
                        TESI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TESI_TITOLO + " VARCHAR(255) NOT NULL," +
                        TESI_ARGOMENTO + " VARCHAR(255) NOT NULL," +
                        TESI_TEMPISTICHE + " INT NOT NULL," +
                        TESI_MEDIAVOTOMINIMA + " FLOAT NOT NULL," +
                        TESI_ESAMINECESSARI + " INT NOT NULL," +
                        TESI_SKILLRICHIESTE + " VARCHAR(255) NOT NULL," +
                        TESI_STATO + " BOOLEAN NOT NULL," +
                        TESI_VISUALIZZAZIONI + " INT NOT NULL," +
                        TESI_RELATOREID + " INT NOT NULL," +
                        "FOREIGN KEY (" + TESI_RELATOREID + ") REFERENCES " + RELATORE + "(" + RELATORE_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //RichiestaTesi
        createTable =   "CREATE TABLE " + RICHIESTA + " (" +
                        RICHIESTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        RICHIESTA_MESSAGGIO + " VARCHAR(255) NOT NULL," +
                        RICHIESTA_CAPACITASTUDENTE + " VARCHAR(255) NOT NULL," +
                        RICHIESTA_TESIID + " INT NOT NULL," +
                        RICHIESTA_TESISTAID + " INT NOT NULL," +
                        RICHIESTA_ACCETTATA + " INT NOT NULL," +
                        RICHIESTA_RISPOSTA + " VARCHAR(255)," +
                        "FOREIGN KEY (" + RICHIESTA_TESIID + ") REFERENCES " + TESI + "(" + TESI_ID + ") ON DELETE CASCADE," +
                        "FOREIGN KEY (" + RICHIESTA_TESISTAID + ") REFERENCES " + TESISTA + "(" + TESISTA_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //TesiScelta
        createTable =   "CREATE TABLE " + TESISCELTA + " (" +
                        TESISCELTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TESISCELTA_DATAPUBBLICAZIONE + " DATE," +
                        TESISCELTA_ABSTRACT + " VARCHAR(255)," +
                        TESISCELTA_DOWNLOAD + " VARCHAR(255)," +
                        TESISCELTA_TESIID + " INT NOT NULL," +
                        TESISCELTA_CORELATOREID + " INT," +
                        TESISCELTA_STATOCORELATORE + " INT," +
                        TESISCELTA_TESISTAID + " INT NOT NULL UNIQUE," +
                        TESISCELTA_CAPACITATESISTA + " VARCHAR(255) NOT NULL," +
                        "FOREIGN KEY (" + TESISCELTA_TESIID + ") REFERENCES " + TESI + "(" + TESI_ID + ") ON DELETE CASCADE," +
                        "FOREIGN KEY (" + TESISCELTA_CORELATOREID + ") REFERENCES " + CORELATORE + "(" + CORELATORE_ID + ")," +
                        "FOREIGN KEY (" + TESISCELTA_TESISTAID + ") REFERENCES " + TESISTA + "(" + TESISTA_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //SegnalazioneChat
        createTable =   "CREATE TABLE " + SEGNALAZIONECHAT + " (" +
                        SEGNALAZIONECHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SEGNALAZIONECHAT_OGGETTO + " VARCHAR(255) NOT NULL," +
                        SEGNALAZIONECHAT_TESISCELTAID + " INT NOT NULL," +
                        "FOREIGN KEY (" + SEGNALAZIONECHAT_TESISCELTAID + ") REFERENCES " + TESISCELTA + "(" + TESISCELTA_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //RispostaSegnalazione
        createTable =   "CREATE TABLE " + MESSAGGISEGNALAZIONE + " (" +
                        MESSAGGISEGNALAZIONE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MESSAGGISEGNALAZIONE_MESSAGGIO + " VARCHAR(255) NOT NULL," +
                        MESSAGGISEGNALAZIONE_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                        MESSAGGISEGNALAZIONE_UTENTEID + " INT NOT NULL," +
                        MESSAGGISEGNALAZIONE_SEGNALAZIONEID + " INT NOT NULL," +
                        "FOREIGN KEY (" + MESSAGGISEGNALAZIONE_UTENTEID + ") REFERENCES " + UTENTI + "(" + UTENTI_ID + ")," +
                        "FOREIGN KEY (" + MESSAGGISEGNALAZIONE_SEGNALAZIONEID + ") REFERENCES " + SEGNALAZIONECHAT + "(" + SEGNALAZIONECHAT_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Task
        createTable =   "CREATE TABLE " + TASK + " (" +
                        TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TASK_TITOLO +" VARCHAR(255) NOT NULL," +
                        TASK_DESCRIZIONE + " VARCHAR(255) NOT NULL," +
                        TASK_DATAINIZIO + " DATE NOT NULL," +
                        TASK_DATAFINE + " DATE NOT NULL," +
                        TASK_LINKMATERIALE + " VARCHAR(255)," +
                        TASK_STATO + " VARCHAR(255) NOT NULL," +
                        TASK_TESISCELTAID + " INT NOT NULL," +
                        "FOREIGN KEY (" + TASK_TESISCELTAID + ") REFERENCES " + TESISCELTA + "(" + TESISCELTA_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Ricevimenti
        createTable =   "CREATE TABLE " + RICEVIMENTI + " (" +
                        RICEVIMENTI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        RICEVIMENTI_DATA + " DATE NOT NULL," +
                        RICEVIMENTI_ORARIO + " TIME NOT NULL," +
                        RICEVIMENTI_TASKID + " INT NOT NULL," +
                        RICEVIMENTI_ACCETTAZIONE + " INT NOT NULL," +
                        RICEVIMENTI_MESSAGGIO + "  VARCHAR(255)," +
                        "FOREIGN KEY (" + RICEVIMENTI_TASKID + ") REFERENCES " + TASK + "(" + TASK_ID + ") ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Popolamento tabella ruoli
        ContentValues cvRuoli = new ContentValues();
        String[] listaRuoli = {"Tesista", "Relatore", "CoRelatore"};
        for(int i=0; i<listaRuoli.length; i++){
            cvRuoli.put(RUOLI_RUOLO, listaRuoli[i]);
            db.insert(RUOLI, null, cvRuoli);
        }
        //Popolamento tabella Universita
        ContentValues cvUniversita = new ContentValues();
        String[] listaUniversita = {"UniBa", "UniCa", "UniNa"};
        for(int i=0; i<listaUniversita.length; i++){
            cvUniversita.put(UNIVERSITA_NOME, listaUniversita[i]);
            db.insert(UNIVERSITA, null, cvUniversita);
        }
        //Popolamento tabella CorsiStudio
        ContentValues cvCorsiStudio = new ContentValues();
        String[] listaCorsiStudio = {"ITPS", "Chimica", "Matematica"};
        for(int i=0; i<listaCorsiStudio.length; i++){
            cvCorsiStudio.put(CORSOSTUDI_NOME, listaCorsiStudio[i]);
            db.insert(CORSOSTUDI, null, cvCorsiStudio);
        }
        //Popolamento tabella Universita-CorsiStudio
        PopolamentoUniCorsi(db);
        //Popolamento ricevimenti
        //Test(db);
    }

    //Viene chiamato nel caso di aggiornamento della versione del database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){}

    /**
     * Metodo usato per verificare se un dato esiste nel database in base alla query
     * @param query
     * @return  Restituisce true esistono i dati della query, altrimenti false
     */
    public boolean VerificaDatoEsistente(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        if (cursore.getCount() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodo usato per ricercare dei dati in base alla query
     * @param query
     * @return  Restituisce l'oggetto Cursor con i risultati
     */
    public Cursor RicercaDato(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        return cursore;
    }

    /**
     * Metodo usato per popolare la tabella UNIVERSITACORSO
     * @param db
     */
    private void PopolamentoUniCorsi(SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(UNIVERSITACORSO_UNIVERSITAID, 1);
        cv.put(UNIVERSITACORSO_CORSOID, 1);
        db.insert(UNIVERSITACORSO, null, cv);

        cv.put(UNIVERSITACORSO_UNIVERSITAID, 1);
        cv.put(UNIVERSITACORSO_CORSOID, 3);
        db.insert(UNIVERSITACORSO, null, cv);

        cv.put(UNIVERSITACORSO_UNIVERSITAID, 2);
        cv.put(UNIVERSITACORSO_CORSOID, 1);
        db.insert(UNIVERSITACORSO, null, cv);

        cv.put(UNIVERSITACORSO_UNIVERSITAID, 2);
        cv.put(UNIVERSITACORSO_CORSOID, 2);
        db.insert(UNIVERSITACORSO, null, cv);

        cv.put(UNIVERSITACORSO_UNIVERSITAID, 3);
        cv.put(UNIVERSITACORSO_CORSOID, 1);
        db.insert(UNIVERSITACORSO, null, cv);

        cv.put(UNIVERSITACORSO_UNIVERSITAID, 3);
        cv.put(UNIVERSITACORSO_CORSOID, 2);
        db.insert(UNIVERSITACORSO, null, cv);

        cv.put(UNIVERSITACORSO_UNIVERSITAID, 3);
        cv.put(UNIVERSITACORSO_CORSOID, 3);
        db.insert(UNIVERSITACORSO, null, cv);
    }

    private void Test(SQLiteDatabase db){
        //Segnalazione
        ContentValues cvSegnalazione = new ContentValues();
        cvSegnalazione.put(SEGNALAZIONECHAT_TESISCELTAID, 1);
        cvSegnalazione.put(SEGNALAZIONECHAT_OGGETTO, "aaaaaaaa");
        db.insert(SEGNALAZIONECHAT, null, cvSegnalazione);

        cvSegnalazione.put(SEGNALAZIONECHAT_TESISCELTAID, 2);
        cvSegnalazione.put(SEGNALAZIONECHAT_OGGETTO, "bbb");
        db.insert(SEGNALAZIONECHAT, null, cvSegnalazione);

        cvSegnalazione.put(SEGNALAZIONECHAT_TESISCELTAID, 3);
        cvSegnalazione.put(SEGNALAZIONECHAT_OGGETTO, "ccc");
        db.insert(SEGNALAZIONECHAT, null, cvSegnalazione);

        //Messaggi
        ContentValues cvMessaggi = new ContentValues();
        cvMessaggi.put(MESSAGGISEGNALAZIONE_UTENTEID, 1);
        cvMessaggi.put(MESSAGGISEGNALAZIONE_MESSAGGIO, "aaa");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_TIMESTAMP, "2023-02-03 08:47:00");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_SEGNALAZIONEID, 1);
        db.insert(MESSAGGISEGNALAZIONE, null, cvMessaggi);

        cvMessaggi.put(MESSAGGISEGNALAZIONE_UTENTEID, 2);
        cvMessaggi.put(MESSAGGISEGNALAZIONE_MESSAGGIO, "bbb");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_TIMESTAMP, "2023-02-03 08:50:20");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_SEGNALAZIONEID, 1);
        db.insert(MESSAGGISEGNALAZIONE, null, cvMessaggi);

        cvMessaggi.put(MESSAGGISEGNALAZIONE_UTENTEID, 1);
        cvMessaggi.put(MESSAGGISEGNALAZIONE_MESSAGGIO, "ccc");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_TIMESTAMP, "2023-02-03 08:48:45");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_SEGNALAZIONEID, 1);
        db.insert(MESSAGGISEGNALAZIONE, null, cvMessaggi);

        cvMessaggi.put(MESSAGGISEGNALAZIONE_UTENTEID, 1);
        cvMessaggi.put(MESSAGGISEGNALAZIONE_MESSAGGIO, "ddd");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_TIMESTAMP, "2023-02-03 12:45:23");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_SEGNALAZIONEID, 2);
        db.insert(MESSAGGISEGNALAZIONE, null, cvMessaggi);

        //Ricevimenti
        ContentValues cvRicevimenti = new ContentValues();
        cvRicevimenti.put(RICEVIMENTI_TASKID, 1);
        cvRicevimenti.put(RICEVIMENTI_DATA, "2023-02-04");
        cvRicevimenti.put(RICEVIMENTI_ORARIO, "16:50");
        cvRicevimenti.put(RICEVIMENTI_ACCETTAZIONE, Ricevimento.IN_ATTESA_RELATORE);
        cvRicevimenti.put(RICEVIMENTI_MESSAGGIO, "A");
        db.insert(RICEVIMENTI, null, cvRicevimenti);

        //Richieste
        ContentValues cvRichieste = new ContentValues();
        cvRichieste.put(RICHIESTA_TESIID, 1);
        cvRichieste.put(RICHIESTA_TESISTAID, 1);
        cvRichieste.put(RICHIESTA_MESSAGGIO, "aaaa");
        cvRichieste.put(RICHIESTA_CAPACITASTUDENTE, "A");
        cvRichieste.put(RICHIESTA_ACCETTATA, RichiestaTesi.IN_ATTESA);
        db.insert(RICHIESTA, null, cvRichieste);

        cvRichieste.put(RICHIESTA_TESIID, 1);
        cvRichieste.put(RICHIESTA_TESISTAID, 2);
        cvRichieste.put(RICHIESTA_MESSAGGIO, "bbbb");
        cvRichieste.put(RICHIESTA_CAPACITASTUDENTE, "B");
        cvRichieste.put(RICHIESTA_ACCETTATA, RichiestaTesi.IN_ATTESA);
        db.insert(RICHIESTA, null, cvRichieste);

        cvRichieste.put(RICHIESTA_TESIID, 1);
        cvRichieste.put(RICHIESTA_TESISTAID, 2);
        cvRichieste.put(RICHIESTA_MESSAGGIO, "abbbbaaa");
        cvRichieste.put(RICHIESTA_CAPACITASTUDENTE, "C");
        cvRichieste.put(RICHIESTA_ACCETTATA, RichiestaTesi.IN_ATTESA);
        db.insert(RICHIESTA, null, cvRichieste);

        //TesiScelte
        ContentValues cvTesiScelte = new ContentValues();
        cvTesiScelte.put(TESISCELTA_TESIID, 1);
        cvTesiScelte.put(TESISCELTA_TESISTAID, 1);
        cvTesiScelte.put(TESISCELTA_CORELATOREID, 1);
        db.insert(TESISCELTA, null, cvTesiScelte);

        cvTesiScelte.put(TESISCELTA_TESIID, 2);
        cvTesiScelte.put(TESISCELTA_TESISTAID, 2);
        cvTesiScelte.put(TESISCELTA_CORELATOREID, 1);
        db.insert(TESISCELTA, null, cvTesiScelte);
    }
}