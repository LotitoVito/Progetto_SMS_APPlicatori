package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiDatabase;
import it.uniba.dib.sms222329.database.TesistaDatabase;

public class Tesista extends UtenteRegistrato {

    private int idTesista;
    private String matricola;
    private float media;
    private int numeroEsamiMancanti;
    private int idUniversitaCorso;

    public Tesista(String matricola, String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente, float media, int numeroEsamiMancanti, int idUniversitaCorso) {
        super(nome, cognome, codiceFiscale, email, password, tipoUtente);
        this.matricola = matricola;
        this.media = media;
        this.numeroEsamiMancanti = numeroEsamiMancanti;
        this.idUniversitaCorso = idUniversitaCorso;
    }

    public Tesista() {
        super();
    }

    public int getIdTesista() {return idTesista;}

    public void setIdTesista(int idTesista) {this.idTesista = idTesista;}

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

    public int getIdUniversitaCorso() {return idUniversitaCorso;}

    public void setIdUniversitaCorso(int idUniversitaCorso) {this.idUniversitaCorso = idUniversitaCorso;}

    public boolean modTesista(String matricola, String nome, String cognome, String email,
                           String password, float media, int numEsamiMancanti, String codFisc, int idUniversitaCorso, Database db) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.codiceFiscale=codFisc;
        this.matricola=matricola;
        this.media=media;
        this.numeroEsamiMancanti=numEsamiMancanti;
        this.idUniversitaCorso=idUniversitaCorso;

        if (TesistaDatabase.modTesista(this, db)){
            return true;
        }
        return false;
    }
}