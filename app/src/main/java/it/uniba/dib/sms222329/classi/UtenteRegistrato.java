package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class UtenteRegistrato extends Utente {
    String nome;
    String cognome;
    String email;
    String password;
    String TipoUtente;

    public UtenteRegistrato() {
        TipoUtente = "-1";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {}

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoUtente() {
        return TipoUtente;
    }

    public boolean LoginStatus(Database dbClass) {
        String query = "SELECT TipoUtente FROM Utenti WHERE Email = '" + email + "' AND Password = '" + password + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        if (cursore.getCount() == 0) {
            //INSERISCI MESSAGGIO DI ERRORE GESTITO TRAMITE LoginActivity.java
            return false;
        } else {
            cursore.moveToNext();
            TipoUtente = cursore.getString(0); //nel select puoi farti restituire piu valori, mettendo 0 restituisce solom il primo valore della prima colonna, è come un array
            return true;
        }
    }

    public Tesista IstanziaTesista(String email, String password, Database dbClass){
        Tesista TesistaLog = new Tesista();
        TesistaLog.setEmail(email);
        TesistaLog.setPassword(password);

        String query = "SELECT * FROM tesista WHERE email = '" + email + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        cursore.moveToNext();

        TesistaLog.setMatricola(cursore.getString(0));
        TesistaLog.setNome(cursore.getString(1));
        TesistaLog.setCognome(cursore.getString(2));
        TesistaLog.setMedia(cursore.getInt(5));
        TesistaLog.setNumeroEsamiMancanti(cursore.getInt(6));
        TesistaLog.setTesiScelta(cursore.getInt(7));

        return TesistaLog;
    }


    public Relatore IstanziaRelatore(String email, String password, Database dbClass){
        Relatore relatoreLog = new Relatore();
        relatoreLog.setEmail(email);
        relatoreLog.setPassword(password);

        String query = "SELECT * FROM relatore WHERE email = '" + email + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        cursore.moveToNext();

        relatoreLog.setMatricola(cursore.getString(0));
        relatoreLog.setNome(cursore.getString(1));
        relatoreLog.setCognome(cursore.getString(2));
        relatoreLog.setMateriaInsegnata(cursore.getString(3));
        relatoreLog.setIDUniversita(cursore.getString(4));
        relatoreLog.setEmail(cursore.getString(5));
        relatoreLog.setPassword(cursore.getString(6));

        return relatoreLog;
    }


    public CoRelatore IstanziaCoRelatore(String email, String password, Database dbClass){
        CoRelatore CorelatoreLog = new CoRelatore();
        CorelatoreLog.setEmail(email);
        CorelatoreLog.setPassword(password);

        String query = "SELECT * FROM Corelatore WHERE email = '" + email + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        cursore.moveToNext();

        CorelatoreLog.setId(cursore.getString(0));
        CorelatoreLog.setNome(cursore.getString(1));
        CorelatoreLog.setCognome(cursore.getString(2));
        CorelatoreLog.setEmail(cursore.getString(3));
        CorelatoreLog.setPassword(cursore.getString(4));

        return CorelatoreLog;
    }
}
