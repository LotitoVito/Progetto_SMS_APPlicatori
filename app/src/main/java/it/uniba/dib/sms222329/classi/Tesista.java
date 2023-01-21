package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class Tesista extends UtenteRegistrato {

    private int idTesista;
    private String matricola;
    private float media;
    private int numeroEsamiMancanti;
    private int idUniversitaCorso;
    private int corso;

    public Tesista(String matricola, String nome, String cognome, String codiceFiscale, String email, String password, float media, int numeroEsamiMancanti, int idUniversitaCorso) {
        super(nome, cognome, codiceFiscale, email, password);
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

    public boolean modTesista(Database dbClass, String matricola, String nome, String cognome,
                              String email, String password, float media, int numEsamiMancanti,
                              String codFisc, int corso) {
        this.matricola=matricola;
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.media=media;
        this.numeroEsamiMancanti=numEsamiMancanti;
        this.codiceFiscale=codFisc;
        this.corso=corso;

        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put("nome", this.nome);
        cvUtente.put("cognome", this.cognome);
        cvUtente.put("email", this.email);
        cvUtente.put("password", this.password);
        cvUtente.put("codice_fiscale", this.codiceFiscale);

        try{
            long updateUtente = db.update("utenti", cvUtente, "id = " + this.idUtente, null);
            if(updateUtente != -1){
                ContentValues cvTesista = new ContentValues();

                cvTesista.put("matricola", this.matricola);
                cvTesista.put("media_voti", this.media);
                cvTesista.put("esami_mancanti", this.numeroEsamiMancanti);
                cvTesista.put("universitacorso_id", this.corso);

                long updateTesista = db.update("tesista", cvTesista, "id = " + this.idTesista, null);
                return updateTesista != -1;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}