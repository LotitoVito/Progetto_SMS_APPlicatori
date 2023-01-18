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
    int idUtente;
    String nome;
    String cognome;
    String codiceFiscale;
    String email;
    String password;
    int TipoUtente;

    public UtenteRegistrato(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UtenteRegistrato(String nome, String cognome, String codiceFiscale, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.password = password;
    }

    public UtenteRegistrato() {
    }

    public int getIdUtente() {return idUtente;}

    public void setIdUtente(int idUtente) {this.idUtente = idUtente;}

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

    public String getCodiceFiscale() {return codiceFiscale;}

    public void setCodiceFiscale(String codiceFiscale) {this.codiceFiscale = codiceFiscale;}

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

    public int getTipoUtente() {
        return TipoUtente;
    }

    //Inserire anche codice_fiscale
    public boolean RegistrazioneUtente(Database dbClass, int ruolo) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put("nome", this.nome);
        cvUtente.put("cognome", this.cognome);
        cvUtente.put("codice_fiscale", this.codiceFiscale);
        cvUtente.put("email", this.email);
        cvUtente.put("password", this.password);
        cvUtente.put("ruolo_id", ruolo);

        try{
            long insertUtente = db.insert("utenti", null, cvUtente);
            if(insertUtente != -1){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean LoginStatus(Database dbClass) {
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query = "SELECT ruolo_id FROM utenti WHERE email = '" + this.email + "' AND password = '" + this.password + "';";
        Cursor cursore = db.rawQuery(query, null);

        if (cursore.getCount() != 0) {
            cursore.moveToNext();
            this.TipoUtente = cursore.getInt(0); //nel select puoi farti restituire piu valori, mettendo 0 restituisce solom il primo valore della prima colonna, è come un array
            return true;
        }
        return false;
    }

    public Tesista IstanziaTesista(Database dbClass){
        Tesista TesistaLog = new Tesista();

        String query =  "SELECT u.id, t.id, matricola, nome, cognome, email, password, media_voti, esami_mancanti, universitacorso_id" +
                        " FROM utenti u, tesista t WHERE u.id=t.utente_id AND email = '" + email + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        cursore.moveToNext();

        TesistaLog.setIdUtente(cursore.getInt(0));
        TesistaLog.setIdTesista(cursore.getInt(1));
        TesistaLog.setMatricola(cursore.getString(2));
        TesistaLog.setNome(cursore.getString(3));
        TesistaLog.setCognome(cursore.getString(4));
        TesistaLog.setEmail(cursore.getString(5));
        TesistaLog.setPassword(cursore.getString(6));
        TesistaLog.setMedia(cursore.getInt(7));
        TesistaLog.setNumeroEsamiMancanti(cursore.getInt(8));
        TesistaLog.setIdUniversitaCorso(cursore.getInt(9));
        //Tesi scelta, codice fiscale

        return TesistaLog;
    }

    public Relatore IstanziaRelatore(Database dbClass){
        Relatore relatoreLog = new Relatore();

        String query = "SELECT u.id, r.id, matricola, nome, cognome, email, password FROM utenti u, relatore r WHERE u.id=r.utente_id AND email = '" + this.email + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        cursore.moveToNext();

        relatoreLog.setIdUtente(cursore.getInt(0));
        relatoreLog.setIdRelatore(cursore.getInt(1));
        relatoreLog.setMatricola(cursore.getString(2));
        relatoreLog.setNome(cursore.getString(3));
        relatoreLog.setCognome(cursore.getString(4));
        relatoreLog.setEmail(cursore.getString(5));
        relatoreLog.setPassword(cursore.getString(6));
        //CorsiUniversita, codice fiscale

        return relatoreLog;
    }


    public CoRelatore IstanziaCoRelatore(Database dbClass){
        CoRelatore CorelatoreLog = new CoRelatore();
        CorelatoreLog.setEmail(email);
        CorelatoreLog.setPassword(password);

        String query =  "SELECT u.id, c.id, nome, cognome, email, password, organizzazione " +
                        "FROM utenti u, Corelatore c WHERE u.id=c.utente_id AND email = '" + email + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        cursore.moveToNext();

        CorelatoreLog.setIdUtente(cursore.getInt(0));
        CorelatoreLog.setIdCorelatore(cursore.getInt(1));
        CorelatoreLog.setNome(cursore.getString(2));
        CorelatoreLog.setCognome(cursore.getString(3));
        CorelatoreLog.setEmail(cursore.getString(4));
        CorelatoreLog.setPassword(cursore.getString(5));
        //organizzazione, codice fiscale

        return CorelatoreLog;
    }
}
