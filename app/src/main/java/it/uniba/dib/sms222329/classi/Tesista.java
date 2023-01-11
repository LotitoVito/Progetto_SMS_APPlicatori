package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class Tesista extends UtenteRegistrato {

    private String matricola;
    private int tesiScelta;
    private float media;
    private int numeroEsamiMancanti;
    private String IDCorsoStudi;
    private String IDUniversita;

    public Tesista(String matricola, String nome, String cognome, String email, String password, float media, int numeroEsamiMancanti, String IDUniversita, String IDCorsoStudi) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.media = media;
        this.numeroEsamiMancanti = numeroEsamiMancanti;
        this.IDCorsoStudi = IDCorsoStudi;
        this.IDUniversita = IDUniversita;
    }
    public Tesista(){}

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

    public int getNumeroEsamiMancanti() {
        return numeroEsamiMancanti;
    }

    public void setNumeroEsamiMancanti(int numeroEsamiMancanti) {this.numeroEsamiMancanti = numeroEsamiMancanti;}

    public int getTesiScelta(){
        return tesiScelta;
    }

    public void setTesiScelta(int tesiScelta) {
        this.tesiScelta = tesiScelta;
    }

    public String getIDCorsoStudi() {return IDCorsoStudi;}

    public void setIDCorsoStudi(String IDCorsoStudi) {this.IDCorsoStudi = IDCorsoStudi;}

    public String getIDUniversita() {return IDUniversita;}

    public void setIDUniversita(String IDUniversita) {this.IDUniversita = IDUniversita;}

    //Registrazione account su database
    public boolean registrazione(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesista = new ContentValues();

        cvTesista.put("Matricola", this.matricola);
        cvTesista.put("Nome", this.nome);
        cvTesista.put("Cognome", this.cognome);
        cvTesista.put("Email", this.email);
        cvTesista.put("Password", this.password);
        cvTesista.put("MediaVoti", this.media);
        cvTesista.put("NumeroEsamiMancanti", this.numeroEsamiMancanti);
        cvTesista.put("IdCorsoStudi", this.IDCorsoStudi);
        cvTesista.put("IdUniversita", this.IDUniversita);

        long insertTesista = db.insert("Tesista", null, cvTesista);
        if(insertTesista != -1){
            ContentValues cvUtente = new ContentValues();

            cvUtente.put("Email", this.email);
            cvUtente.put("Password", this.password);
            cvUtente.put("TipoUtente", 0);

            long insertUtente = db.insert("Utenti", null, cvUtente);
            if(insertUtente != -1){
                return true;
            }
        }
        return false;
    }

    private boolean modTesista(Database dbClass, String matricola, String nome, String cognome, String email, String password, float media, int numEsamiMancanti) {
        this.matricola=matricola;
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.media=media;
        this.numeroEsamiMancanti=numEsamiMancanti;

        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesista = new ContentValues();

        cvTesista.put("Nome", this.nome);
        cvTesista.put("Cognome", this.cognome);
        cvTesista.put("Email", this.email);
        cvTesista.put("Password", this.password);
        cvTesista.put("MediaVoti", this.media);
        cvTesista.put("NumeroEsamiMancanti", this.numeroEsamiMancanti);

        long updateTesista = db.update("Tesista", cvTesista, "Matricola = " + this.matricola, null);
        return updateTesista != -1;
    }
}