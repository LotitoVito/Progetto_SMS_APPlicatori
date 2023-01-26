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

        cvTesista.put(Database.TESISTA_UTENTEID, idUtente.getString(0));
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

        TesistaLog.setIdUtente(cursore.getInt(0));
        TesistaLog.setIdTesista(cursore.getInt(1));
        TesistaLog.setMatricola(cursore.getString(2));
        TesistaLog.setNome(cursore.getString(3));
        TesistaLog.setCognome(cursore.getString(4));
        TesistaLog.setEmail(cursore.getString(5));
        TesistaLog.setPassword(cursore.getString(6));
        TesistaLog.setMedia(cursore.getInt(7));
        TesistaLog.setNumeroEsamiMancanti(cursore.getInt(8));
        TesistaLog.setIdUniversitaCorso(cursore.getInt(9));
        TesistaLog.setCodiceFiscale(cursore.getString(10));

        return TesistaLog;
    }
}
