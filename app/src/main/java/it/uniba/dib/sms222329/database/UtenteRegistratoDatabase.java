package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;

public class UtenteRegistratoDatabase {

    public static boolean RegistrazioneUtente(Tesista tesista, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put(Database.UTENTI_NOME, tesista.getNome());
        cvUtente.put(Database.UTENTI_COGNOME, tesista.getCognome());
        cvUtente.put(Database.UTENTI_CODICEFISCALE, tesista.getCodiceFiscale());
        cvUtente.put(Database.UTENTI_EMAIL, tesista.getEmail());
        cvUtente.put(Database.UTENTI_PASSWORD, tesista.getPassword());
        cvUtente.put(Database.UTENTI_RUOLOID, tesista.getTipoUtente());

        try{
            long insertUtente = db.insert(Database.UTENTI, null, cvUtente);
            if(insertUtente != -1){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean RegistrazioneUtente(Relatore relatore, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put(Database.UTENTI_NOME, relatore.getNome());
        cvUtente.put(Database.UTENTI_COGNOME, relatore.getCognome());
        cvUtente.put(Database.UTENTI_CODICEFISCALE, relatore.getCodiceFiscale());
        cvUtente.put(Database.UTENTI_EMAIL, relatore.getEmail());
        cvUtente.put(Database.UTENTI_PASSWORD, relatore.getPassword());
        cvUtente.put(Database.UTENTI_RUOLOID, relatore.getTipoUtente());

        try{
            long insertUtente = db.insert(Database.UTENTI, null, cvUtente);
            if(insertUtente != -1){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean RegistrazioneUtente(CoRelatore coRelatore, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put(Database.UTENTI_NOME, coRelatore.getNome());
        cvUtente.put(Database.UTENTI_COGNOME, coRelatore.getCognome());
        cvUtente.put(Database.UTENTI_CODICEFISCALE, coRelatore.getCodiceFiscale());
        cvUtente.put(Database.UTENTI_EMAIL, coRelatore.getEmail());
        cvUtente.put(Database.UTENTI_PASSWORD, coRelatore.getPassword());
        cvUtente.put(Database.UTENTI_RUOLOID, coRelatore.getTipoUtente());

        try{
            long insertUtente = db.insert(Database.UTENTI, null, cvUtente);
            if(insertUtente != -1){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean LoginStatus(UtenteRegistrato account, Database dbClass) {
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query = "SELECT " + Database.UTENTI_RUOLOID + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_EMAIL + " = '" + account.getEmail() + "' AND " + Database.UTENTI_PASSWORD + " = '" + account.getPassword() + "';";
        Cursor cursore = db.rawQuery(query, null);

        if (cursore.getCount() != 0) {
            cursore.moveToNext();
            account.setTipoUtente(cursore.getInt(0)); //nel select puoi farti restituire piu valori, mettendo 0 restituisce solom il primo valore della prima colonna, è come un array
            return true;
        }
        return false;
    }

    public static boolean ControlloMail(String email1, String email2, Database dbClass){     //ritorna vero se le email corrispondono e se c'è corrispondenza nel db
        String query = "SELECT " + Database.UTENTI_EMAIL + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_EMAIL + " = '" + email1 + "';";
        if (email1.equals(email2) && dbClass.VerificaDatoEsistente(query)){
            return true;
        }
        return false;
    }

    public static boolean ResetPassword(String password1, String password2, String email, Database dbClass){
        if(password1.equals(password2)){
            String aggiornaPassword = "UPDATE " + Database.UTENTI + " SET " + Database.UTENTI_PASSWORD + " = '" + password1 + "' WHERE " + Database.UTENTI_EMAIL + " = '" + email + "';";
            SQLiteDatabase db = dbClass.getWritableDatabase();
            db.execSQL(aggiornaPassword);
            return true;
        }
        return false;
    }
}
