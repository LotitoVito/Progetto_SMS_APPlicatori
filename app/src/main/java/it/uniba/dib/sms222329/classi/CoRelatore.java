package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class CoRelatore extends Supervisore {

    private String id;

    public CoRelatore(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Registrazione account su database
    @Override
    public boolean registrazione(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Nome", this.nome);
        cv.put("Cognome", this.cognome);
        cv.put("Email", this.email);
        cv.put("Password", this.password);

        long insert = db.insert("CoRelatore", null, cv);
        if(insert != -1){
            return true;
        } else{
            return false;
        }
    }
}
