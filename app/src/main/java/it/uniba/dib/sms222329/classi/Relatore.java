package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class Relatore extends Supervisore {

    private String idRelatore;
    private String matricola;
    private String IDCorsi[];

    public Relatore(String matricola, String nome, String cognome, String codiceFiscale, String email, String password) {
        super(nome, cognome, codiceFiscale, email, password);
        this.matricola = matricola;
        //inserire id corsi
    }

    public Relatore(){ super();}

    public String getIdRelatore() {return idRelatore;}

    public void setIdRelatore(String idRelatore) {this.idRelatore = idRelatore;}

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    //Registrazione account su database
    public boolean RegistrazioneRelatore(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRelatore = new ContentValues();

        Cursor idUtente = dbClass.RicercaDato("SELECT id FROM utenti WHERE email = '" + this.email + "';");
        idUtente.moveToNext();

        cvRelatore.put("utente_id", idUtente.getString(0));
        cvRelatore.put("matricola", this.matricola);

        try{
            long insertCoRelatore = db.insert("relatore", null, cvRelatore);
            if(insertCoRelatore != -1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private boolean modRelatore(Database dbClass, String nome, String cognome, String email, String password) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;

        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put("Nome", this.nome);
        cvUtente.put("Cognome", this.cognome);
        cvUtente.put("Email", this.email);
        cvUtente.put("Password", this.password);

        try{
            long updateUtente = db.update("utenti", cvUtente, "id = " + this.idUtente, null);
            return updateUtente != -1;
        }catch(Exception e){

        }
        return false;
    }
}
