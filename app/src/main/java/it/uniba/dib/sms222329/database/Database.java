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
        //Tesista
        createTable =   "CREATE TABLE IF NOT EXISTS Tesista(" +
                        "Matricola INTEGER(10) PRIMARY KEY," +
                        "Nome CHAR(255) NOT NULL," +
                        "Cognome CHAR(255) NOT NULL," +
                        "Email CHAR(255) NOT NULL REFERENCES Utenti(Email)," +
                        "Password CHAR(255) NOT NULL," +
                        "MediaVoti INTEGER(10) NOT NULL," +
                        "NumeroEsamiMancanti INTEGER(10) NOT NULL," +
                        "IdTesiScelta INTEGER(10) REFERENCES TesiScelta(TesiIDTesi)," +
                        "IdCorsoStudi INTEGER(10) NOT NULL REFERENCES CorsiStudio(ID)," +
                        "IdUniversita INTEGER(10) NOT NULL REFERENCES Universita(ID));";
        db.execSQL(createTable);
        //Università
        createTable =   "CREATE TABLE IF NOT EXISTS Universita(" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Nome CHAR(255) NOT NULL)";
        db.execSQL(createTable);
        //CorsoStudi
        createTable =   "CREATE TABLE IF NOT EXISTS CorsiStudio(" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Nome CHAR(255) NOT NULL)";
        db.execSQL(createTable);
        //Universita-Tesi
        createTable =   "CREATE TABLE IF NOT EXISTS UniversitaCorsistudio(" +
                        "IDUniversita INTEGER REFERENCES Universita(ID)," +
                        "IDCorsoStudio INTEGER REFERENCES CorsiStudio(ID)," +
                        "PRIMARY KEY(IDUniversita, IDCorsoStudio))";
        db.execSQL(createTable);
        //Relatore
        createTable =   "CREATE TABLE IF NOT EXISTS Relatore(" +
                        "Matricola INTEGER(10) PRIMARY KEY," +
                        "Nome CHAR(255) NOT NULL," +
                        "Cognome CHAR(255) NOT NULL," +
                        "MateriaInsegnata CHAR(255) NOT NULL," +
                        "IdUniversita INTEGER(10) NOT NULL REFERENCES Universita(ID)," +
                        "Email CHAR(255) NOT NULL REFERENCES Utenti(Email)," +
                        "Password CHAR(255) NOT NULL);";
        db.execSQL(createTable);
        //CoRelatore
        createTable =   "CREATE TABLE IF NOT EXISTS CoRelatore(" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Nome CHAR(255) NOT NULL," +
                        "Cognome CHAR(255) NOT NULL," +
                        "Email CHAR(255) NOT NULL REFERENCES Utenti(Email)," +
                        "Password CHAR(255) NOT NULL);";
        db.execSQL(createTable);
        //Utente
        createTable =   "CREATE TABLE IF NOT EXISTS Utenti(" +
                        "Email CHAR(255) PRIMARY KEY," +
                        "Password CHAR(255) NOT NULL," +
                        "TipoUtente INTEGER(1)NOT NULL );";
        db.execSQL(createTable);
        //Tesi
        createTable =   "CREATE TABLE IF NOT EXISTS Tesi(" +
                        "IDTesi INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Argomenti CHAR(255) NOT NULL," +
                        "DataPubblicazione TIME(6) NOT NULL," +
                        "StatoDisponibilita NUMBER(1) NOT NULL," +
                        "NumeroVisualizzazioni INTEGER(10) NOT NULL," +
                        "MatricolaRelatore INTEGER(10) NOT NULL REFERENCES Relatore(Matricola)," +
                        "IDCorelatore INTEGER(10) REFERENCES CoRelatore (ID)," +
                        "LinkMateriale CHAR(255)," +   //Da rivedere
                        "QRCode INTEGER(10)," +
                        "Tempistiche INTEGER(10) NOT NULL," +
                        "MediaVotiMinima INTEGER(10) NOT NULL," +
                        "EsamiMancantiNecessari CHAR(255) NOT NULL," +
                        "SkillRichieste CHAR(255) NOT NULL);";
        db.execSQL(createTable);
        //TesiScelta
        createTable =   "CREATE TABLE IF NOT EXISTS TesiScelta(" +
                        "IDTesiScelta INTEGER (10) PRIMARY KEY REFERENCES Tesi(IDTesi)," +
                        "IDTask INTEGER(10) NOT NULL REFERENCES Task(IDTask));";
        db.execSQL(createTable);
        //Task
        createTable =   "CREATE TABLE IF NOT EXISTS Task(" +
                        "IDTask INTEGER(10) PRIMARY KEY," +
                        "Descrizione CHAR(255) NOT NULL," +
                        "DataInizio TIME(6) NOT NULL," +
                        "DataFine TIME(6)," +
                        "Stato CHAR(255) NOT NULL);";
        db.execSQL(createTable);
        //Ricevimento
        createTable =   "CREATE TABLE IF NOT EXISTS Ricevimenti(" +
                        "IDRicevimenti INTEGER(10) NOT NULL," +
                        "IDtask INTEGER(10) REFERENCES Task(IDTask)," +
                        "MatricolaRelatore INTEGER(10) NOT NULL REFERENCES Relatore(Matricola)," +
                        "Matricolatesista INTEGER(10) NOT NULL REFERENCES Tesista(Matricola)," +
                        "Data TIME(6) NOT NULL," +
                        "Orario TIME(6) NOT NULL," +
                        "Argomento CHAR(255) NOT NULL);";
        db.execSQL(createTable);
        //Segnalazione
        createTable =   "CREATE TABLE IF NOT EXISTS Segnalazione(" +
                        "IDSegnalazione INTEGER(10) PRIMARY KEY," +
                        "IDTesi INTEGER(10) NOT NULL," +
                        "MatricolaTesista INTEGER(10) NOT NULL REFERENCES Tesista(Matricola)," +
                        "MatricolaRelatore INTEGER (10) NOT NULL );";
        db.execSQL(createTable);
        //Popolamento tabella Universita
        ContentValues cvUniversita = new ContentValues();
        String[] listaUniversita = {"UniBa", "UniCa", "UniNa"};
        for(int i=0; i<listaUniversita.length; i++){
            cvUniversita.put("Nome", listaUniversita[i]);
            db.insert("Universita", null, cvUniversita);
        }
        //Popolamento tabella CorsiStudio
        ContentValues cvCorsiStudio = new ContentValues();
        String[] listaCorsiStudio = {"ITPS", "Chimica", "Matematica"};
        for(int i=0; i<listaCorsiStudio.length; i++){
            cvCorsiStudio.put("Nome", listaCorsiStudio[i]);
            db.insert("CorsiStudio", null, cvCorsiStudio);
        }
        //Popolamento tabella Universita-CorsiStudio
        PopolamentoUniCorsi(db);
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

        cv.put("IDUniversita", 1);
        cv.put("IDCorsoStudio", 1);
        db.insert("UniversitaCorsistudio", null, cv);

        cv.put("IDUniversita", 1);
        cv.put("IDCorsoStudio", 3);
        db.insert("UniversitaCorsistudio", null, cv);

        cv.put("IDUniversita", 2);
        cv.put("IDCorsoStudio", 1);
        db.insert("UniversitaCorsistudio", null, cv);

        cv.put("IDUniversita", 2);
        cv.put("IDCorsoStudio", 2);
        db.insert("UniversitaCorsistudio", null, cv);

        cv.put("IDUniversita", 3);
        cv.put("IDCorsoStudio", 1);
        db.insert("UniversitaCorsistudio", null, cv);

        cv.put("IDUniversita", 3);
        cv.put("IDCorsoStudio", 2);
        db.insert("UniversitaCorsistudio", null, cv);

        cv.put("IDUniversita", 3);
        cv.put("IDCorsoStudio", 3);
        db.insert("UniversitaCorsistudio", null, cv);
    }
}