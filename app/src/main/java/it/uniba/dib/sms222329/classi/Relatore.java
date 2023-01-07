package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class Relatore extends Supervisore {

    private String matricola;
    private String materiaInsegnata;

    public Relatore(String matricola, String nome, String cognome, String materiaInsegnata, String email, String password) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.materiaInsegnata = materiaInsegnata;
        this.email = email;
        this.password = password;
    }

    public Relatore(){}

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getMateriaInsegnata() {return materiaInsegnata;}

    public void setMateriaInsegnata(String materiaInsegnata) {this.materiaInsegnata = materiaInsegnata;}

    //Registrazione account su database
    public boolean registrazione(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRelatore = new ContentValues();

        cvRelatore.put("Matricola", this.matricola);
        cvRelatore.put("Nome", this.nome);
        cvRelatore.put("Cognome", this.cognome);
        cvRelatore.put("MateriaInsegnata", this.materiaInsegnata);
        cvRelatore.put("Email", this.email);
        cvRelatore.put("Password", this.password);

        long insertRelatore = db.insert("Relatore", null, cvRelatore);
        if(insertRelatore != -1){//se va bene l inserimento
            ContentValues cvUtente = new ContentValues();
            cvUtente.put("Email", this.email);
            cvUtente.put("Password", this.password);
            cvUtente.put("TipoUtente", 1);

            long insertUtente = db.insert("Utenti", null, cvUtente);
            if(insertUtente != -1) {
                return true;
            }
        }
        return false;
    }
}
