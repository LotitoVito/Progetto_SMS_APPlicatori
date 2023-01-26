package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.CoRelatore;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;

public class CoRelatoreDatabase {

    public static boolean RegistrazioneCoRelatore(CoRelatore corelatore, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvCoRelatore = new ContentValues();

        Cursor idUtente = dbClass.RicercaDato("SELECT " + Database.UTENTI_ID + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_EMAIL + " = '" + corelatore.getEmail() + "';");
        idUtente.moveToNext();

        cvCoRelatore.put(Database.CORELATORE_UTENTEID, idUtente.getString(0));
        cvCoRelatore.put(Database.CORELATORE_ORGANIZZAZIONE, corelatore.getOrganizzazione());

        try{
            long insertCoRelatore = db.insert(Database.CORELATORE, null, cvCoRelatore);
            if(insertCoRelatore != -1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean modCoRelatore(CoRelatore corelatore, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put(Database.UTENTI_NOME, corelatore.getNome());
        cvUtente.put(Database.UTENTI_COGNOME, corelatore.getCognome());
        cvUtente.put(Database.UTENTI_EMAIL, corelatore.getEmail());
        cvUtente.put(Database.UTENTI_PASSWORD, corelatore.getPassword());
        cvUtente.put(Database.UTENTI_CODICEFISCALE, corelatore.getCodiceFiscale());

        try {
            long updateUtente = db.update(Database.UTENTI, cvUtente, Database.UTENTI_ID + " = " + corelatore.getIdUtente() + ";", null);

            ContentValues cvCoRel= new ContentValues();
            cvCoRel.put(Database.CORELATORE_ORGANIZZAZIONE, corelatore.getOrganizzazione());
            long updateCoRel = db.update(Database.CORELATORE, cvCoRel, Database.CORELATORE_ID + " = " + corelatore.getIdCorelatore() + ";", null);

            return updateUtente != -1 && updateCoRel !=-1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static CoRelatore IstanziaCoRelatore(UtenteRegistrato account, Database dbClass){
        CoRelatore CorelatoreLog = new CoRelatore();

        String query =  "SELECT u." + Database.UTENTI_ID + ", c." + Database.CORELATORE_ID + ", " + Database.UTENTI_NOME + ", " + Database.UTENTI_COGNOME + ", " + Database.UTENTI_EMAIL + ", " + Database.UTENTI_PASSWORD + ", " + Database.CORELATORE_ORGANIZZAZIONE + ", " + Database.UTENTI_CODICEFISCALE + " " +
                        "FROM " + Database.UTENTI + " u, " + Database.CORELATORE + " c " +
                        "WHERE u." + Database.UTENTI_ID + "=c." + Database.CORELATORE_UTENTEID + " AND " + Database.UTENTI_EMAIL + " = '" + account.getEmail() + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        cursore.moveToNext();

        CorelatoreLog.setIdUtente(cursore.getInt(0));
        CorelatoreLog.setIdCorelatore(cursore.getInt(1));
        CorelatoreLog.setNome(cursore.getString(2));
        CorelatoreLog.setCognome(cursore.getString(3));
        CorelatoreLog.setEmail(cursore.getString(4));
        CorelatoreLog.setPassword(cursore.getString(5));
        CorelatoreLog.setOrganizzazione(cursore.getString(6));
        CorelatoreLog.setCodiceFiscale(cursore.getString(7));

        return CorelatoreLog;
    }
}
