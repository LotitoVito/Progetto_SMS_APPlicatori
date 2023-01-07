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
                "EsamiSvolti INTEGER(10) NOT NULL," +
                "IdTesiScelta INTEGER(10) REFERENCES TesiScelta(TesiIDTesi));";
        db.execSQL(createTable);
        //Relatore
        createTable =   "CREATE TABLE IF NOT EXISTS Relatore(" +
                "Matricola INTEGER(10) PRIMARY KEY," +
                "Nome CHAR(255) NOT NULL," +
                "Cognome CHAR(255) NOT NULL," +
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
                "IDTesi INTEGER(10) PRIMARY KEY," +
                "Argomenti CHAR(255) NOT NULL," +
                "DataPubblicazione TIME(6) NOT NULL," +
                "MatricolaRelatore INTEGER(10) NOT NULL REFERENCES Relatore(Matricola)," +
                "IDCorelatore INTEGER(10) REFERENCES CoRelatore (ID)," +
                "QRCode INTEGER(10)," +
                "UtenteIDUtente INTEGER(10)," +
                "UtenteIDUtente2 INTEGER(10));";
        db.execSQL(createTable);
        //Materiale
        createTable =   "CREATE TABLE IF NOT EXISTS Materiale(" +
                "IDTesi INTEGER (10)PRIMARY KEY REFERENCES Tesi(IDTesi)," +
                "LinkMateriale CHAR(255));";
        db.execSQL(createTable);
        //Vincoli
        createTable =   "CREATE TABLE IF NOT EXISTS Vincoli(" +
                "IDTesi INTEGER(10) PRIMARY KEY REFERENCES Tesi(IDTesi)," +
                "Tempistiche INTEGER(10) NOT NULL," +
                "MediaVotiMinima INTEGER(10) NOT NULL," +
                "EsamiNecessari CHAR(255) NOT NULL," +
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
    }

    //Viene chiamato nel caso di aggiornamento della versione del database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){}

    public boolean VerificaDatoEsistente(String campo, String tabella, String dato){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + campo + " FROM " + tabella + " WHERE " + campo + " = '" + dato + "';";
        Cursor cursore = db.rawQuery(query, null);

        if (cursore.getCount() != 0) {
            return true;
        }
        return false;
    }
}
