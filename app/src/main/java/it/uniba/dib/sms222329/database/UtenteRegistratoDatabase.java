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

        cvUtente.put("nome", tesista.getNome());
        cvUtente.put("cognome", tesista.getCognome());
        cvUtente.put("codice_fiscale", tesista.getCodiceFiscale());
        cvUtente.put("email", tesista.getEmail());
        cvUtente.put("password", tesista.getPassword());
        cvUtente.put("ruolo_id", tesista.getTipoUtente());

        try{
            long insertUtente = db.insert("utenti", null, cvUtente);
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

        cvUtente.put("nome", relatore.getNome());
        cvUtente.put("cognome", relatore.getCognome());
        cvUtente.put("codice_fiscale", relatore.getCodiceFiscale());
        cvUtente.put("email", relatore.getEmail());
        cvUtente.put("password", relatore.getPassword());
        cvUtente.put("ruolo_id", relatore.getTipoUtente());

        try{
            long insertUtente = db.insert("utenti", null, cvUtente);
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

        cvUtente.put("nome", coRelatore.getNome());
        cvUtente.put("cognome", coRelatore.getCognome());
        cvUtente.put("codice_fiscale", coRelatore.getCodiceFiscale());
        cvUtente.put("email", coRelatore.getEmail());
        cvUtente.put("password", coRelatore.getPassword());
        cvUtente.put("ruolo_id", coRelatore.getTipoUtente());

        try{
            long insertUtente = db.insert("utenti", null, cvUtente);
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
        String query = "SELECT ruolo_id FROM utenti WHERE email = '" + account.getEmail() + "' AND password = '" + account.getPassword() + "';";
        Cursor cursore = db.rawQuery(query, null);

        if (cursore.getCount() != 0) {
            cursore.moveToNext();
            account.setTipoUtente(cursore.getInt(0)); //nel select puoi farti restituire piu valori, mettendo 0 restituisce solom il primo valore della prima colonna, è come un array
            return true;
        }
        return false;
    }

    public static boolean ControlloMail(String email1, String email2, Database dbClass){     //ritorna vero se le email corrispondono e se c'è corrispondenza nel db
        String query = "SELECT email FROM utenti WHERE email = '" + email1 + "';";
        if (email1.equals(email2) && dbClass.VerificaDatoEsistente(query)){
            Log.d("email1", email1);
            Log.d("email2", email2);

            return true;
        }
        return false;
    }

    public static boolean ResetPassword(String password1, String password2, String email, Database dbClass){
        if(password1.equals(password2)){
            String aggiornaPassword = "UPDATE utenti SET password = '" + password1 + "' WHERE email = '" + email + "';";
            SQLiteDatabase db = dbClass.getWritableDatabase();
            db.execSQL(aggiornaPassword);
            return true;
        }
        return false;
    }
}
