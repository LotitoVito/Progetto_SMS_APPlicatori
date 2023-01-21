package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class CoRelatore extends Supervisore {

    private String organizzazione;
    private int idCorelatore;

    public CoRelatore(int idCorelatore, String nome, String cognome, String codiceFiscale, String email, String password, String organizzazione) {
        super(nome, cognome, codiceFiscale, email, password);
        this.idCorelatore = idCorelatore;
        this.organizzazione = organizzazione;
    }

    //Costruttore per registrazione
    public CoRelatore(String nome, String cognome, String codiceFiscale, String email, String password, String organizzazione) {
        super(nome, cognome, codiceFiscale, email, password);
        this.organizzazione = organizzazione;
    }

    public CoRelatore() {
        super();
    }

    public int getIdCorelatore() {return idCorelatore;}

    public void setIdCorelatore(int idCorelatore) {this.idCorelatore = idCorelatore;}

    public String getOrganizzazione() {return organizzazione;}

    public void setOrganizzazione(String organizzazione) {this.organizzazione = organizzazione;}

    //Inserire organizzazione
    public boolean RegistrazioneCoRelatore(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvCoRelatore = new ContentValues();

        Cursor idUtente = dbClass.RicercaDato("SELECT id FROM utenti WHERE email = '" + this.email + "';");
        idUtente.moveToNext();

        cvCoRelatore.put("utente_id", idUtente.getString(0));
        cvCoRelatore.put("organizzazione", this.organizzazione);

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

    public boolean modCoRelatore(Database dbClass, String nome, String cognome, String email,
                                  String password, String codFisc, String org) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.organizzazione=org;
        this.codiceFiscale=codFisc;

        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put("nome", this.nome);
        cvUtente.put("cognome", this.cognome);
        cvUtente.put("email", this.email);
        cvUtente.put("password", this.password);
        cvUtente.put("codice_fiscale", this.codiceFiscale);

        try {
            long updateUtente = db.update("utenti", cvUtente, "id = " + this.idUtente, null);

            ContentValues cvCoRel= new ContentValues();
            cvCoRel.put("organizzazione", this.organizzazione);
            long updateCoRel = db.update("utenti", cvCoRel, "id = " + this.idCorelatore, null);

            return updateUtente != -1 && updateCoRel !=-1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
