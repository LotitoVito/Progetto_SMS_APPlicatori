package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;

public class TesistaDatabase {

    public static boolean RegistrazioneTesista(Tesista tesista, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesista = new ContentValues();

        Cursor idUtente = dbClass.RicercaDato("SELECT " + Database.UTENTI_ID + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_EMAIL + " = '" + tesista.getEmail() + "';");
        idUtente.moveToNext();

        cvTesista.put(Database.TESISTA_UTENTEID, idUtente.getString(idUtente.getColumnIndexOrThrow(Database.UTENTI_ID)));
        cvTesista.put(Database.TESISTA_MATRICOLA, tesista.getMatricola());
        cvTesista.put(Database.TESISTA_MEDIAVOTI, tesista.getMedia());
        cvTesista.put(Database.TESISTA_ESAMIMANCANTI, tesista.getNumeroEsamiMancanti());
        cvTesista.put(Database.TESISTA_UNIVERSITACORSOID, tesista.getIdUniversitaCorso());

        try{
            long insertCoRelatore = db.insert(Database.TESISTA, null, cvTesista);
            if(insertCoRelatore != -1){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean modTesista(Tesista tesista, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put(Database.UTENTI_NOME, tesista.getNome());
        cvUtente.put(Database.UTENTI_COGNOME, tesista.getCognome());
        cvUtente.put(Database.UTENTI_EMAIL, tesista.getEmail());
        cvUtente.put(Database.UTENTI_PASSWORD, tesista.getPassword());
        cvUtente.put(Database.UTENTI_CODICEFISCALE, tesista.getCodiceFiscale());

        try{
            long updateUtente = db.update(Database.UTENTI, cvUtente, Database.UTENTI_ID + " = " + tesista.getIdUtente(), null);
            if(updateUtente != -1){
                ContentValues cvTesista = new ContentValues();

                cvTesista.put(Database.TESISTA_MATRICOLA, tesista.getMatricola());
                cvTesista.put(Database.TESISTA_MEDIAVOTI, tesista.getMedia());
                cvTesista.put(Database.TESISTA_ESAMIMANCANTI, tesista.getNumeroEsamiMancanti());
                cvTesista.put(Database.TESISTA_UNIVERSITACORSOID, tesista.getIdUniversitaCorso());

                long updateTesista = db.update(Database.TESISTA, cvTesista, Database.TESISTA_ID + " = " + tesista.getIdTesista(), null);
                return updateTesista != -1;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static Tesista IstanziaTesista(UtenteRegistrato account, Database dbClass){
        Tesista TesistaLog = new Tesista();

        String query =  "SELECT u." + Database.UTENTI_ID + ", t." + Database.TESISTA_ID + ", " + Database.TESISTA_MATRICOLA + ", " + Database.UTENTI_NOME + ", " + Database.UTENTI_COGNOME + ", " + Database.UTENTI_EMAIL + ", " + Database.UTENTI_PASSWORD + ", " + Database.TESISTA_MEDIAVOTI + ", " + Database.TESISTA_ESAMIMANCANTI + ", " + Database.TESISTA_UNIVERSITACORSOID + ", " + Database.UTENTI_CODICEFISCALE + " " +
                        "FROM " + Database.UTENTI + " u, " + Database.TESISTA + " t " +
                        "WHERE u." + Database.UTENTI_ID + "=t." + Database.TESISTA_UTENTEID + " AND " + Database.UTENTI_EMAIL + " = '" + account.getEmail() + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        cursore.moveToNext();

        TesistaLog.setIdUtente(cursore.getInt(cursore.getColumnIndexOrThrow(Database.UTENTI_ID)));
        TesistaLog.setIdTesista(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISTA_ID)));
        TesistaLog.setMatricola(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISTA_MATRICOLA)));
        TesistaLog.setNome(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_NOME)));
        TesistaLog.setCognome(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)));
        TesistaLog.setEmail(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_EMAIL)));
        TesistaLog.setPassword(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_PASSWORD)));
        TesistaLog.setMedia(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISTA_MEDIAVOTI)));
        TesistaLog.setNumeroEsamiMancanti(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISTA_ESAMIMANCANTI)));
        TesistaLog.setIdUniversitaCorso(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISTA_UNIVERSITACORSOID)));
        TesistaLog.setCodiceFiscale(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_CODICEFISCALE)));

        return TesistaLog;
    }
}
