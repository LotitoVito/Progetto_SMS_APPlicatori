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
    public static final String TESI_UNIVERSITACORSOID = "universita_corso_id";

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
                        TESI_UNIVERSITACORSOID + " INT NOT NULL," +
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
        //Popolamento di base
        Popolamento(db);
    }

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

    private void Popolamento(SQLiteDatabase db){
        //Corelatore
        ContentValues cvCorelatore = new ContentValues();

        cvCorelatore.put(CORELATORE_ORGANIZZAZIONE, "Exprivia");
        cvCorelatore.put(CORELATORE_UTENTEID, 6);
        db.insert(CORELATORE, null, cvCorelatore);

        //CorsiRelatore
        ContentValues cvCorsiRelatore = new ContentValues();

        cvCorsiRelatore.put(CORSIRELATORE_RELATOREID, 1);
        cvCorsiRelatore.put(CORSIRELATORE_UNIVERSITACORSOID, 3);
        db.insert(CORSIRELATORE, null, cvCorsiRelatore);

        cvCorsiRelatore.put(CORSIRELATORE_RELATOREID, 2);
        cvCorsiRelatore.put(CORSIRELATORE_UNIVERSITACORSOID, 1);
        db.insert(CORSIRELATORE, null, cvCorsiRelatore);

        cvCorsiRelatore.put(CORSIRELATORE_RELATOREID, 2);
        cvCorsiRelatore.put(CORSIRELATORE_UNIVERSITACORSOID, 2);
        db.insert(CORSIRELATORE, null, cvCorsiRelatore);

        cvCorsiRelatore.put(CORSIRELATORE_RELATOREID, 3);
        cvCorsiRelatore.put(CORSIRELATORE_UNIVERSITACORSOID, 6);
        db.insert(CORSIRELATORE, null, cvCorsiRelatore);

        cvCorsiRelatore.put(CORSIRELATORE_RELATOREID, 3);
        cvCorsiRelatore.put(CORSIRELATORE_UNIVERSITACORSOID, 7);
        db.insert(CORSIRELATORE, null, cvCorsiRelatore);

        //CorsoStudi
        ContentValues cvCorsi = new ContentValues();

        cvCorsi.put(CORSOSTUDI_NOME, "ITPS");
        db.insert(CORSOSTUDI, null, cvCorsi);

        cvCorsi.put(CORSOSTUDI_NOME, "Chimica");
        db.insert(CORSOSTUDI, null, cvCorsi);

        cvCorsi.put(CORSOSTUDI_NOME, "Matematica");
        db.insert(CORSOSTUDI, null, cvCorsi);

        //SegnalazioneMessaggi
        ContentValues cvMessaggi = new ContentValues();

        cvMessaggi.put(MESSAGGISEGNALAZIONE_MESSAGGIO, "Salve prof, vorrei chiederle quali sistemi la tesi permettera' di analizzare");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_TIMESTAMP, "2023-02-13 17:09:59");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_UTENTEID, 1);
        cvMessaggi.put(MESSAGGISEGNALAZIONE_SEGNALAZIONEID, 1);
        db.insert(MESSAGGISEGNALAZIONE, null, cvMessaggi);

        cvMessaggi.put(MESSAGGISEGNALAZIONE_MESSAGGIO, "Salve prof, la contatto per sapere se la tesi tornera' disponibile o se esistono tesi simili");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_TIMESTAMP, "2023-02-13 17:23:07");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_UTENTEID, 3);
        cvMessaggi.put(MESSAGGISEGNALAZIONE_SEGNALAZIONEID, 2);
        db.insert(MESSAGGISEGNALAZIONE, null, cvMessaggi);

        cvMessaggi.put(MESSAGGISEGNALAZIONE_MESSAGGIO, "Gentile proof, sono Palladino Mario da Exprivia, ci piacerebbe collaborare per quest tesi se ce ne fosse occasione");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_TIMESTAMP, "2023-02-13 18:08:16");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_UTENTEID, 6);
        cvMessaggi.put(MESSAGGISEGNALAZIONE_SEGNALAZIONEID, 3);
        db.insert(MESSAGGISEGNALAZIONE, null, cvMessaggi);

        cvMessaggi.put(MESSAGGISEGNALAZIONE_MESSAGGIO, "Signor Palladino le Mando la richiesta di collaborazione");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_TIMESTAMP, "2023-02-13 18:08:51");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_UTENTEID, 5);
        cvMessaggi.put(MESSAGGISEGNALAZIONE_SEGNALAZIONEID, 3);
        db.insert(MESSAGGISEGNALAZIONE, null, cvMessaggi);

        cvMessaggi.put(MESSAGGISEGNALAZIONE_MESSAGGIO, "Gentile alunno, I sistemi da analizzare sono a scelta");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_TIMESTAMP, "2023-02-13 18:09:16");
        cvMessaggi.put(MESSAGGISEGNALAZIONE_UTENTEID, 5);
        cvMessaggi.put(MESSAGGISEGNALAZIONE_SEGNALAZIONEID, 1);
        db.insert(MESSAGGISEGNALAZIONE, null, cvMessaggi);

        //Relatore
        ContentValues cvRelatore = new ContentValues();

        cvRelatore.put(RELATORE_MATRICOLA, 789569);
        cvRelatore.put(RELATORE_UTENTEID, 4);
        db.insert(RELATORE, null, cvRelatore);

        cvRelatore.put(RELATORE_MATRICOLA, 745256);
        cvRelatore.put(RELATORE_UTENTEID, 5);
        db.insert(RELATORE, null, cvRelatore);

        cvRelatore.put(RELATORE_MATRICOLA, 256554);
        cvRelatore.put(RELATORE_UTENTEID, 7);
        db.insert(RELATORE, null, cvRelatore);

        //Ricevimenti
        ContentValues cvRicevimenti = new ContentValues();

        cvRicevimenti.put(RICEVIMENTI_DATA, "2023-02-20");
        cvRicevimenti.put(RICEVIMENTI_ORARIO, "16:10");
        cvRicevimenti.put(RICEVIMENTI_TASKID, 2);
        cvRicevimenti.put(RICEVIMENTI_ACCETTAZIONE, 3);
        cvRicevimenti.put(RICEVIMENTI_MESSAGGIO, "Domande sul materiale di studio");
        db.insert(RICEVIMENTI, null, cvRicevimenti);

        cvRicevimenti.put(RICEVIMENTI_DATA, "2023-02-14");
        cvRicevimenti.put(RICEVIMENTI_ORARIO, "17:25");
        cvRicevimenti.put(RICEVIMENTI_TASKID, 5);
        cvRicevimenti.put(RICEVIMENTI_ACCETTAZIONE, 1);
        cvRicevimenti.put(RICEVIMENTI_MESSAGGIO, "Discussione sulla verifica delle ipotesi");
        db.insert(RICEVIMENTI, null, cvRicevimenti);

        //RichiestaTesi
        ContentValues cvRichiesta = new ContentValues();

        cvRichiesta.put(RICHIESTA_MESSAGGIO, "Salve prof, vorrei far richiesta per la tesi");
        cvRichiesta.put(RICHIESTA_CAPACITASTUDENTE, "Ho conoscenze avanzate di cyber security");
        cvRichiesta.put(RICHIESTA_TESIID, 1);
        cvRichiesta.put(RICHIESTA_TESISTAID, 1);
        cvRichiesta.put(RICHIESTA_ACCETTATA, 2);
        cvRichiesta.put(RICHIESTA_RISPOSTA, (String) null);
        db.insert(RICHIESTA, null, cvRichiesta);

        cvRichiesta.put(RICHIESTA_MESSAGGIO, "Vorrei far richiesta per la tesi");
        cvRichiesta.put(RICHIESTA_CAPACITASTUDENTE, "Ho conoscenze avanzate di chimica verde");
        cvRichiesta.put(RICHIESTA_TESIID, 4);
        cvRichiesta.put(RICHIESTA_TESISTAID, 2);
        cvRichiesta.put(RICHIESTA_ACCETTATA, 2);
        cvRichiesta.put(RICHIESTA_RISPOSTA, "");
        db.insert(RICHIESTA, null, cvRichiesta);

        //Ruoli
        ContentValues cvRuoli = new ContentValues();

        cvRuoli.put(RUOLI_RUOLO, "Tesista");
        db.insert(RUOLI, null, cvRuoli);

        cvRuoli.put(RUOLI_RUOLO, "Relatore");
        db.insert(RUOLI, null, cvRuoli);

        cvRuoli.put(RUOLI_RUOLO, "CoRelatore");
        db.insert(RUOLI, null, cvRuoli);

        //SegnalazioneChat
        ContentValues cvChat = new ContentValues();

        cvChat.put(SEGNALAZIONECHAT_OGGETTO, "Domanda sistemi da analizzare");
        cvChat.put(SEGNALAZIONECHAT_TESISCELTAID, 1);
        db.insert(SEGNALAZIONECHAT, null, cvChat);

        cvChat.put(SEGNALAZIONECHAT_OGGETTO, "Domanda disponibilita");
        cvChat.put(SEGNALAZIONECHAT_TESISCELTAID, 3);
        db.insert(SEGNALAZIONECHAT, null, cvChat);

        cvChat.put(SEGNALAZIONECHAT_OGGETTO, "Richiesta collaborazione");
        cvChat.put(SEGNALAZIONECHAT_TESISCELTAID, 1);
        db.insert(SEGNALAZIONECHAT, null, cvChat);

        //Task
        ContentValues cvTask = new ContentValues();

        cvTask.put(TASK_TITOLO, "Recupero materiale di studio");
        cvTask.put(TASK_DESCRIZIONE, "Recuperare i sistemi piu' avanzati di sicurezza informatica a scelta");
        cvTask.put(TASK_DATAINIZIO, "2023-02-13");
        cvTask.put(TASK_DATAFINE, "2023-02-15");
        cvTask.put(TASK_LINKMATERIALE, "-NOAui7PDwAynTJk7kVY");
        cvTask.put(TASK_STATO, 4);
        cvTask.put(TASK_TESISCELTAID, 1);
        db.insert(TASK, null, cvTask);

        cvTask.put(TASK_TITOLO, "Studio del materiale");
        cvTask.put(TASK_DESCRIZIONE, "Studiare il material precedentemente recuperato");
        cvTask.put(TASK_DATAINIZIO, "2023-02-13");
        cvTask.put(TASK_DATAFINE, "2023-02-22");
        cvTask.put(TASK_STATO, 3);
        cvTask.put(TASK_TESISCELTAID, 1);
        db.insert(TASK, null, cvTask);

        cvTask.put(TASK_TITOLO, "Studio preliminare");
        cvTask.put(TASK_DESCRIZIONE, "Si studino i problemi che causano l'inquinamento da parte delle industrie");
        cvTask.put(TASK_DATAINIZIO, "2023-02-13");
        cvTask.put(TASK_DATAFINE, "2023-01-25");
        cvTask.put(TASK_STATO, 4);
        cvTask.put(TASK_TESISCELTAID, 2);
        db.insert(TASK, null, cvTask);

        cvTask.put(TASK_TITOLO, "Ipotesi tecniche");
        cvTask.put(TASK_DESCRIZIONE, "Si ipotizzino alcune tecniche che possano rimediare ai problemi trovati precedentemente");
        cvTask.put(TASK_DATAINIZIO, "2023-02-13");
        cvTask.put(TASK_DATAFINE, "2023-02-01");
        cvTask.put(TASK_LINKMATERIALE, "-NOAwqhbbRBVWupN3eGO");
        cvTask.put(TASK_STATO, 0);
        cvTask.put(TASK_TESISCELTAID, 2);
        db.insert(TASK, null, cvTask);

        cvTask.put(TASK_TITOLO, "Verifica ipotesi");
        cvTask.put(TASK_DESCRIZIONE, "Si verifichino le ipotesi pensate precedentemente");
        cvTask.put(TASK_DATAINIZIO, "2023-02-13");
        cvTask.put(TASK_DATAFINE, "2023-02-07");
        cvTask.put(TASK_STATO, 4);
        cvTask.put(TASK_TESISCELTAID, 2);
        db.insert(TASK, null, cvTask);

        cvTask.put(TASK_TITOLO, "Conclusioni");
        cvTask.put(TASK_DESCRIZIONE, "Si scrivano le conclusioni sullo studio effettuato");
        cvTask.put(TASK_DATAINIZIO, "2023-02-13");
        cvTask.put(TASK_DATAFINE, "2023-02-15");
        cvTask.put(TASK_STATO, 4);
        cvTask.put(TASK_TESISCELTAID, 2);
        db.insert(TASK, null, cvTask);

        //Tesi
        ContentValues cvTesi = new ContentValues();

        cvTesi.put(TESI_TITOLO, "Analisi della sicurezza informatica per la protezione dei dati sensibili");
        cvTesi.put(TESI_ARGOMENTO, "La tesi prevede l'analisi della sicurezza di sistemi informativi moderni per la protezione dei dati sensibili");
        cvTesi.put(TESI_TEMPISTICHE, 4);
        cvTesi.put(TESI_MEDIAVOTOMINIMA, 24.0);
        cvTesi.put(TESI_ESAMINECESSARI, 5);
        cvTesi.put(TESI_SKILLRICHIESTE, "Conoscenze base sulla cyber security");
        cvTesi.put(TESI_STATO, 1);
        cvTesi.put(TESI_VISUALIZZAZIONI, 5);
        cvTesi.put(TESI_RELATOREID, 2);
        cvTesi.put(TESI_UNIVERSITACORSOID, 1);
        db.insert(TESI, null, cvTesi);

        cvTesi.put(TESI_TITOLO, "Studio della teoria dei numeri e applicazioni nella crittografia");
        cvTesi.put(TESI_ARGOMENTO, "La tesi approfondira' le tecniche della crittografia");
        cvTesi.put(TESI_TEMPISTICHE, 3);
        cvTesi.put(TESI_MEDIAVOTOMINIMA, 26.0);
        cvTesi.put(TESI_ESAMINECESSARI, 4);
        cvTesi.put(TESI_SKILLRICHIESTE, "Il tesista deve avere delle conosenze matematiche di livello medioIl tesista deve avere delle conosenze matematiche di livello medio");
        cvTesi.put(TESI_STATO, 1);
        cvTesi.put(TESI_VISUALIZZAZIONI, 4);
        cvTesi.put(TESI_RELATOREID, 2);
        cvTesi.put(TESI_UNIVERSITACORSOID, 2);
        db.insert(TESI, null, cvTesi);

        cvTesi.put(TESI_TITOLO, "Studio delle tecniche di compressione dei dati per migliorare la velocita'");
        cvTesi.put(TESI_ARGOMENTO, "La tesi permettera' di studiare nel dettaglio le tecniche gia' esistenti e di migliorarle");
        cvTesi.put(TESI_TEMPISTICHE, 3);
        cvTesi.put(TESI_MEDIAVOTOMINIMA, 25.0);
        cvTesi.put(TESI_ESAMINECESSARI, 2);
        cvTesi.put(TESI_SKILLRICHIESTE, "Conoscenze base della compressione dei dati");
        cvTesi.put(TESI_STATO, 0);
        cvTesi.put(TESI_VISUALIZZAZIONI, 7);
        cvTesi.put(TESI_RELATOREID, 1);
        cvTesi.put(TESI_UNIVERSITACORSOID, 3);
        db.insert(TESI, null, cvTesi);

        cvTesi.put(TESI_TITOLO, "Chimica verde: analisi delle strategie per ridurre l'impatto ambientale delle industrie chimiche");
        cvTesi.put(TESI_ARGOMENTO, "La tesi verte sullo studio di nuove tecniche adatte all ridurre l'inquinamento provocato dalle industries chimiche");
        cvTesi.put(TESI_TEMPISTICHE, 6);
        cvTesi.put(TESI_MEDIAVOTOMINIMA, 28.0);
        cvTesi.put(TESI_ESAMINECESSARI, 1);
        cvTesi.put(TESI_SKILLRICHIESTE, "Conoscenze avanzate di chimica verde");
        cvTesi.put(TESI_STATO, 1);
        cvTesi.put(TESI_VISUALIZZAZIONI, 0);
        cvTesi.put(TESI_RELATOREID, 3);
        cvTesi.put(TESI_UNIVERSITACORSOID, 6);
        db.insert(TESI, null, cvTesi);

        //TesiScelta
        ContentValues cvTesiScelta = new ContentValues();

        cvTesiScelta.put(TESISCELTA_TESIID, 1);
        cvTesiScelta.put(TESISCELTA_CORELATOREID, 1);
        cvTesiScelta.put(TESISCELTA_STATOCORELATORE, 1);
        cvTesiScelta.put(TESISCELTA_TESISTAID, 1);
        cvTesiScelta.put(TESISCELTA_CAPACITATESISTA, "Ho conoscenze avanzate di cyber security");
        db.insert(TESISCELTA, null, cvTesiScelta);

        cvTesiScelta.put(TESISCELTA_DATAPUBBLICAZIONE, "2023-02-13");
        cvTesiScelta.put(TESISCELTA_ABSTRACT, "La tesi condotta have riportato...");
        cvTesiScelta.put(TESISCELTA_DOWNLOAD, "-NOAwurzFpzWQ5qto7rK");
        cvTesiScelta.put(TESISCELTA_TESIID, 4);
        cvTesiScelta.put(TESISCELTA_TESISTAID, 2);
        cvTesiScelta.put(TESISCELTA_CAPACITATESISTA, "Ho conoscenze avanzate di chimica verde");
        db.insert(TESISCELTA, null, cvTesiScelta);

        //Tesista
        ContentValues cvTesista = new ContentValues();

        cvTesista.put(TESISTA_MATRICOLA, 717828);
        cvTesista.put(TESISTA_MEDIAVOTI, 25.0);
        cvTesista.put(TESISTA_ESAMIMANCANTI, 5);
        cvTesista.put(TESISTA_UTENTEID, 1);
        cvTesista.put(TESISTA_UNIVERSITACORSOID, 1);
        db.insert(TESISTA, null, cvTesista);

        cvTesista.put(TESISTA_MATRICOLA, 856896);
        cvTesista.put(TESISTA_MEDIAVOTI, 26.0);
        cvTesista.put(TESISTA_ESAMIMANCANTI, 3);
        cvTesista.put(TESISTA_UTENTEID, 2);
        cvTesista.put(TESISTA_UNIVERSITACORSOID, 6);
        db.insert(TESISTA, null, cvTesista);

        cvTesista.put(TESISTA_MATRICOLA, 741564);
        cvTesista.put(TESISTA_MEDIAVOTI, 23.0);
        cvTesista.put(TESISTA_ESAMIMANCANTI, 6);
        cvTesista.put(TESISTA_UTENTEID, 3);
        cvTesista.put(TESISTA_UNIVERSITACORSOID, 3);
        db.insert(TESISTA, null, cvTesista);

        //Universita
        ContentValues cvUniversita = new ContentValues();

        cvUniversita.put(UNIVERSITA_NOME, "UniBa");
        db.insert(UNIVERSITA, null, cvUniversita);

        cvUniversita.put(UNIVERSITA_NOME, "UniCa");
        db.insert(UNIVERSITA, null, cvUniversita);

        cvUniversita.put(UNIVERSITA_NOME, "UniNa");
        db.insert(UNIVERSITA, null, cvUniversita);

        //UniversitaCorso
        ContentValues cvUniCorso = new ContentValues();

        cvUniCorso.put(UNIVERSITACORSO_UNIVERSITAID, 1);
        cvUniCorso.put(UNIVERSITACORSO_CORSOID, 1);
        db.insert(UNIVERSITACORSO, null, cvUniCorso);

        cvUniCorso.put(UNIVERSITACORSO_UNIVERSITAID, 1);
        cvUniCorso.put(UNIVERSITACORSO_CORSOID, 3);
        db.insert(UNIVERSITACORSO, null, cvUniCorso);

        cvUniCorso.put(UNIVERSITACORSO_UNIVERSITAID, 2);
        cvUniCorso.put(UNIVERSITACORSO_CORSOID, 1);
        db.insert(UNIVERSITACORSO, null, cvUniCorso);

        cvUniCorso.put(UNIVERSITACORSO_UNIVERSITAID, 2);
        cvUniCorso.put(UNIVERSITACORSO_CORSOID, 2);
        db.insert(UNIVERSITACORSO, null, cvUniCorso);

        cvUniCorso.put(UNIVERSITACORSO_UNIVERSITAID, 3);
        cvUniCorso.put(UNIVERSITACORSO_CORSOID, 1);
        db.insert(UNIVERSITACORSO, null, cvUniCorso);

        cvUniCorso.put(UNIVERSITACORSO_UNIVERSITAID, 3);
        cvUniCorso.put(UNIVERSITACORSO_CORSOID, 2);
        db.insert(UNIVERSITACORSO, null, cvUniCorso);

        cvUniCorso.put(UNIVERSITACORSO_UNIVERSITAID, 3);
        cvUniCorso.put(UNIVERSITACORSO_CORSOID, 3);
        db.insert(UNIVERSITACORSO, null, cvUniCorso);

        //Utenti
        ContentValues cvUtenti = new ContentValues();

        cvUtenti.put(UTENTI_NOME, "Vito");
        cvUtenti.put(UTENTI_COGNOME, "Lotito");
        cvUtenti.put(UTENTI_EMAIL, "vito@gmail.com");
        cvUtenti.put(UTENTI_CODICEFISCALE, "LTTVTI");
        cvUtenti.put(UTENTI_PASSWORD, "vitopassword");
        cvUtenti.put(UTENTI_RUOLOID, 1);
        db.insert(UTENTI, null, cvUtenti);

        cvUtenti.put(UTENTI_NOME, "Marco");
        cvUtenti.put(UTENTI_COGNOME, "Scarpa");
        cvUtenti.put(UTENTI_EMAIL, "marco@gmail.com");
        cvUtenti.put(UTENTI_CODICEFISCALE, "MRCSCA");
        cvUtenti.put(UTENTI_PASSWORD, "marcopassword");
        cvUtenti.put(UTENTI_RUOLOID, 1);
        db.insert(UTENTI, null, cvUtenti);

        cvUtenti.put(UTENTI_NOME, "Roberto");
        cvUtenti.put(UTENTI_COGNOME, "Tondi");
        cvUtenti.put(UTENTI_EMAIL, "roberto@gmail.com");
        cvUtenti.put(UTENTI_CODICEFISCALE, "RBETNO");
        cvUtenti.put(UTENTI_PASSWORD, "robertopassword");
        cvUtenti.put(UTENTI_RUOLOID, 1);
        db.insert(UTENTI, null, cvUtenti);

        cvUtenti.put(UTENTI_NOME, "Michele");
        cvUtenti.put(UTENTI_COGNOME, "Cabiddu");
        cvUtenti.put(UTENTI_EMAIL, "michele@gmail.com");
        cvUtenti.put(UTENTI_CODICEFISCALE, "MCHCBA");
        cvUtenti.put(UTENTI_PASSWORD, "michelepassword");
        cvUtenti.put(UTENTI_RUOLOID, 2);
        db.insert(UTENTI, null, cvUtenti);

        cvUtenti.put(UTENTI_NOME, "Giuseppe");
        cvUtenti.put(UTENTI_COGNOME, "Damiano");
        cvUtenti.put(UTENTI_EMAIL, "giuseppe@gmail.com");
        cvUtenti.put(UTENTI_CODICEFISCALE, "GSEDMA");
        cvUtenti.put(UTENTI_PASSWORD, "giuseppepassword");
        cvUtenti.put(UTENTI_RUOLOID, 2);
        db.insert(UTENTI, null, cvUtenti);

        cvUtenti.put(UTENTI_NOME, "Mario");
        cvUtenti.put(UTENTI_COGNOME, "Palladino");
        cvUtenti.put(UTENTI_EMAIL, "mario@gmail.com");
        cvUtenti.put(UTENTI_CODICEFISCALE, "MRIPLA");
        cvUtenti.put(UTENTI_PASSWORD, "mariopassword");
        cvUtenti.put(UTENTI_RUOLOID, 3);
        db.insert(UTENTI, null, cvUtenti);

        cvUtenti.put(UTENTI_NOME, "Federico");
        cvUtenti.put(UTENTI_COGNOME, "Rossi");
        cvUtenti.put(UTENTI_EMAIL, "federico@gmail.com");
        cvUtenti.put(UTENTI_CODICEFISCALE, "FDERSO");
        cvUtenti.put(UTENTI_PASSWORD, "federicopassword");
        cvUtenti.put(UTENTI_RUOLOID, 2);
        db.insert(UTENTI, null, cvUtenti);
    }
}