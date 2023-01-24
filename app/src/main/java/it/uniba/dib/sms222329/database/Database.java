package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import it.uniba.dib.sms222329.classi.Tesista;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, "Laureapp.db", null, 1);
    }

    //Usato quando si accede per la prima volta al Database, codice di inizializzazione
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable;
        //Utenti
        createTable =   "CREATE TABLE utenti (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome VARCHAR(255) NOT NULL," +
                        "cognome VARCHAR(255) NOT NULL," +
                        "email VARCHAR(255) NOT NULL UNIQUE," +
                        "codice_fiscale VARCHAR(255) NOT NULL UNIQUE," +
                        "password VARCHAR(255) NOT NULL," +
                        "ruolo_id INT NOT NULL," +
                        "FOREIGN KEY (ruolo_id) REFERENCES ruoli(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Tesista
        createTable =   "CREATE TABLE tesista (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "matricola VARCHAR(255) NOT NULL," +
                        "media_voti FLOAT NOT NULL," +
                        "esami_mancanti INT NOT NULL," +
                        "utente_id INT NOT NULL UNIQUE," +
                        "universitacorso_id INT NOT NULL," +
                        "FOREIGN KEY (utente_id) REFERENCES utenti(id) ON DELETE CASCADE," +
                        "FOREIGN KEY (universitacorso_id) REFERENCES universitacorso(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Relatore
        createTable =   "CREATE TABLE relatore (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "matricola VARCHAR(255) NOT NULL," +
                        "utente_id INT NOT NULL UNIQUE," +
                        "FOREIGN KEY (utente_id) REFERENCES utenti(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //CoRelatore
        createTable =   "CREATE TABLE coRelatore (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "organizzazione VARCHAR(255) NOT NULL," +
                        "utente_id INT NOT NULL UNIQUE," +
                        "FOREIGN KEY (utente_id) REFERENCES utenti(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Universita
        createTable =   "CREATE TABLE universita (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome VARCHAR(255) NOT NULL UNIQUE);";
        db.execSQL(createTable);
        //CorsoStudi
        createTable =   "CREATE TABLE corsoStudi (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome VARCHAR(255) NOT NULL UNIQUE);";
        db.execSQL(createTable);
        //UniversitaCorso
        createTable =   "CREATE TABLE universitacorso (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "universita_id INT NOT NULL," +
                        "corso_id INT NOT NULL," +
                        "FOREIGN KEY (universita_id) REFERENCES universita(id) ON DELETE CASCADE," +
                        "FOREIGN KEY (corso_id) REFERENCES corsoStudi(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Ruoli
        createTable =   "CREATE TABLE ruoli (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ruolo VARCHAR(255) NOT NULL);";
        db.execSQL(createTable);
        //CorsiRelatore
        createTable =   "CREATE TABLE corsiRelatore (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "relatore_id INT NOT NULL," +
                        "universitacorso_id INT NOT NULL," +
                        "FOREIGN KEY (relatore_id) REFERENCES relatore(id) ON DELETE CASCADE," +
                        "FOREIGN KEY (universitacorso_id) REFERENCES universitacorso(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Tesi
        createTable =   "CREATE TABLE tesi (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "titolo VARCHAR(255) NOT NULL," +
                        "argomento VARCHAR(255) NOT NULL," +
                        "tempistiche INT NOT NULL," +
                        "media_voto_minima FLOAT NOT NULL," +
                        "esami_necessari INT NOT NULL," +
                        "skill_richieste VARCHAR(255) NOT NULL," +
                        "stato BOOLEAN NOT NULL," +
                        "visualizzazioni INT NOT NULL," +
                        "relatore_id INT NOT NULL," +
                        "FOREIGN KEY (relatore_id) REFERENCES relatore(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //RichiestaTesi
        createTable =   "CREATE TABLE richiesta (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "messaggio VARCHAR(255) NOT NULL," +
                        "tesi_id INT NOT NULL," +
                        "tesista_id INT NOT NULL," +
                        "accettata BOOLEAN NOT NULL," +
                        "risposta VARCHAR(255)," +
                        "FOREIGN KEY (tesi_id) REFERENCES tesi(id) ON DELETE CASCADE," +
                        "FOREIGN KEY (tesista_id) REFERENCES tesista(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //TesiScelta
        createTable =   "CREATE TABLE tesi_scelta (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "data_pubblicazione DATE," +
                        "abstract VARCHAR(255)," +
                        "download VARCHAR(255)," +
                        "tesi_id INT NOT NULL," +
                        "corelatore_id INT," +
                        "tesista_id INT NOT NULL UNIQUE," +
                        "FOREIGN KEY (tesi_id) REFERENCES tesi(id) ON DELETE CASCADE," +
                        "FOREIGN KEY (corelatore_id) REFERENCES coRelatore(id)," +
                        "FOREIGN KEY (tesista_id) REFERENCES tesista(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //SegnalazioneChat
        createTable =   "CREATE TABLE segnalazioneChat (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "oggetto VARCHAR(255) NOT NULL," +
                        "tesi_scelta_id INT NOT NULL," +
                        "FOREIGN KEY (tesi_scelta_id) REFERENCES tesi_scelta(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //RispostaSegnalazione
        createTable =   "CREATE TABLE messaggiSegnalazione (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "messaggio VARCHAR(255) NOT NULL," +
                        "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                        "utente_id INT NOT NULL UNIQUE," +
                        "segnalazione_id INT NOT NULL," +
                        "FOREIGN KEY (utente_id) REFERENCES utenti(id)," +
                        "FOREIGN KEY (segnalazione_id) REFERENCES tesi_scelta(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Task
        createTable =   "CREATE TABLE task (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "descrizione VARCHAR(255) NOT NULL," +
                        "data_inizio DATE NOT NULL," +
                        "data_fine DATE NOT NULL," +
                        "link_materiale VARCHAR(255)," +
                        "stato VARCHAR(255) NOT NULL," +
                        "tesi_scelta_id INT NOT NULL," +
                        "FOREIGN KEY (tesi_scelta_id) REFERENCES tesi_scelta(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Ricevimenti
        createTable =   "CREATE TABLE ricevimenti (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "data DATE NOT NULL," +
                        "orario TIME NOT NULL," +
                        "argomento VARCHAR(255) NOT NULL," +
                        "task_id INT NOT NULL," +
                        "FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE);";
        db.execSQL(createTable);
        //Popolamento tabella Universita
        ContentValues cvUniversita = new ContentValues();
        String[] listaUniversita = {"UniBa", "UniCa", "UniNa"};
        for(int i=0; i<listaUniversita.length; i++){
            cvUniversita.put("nome", listaUniversita[i]);
            db.insert("universita", null, cvUniversita);
        }
        //Popolamento tabella CorsiStudio
        ContentValues cvCorsiStudio = new ContentValues();
        String[] listaCorsiStudio = {"ITPS", "Chimica", "Matematica"};
        for(int i=0; i<listaCorsiStudio.length; i++){
            cvCorsiStudio.put("nome", listaCorsiStudio[i]);
            db.insert("corsoStudi", null, cvCorsiStudio);
        }
        //Popolamento tabella Universita-CorsiStudio
        PopolamentoUniCorsi(db);
        //Popolamento tabella ruoli
        ContentValues cvRuoli = new ContentValues();
        String[] listaRuoli = {"Tesista", "Relatore", "CoRelatore"};
        for(int i=0; i<listaRuoli.length; i++){
            cvCorsiStudio.put("ruolo", listaRuoli[i]);
            db.insert("ruoli", null, cvRuoli);
        }
    }

    //Viene chiamato nel caso di aggiornamento della versione del database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){}

    //Verifica che il dato cercato in una determinata tabella esiste o no
    public boolean VerificaDatoEsistente(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        if (cursore.getCount() != 0) {
            return true;
        }
        return false;
    }

    //Restituisce i risultati della ricerca di un dato
    public Cursor RicercaDato(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        return cursore;
    }

    //Metodo usato per i dati di mock della tabella Universita-CorsiStudio
    private void PopolamentoUniCorsi(SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put("universita_id", 1);
        cv.put("corso_id", 1);
        db.insert("universitacorso", null, cv);

        cv.put("universita_id", 1);
        cv.put("corso_id", 3);
        db.insert("universitacorso", null, cv);

        cv.put("universita_id", 2);
        cv.put("corso_id", 1);
        db.insert("universitacorso", null, cv);

        cv.put("universita_id", 2);
        cv.put("corso_id", 2);
        db.insert("universitacorso", null, cv);

        cv.put("universita_id", 3);
        cv.put("corso_id", 1);
        db.insert("universitacorso", null, cv);

        cv.put("universita_id", 3);
        cv.put("corso_id", 2);
        db.insert("universitacorso", null, cv);

        cv.put("universita_id", 3);
        cv.put("corso_id", 3);
        db.insert("universitacorso", null, cv);
    }
}