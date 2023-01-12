package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class CoRelatore extends Supervisore {

    private String idCorelatore;

    public CoRelatore(String idCorelatore, String nome, String cognome, String email, String password) {
        this.idCorelatore = idCorelatore;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    //Costruttore per registrazione
    public CoRelatore(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public CoRelatore() {}

    public String getIdCorelatore() {return idCorelatore;}

    public void setIdCorelatore(String idCorelatore) {this.idCorelatore = idCorelatore;}

    //Inserire organizzazione
    public boolean RegistrazioneCoRelatore(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvCoRelatore = new ContentValues();

        Cursor idUtente = dbClass.RicercaDato("SELECT id FROM utenti WHERE email = '" + this.email + "';");
        idUtente.moveToNext();

        cvCoRelatore.put("utente_id", idUtente.getString(0));
        //cvCoRelatore.put("organizzazione", this.organizzazione);

        try{
            long insertCoRelatore = db.insert("coRelatore", null, cvCoRelatore);
            if(insertCoRelatore != -1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private boolean modCoRelatore(Database dbClass, String nome, String cognome, String email, String password) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;

        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put("nome", this.nome);
        cvUtente.put("cognome", this.cognome);
        cvUtente.put("email", this.email);
        cvUtente.put("password", this.password);

        try {
            long updateUtente = db.update("utenti", cvUtente, "id = " + this.idUtente, null);
            return updateUtente != -1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
