package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class Relatore extends Supervisore {

    private String matricola;

    public Relatore(String matricola, String nome, String cognome, String email, String password) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public Relatore(){

    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    //Registrazione account su database

    public boolean registrazione(Database dbClass) { //passo il db creato nell'activity, è una variabile
        SQLiteDatabase db = dbClass.getWritableDatabase();  //contiene i metodi per accedere al db scrivibile in questo caso
        ContentValues cvRelatore = new ContentValues(); //classe di sqllitedb, per scrivere, è un contenitore di valori

        cvRelatore.put("Matricola", this.matricola);
        cvRelatore.put("Nome", this.nome);
        cvRelatore.put("Cognome", this.cognome);
        cvRelatore.put("Email", this.email);
        cvRelatore.put("Password", this.password);

        long insertRelatore = db.insert("Relatore", null, cvRelatore);// scriv il nome della tabella -> db.insert è un metodo mentre cv relatore contiene le variabili da scrivere nel db
        if(insertRelatore != -1){//se va bene l inserimento
            ContentValues cvUtente = new ContentValues(); /* ricreo un nuovo contenitore di valori perchè
            la tabella dove devo reinserire i dati ha in questo caso meno colonne (e diverse) rispetto alla precedente
            (possono essere omesse delle colonne in cui non si vogliono omettere dei valori)
            se inserisco il nome colonna errato mi da ovviamente un errore*/
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
