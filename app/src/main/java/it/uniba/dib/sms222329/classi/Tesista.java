package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class Tesista extends UtenteRegistrato {

    private String idTesista;
    private String matricola;
    private int tesiScelta;
    private float media;
    private int numeroEsamiMancanti;
    private String idUniversitaCorso;

    public Tesista(String matricola, String nome, String cognome, String codiceFiscale, String email, String password, float media, int numeroEsamiMancanti, String idUniversitaCorso) {
        super(nome, cognome, codiceFiscale, email, password);
        this.matricola = matricola;
        this.media = media;
        this.numeroEsamiMancanti = numeroEsamiMancanti;
        this.idUniversitaCorso = idUniversitaCorso;
    }

    public Tesista() {
        super();
    }

    public String getIdTesista() {return idTesista;}

    public void setIdTesista(String idTesista) {this.idTesista = idTesista;}

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

    public String getIdUniversitaCorso() {return idUniversitaCorso;}

    public void setIdUniversitaCorso(String idUniversitaCorso) {this.idUniversitaCorso = idUniversitaCorso;}

    //Registrazione account su database
    public boolean RegistrazioneTesista(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesista = new ContentValues();

        Cursor idUtente = dbClass.RicercaDato("SELECT id FROM utenti WHERE email = '" + this.email + "';");
        idUtente.moveToNext();

        cvTesista.put("utente_id", idUtente.getString(0));
        cvTesista.put("matricola", this.matricola);
        cvTesista.put("media_voti", this.media);
        cvTesista.put("esami_mancanti", this.numeroEsamiMancanti);
        cvTesista.put("universitacorso_id", this.idUniversitaCorso);

        try{
            long insertCoRelatore = db.insert("tesista", null, cvTesista);
            if(insertCoRelatore != -1){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
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
        ContentValues cvUtente = new ContentValues();

        cvUtente.put("Nome", this.nome);
        cvUtente.put("Cognome", this.cognome);
        cvUtente.put("Email", this.email);
        cvUtente.put("Password", this.password);
        cvUtente.put("MediaVoti", this.media);
        cvUtente.put("NumeroEsamiMancanti", this.numeroEsamiMancanti);

        try{
            long updateUtente = db.update("utenti", cvUtente, "id = " + this.idUtente, null);
            if(updateUtente != -1){
                ContentValues cvTesista = new ContentValues();

                cvTesista.put("MediaVoti", this.media);
                cvTesista.put("NumeroEsamiMancanti", this.numeroEsamiMancanti);

                long updateTesista = db.update("tesista", cvTesista, "id = " + this.idTesista, null);
                if(updateTesista != -1){
                    return true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}