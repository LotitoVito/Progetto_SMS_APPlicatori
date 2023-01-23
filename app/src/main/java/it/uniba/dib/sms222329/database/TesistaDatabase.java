package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.Tesista;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;

public class TesistaDatabase {

    public static boolean RegistrazioneTesista(Tesista tesista, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesista = new ContentValues();

        Cursor idUtente = dbClass.RicercaDato("SELECT id FROM utenti WHERE email = '" + tesista.getEmail() + "';");
        idUtente.moveToNext();

        cvTesista.put("utente_id", idUtente.getString(0));
        cvTesista.put("matricola", tesista.getMatricola());
        cvTesista.put("media_voti", tesista.getMedia());
        cvTesista.put("esami_mancanti", tesista.getNumeroEsamiMancanti());
        cvTesista.put("universitacorso_id", tesista.getIdUniversitaCorso());

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

    public static boolean modTesista(Tesista tesista, Database dbClass) {

        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put("nome", tesista.getNome());
        cvUtente.put("cognome", tesista.getCognome());
        cvUtente.put("email", tesista.getEmail());
        cvUtente.put("password", tesista.getPassword());
        cvUtente.put("codice_fiscale", tesista.getCodiceFiscale());

        try{
            long updateUtente = db.update("utenti", cvUtente, "id = " + tesista.getIdUtente(), null);
            if(updateUtente != -1){
                ContentValues cvTesista = new ContentValues();

                cvTesista.put("matricola", tesista.getMatricola());
                cvTesista.put("media_voti", tesista.getMedia());
                cvTesista.put("esami_mancanti", tesista.getNumeroEsamiMancanti());
                cvTesista.put("universitacorso_id", tesista.getIdUniversitaCorso());

                long updateTesista = db.update("tesista", cvTesista, "id = " + tesista.getIdTesista(), null);
                return updateTesista != -1;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static Tesista IstanziaTesista(UtenteRegistrato account, Database dbClass){
        Tesista TesistaLog = new Tesista();

        String query =  "SELECT u.id, t.id, matricola, nome, cognome, email, password, media_voti, esami_mancanti, universitacorso_id" +
                " FROM utenti u, tesista t WHERE u.id=t.utente_id AND email = '" + account.getEmail() + "';";
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
        //Tesi scelta, codice fiscale

        return TesistaLog;
    }
}
