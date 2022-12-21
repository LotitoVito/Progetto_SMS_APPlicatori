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

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
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

        long insert = db.insert("Relatore", null, cv);
        if(insert != -1){
            return true;
        } else{
            return false;
        }
    }
}
