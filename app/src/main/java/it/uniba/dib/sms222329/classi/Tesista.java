package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class Tesista extends UtenteRegistrato {

    private String matricola;
    //private Tesi tesiScelta;
    private float media;
    private int numeroEsamiSvolti;

    public Tesista(String matricola, String nome, String cognome, String email, String password, float media, int numeroEsamiSvolti) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.media = media;
        this.numeroEsamiSvolti = numeroEsamiSvolti;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public int getEsamiSvolti() {
        return numeroEsamiSvolti;
    }

    public void setEsamiSvolti(int esamiSvolti) {
        this.numeroEsamiSvolti = esamiSvolti;
    }

    //Registrazione account su database
    @Override
    public boolean registrazione(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Matricola", this.matricola);
        cv.put("Nome", this.nome);
        cv.put("Cognome", this.cognome);
        cv.put("Email", this.email);
        cv.put("Password", this.password);
        cv.put("MediaVoti", this.media);
        cv.put("EsamiSvolti", this.numeroEsamiSvolti);

        long insert = db.insert("Tesista", null, cv);
        if(insert != -1){
            return true;
        } else{
            return false;
        }
    }
}