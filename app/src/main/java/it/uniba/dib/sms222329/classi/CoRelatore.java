package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class CoRelatore extends Supervisore {

    private String id;

    public CoRelatore(String id, String nome, String cognome, String email, String password) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Registrazione account su database

    public boolean registrazione(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvCoRelatore = new ContentValues();

        cvCoRelatore.put("Nome", this.nome);
        cvCoRelatore.put("Cognome", this.cognome);
        cvCoRelatore.put("Email", this.email);
        cvCoRelatore.put("Password", this.password);

        long insertCoRelatore = db.insert("CoRelatore", null, cvCoRelatore);
        if(insertCoRelatore != -1){
            ContentValues cvUtente = new ContentValues();

            cvUtente.put("Email", this.email);
            cvUtente.put("Password", this.password);
            cvUtente.put("TipoUtente", 2);

            long insertUtente = db.insert("Utenti", null, cvUtente);
            if(insertUtente != -1) {
                return true;
            }
        }
        return false;
    }

    private boolean modCoRelatore(Database dbClass, String nome, String cognome, String email, String password) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;

        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvCoRelatore = new ContentValues();

        cvCoRelatore.put("Nome", this.nome);
        cvCoRelatore.put("Cognome", this.cognome);
        cvCoRelatore.put("Email", this.email);
        cvCoRelatore.put("Password", this.password);

        long updateCoRelatore = db.update("CoRelatore", cvCoRelatore, "ID = " + this.id, null);
        return updateCoRelatore != -1;
    }
}
