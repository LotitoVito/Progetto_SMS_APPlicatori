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

        Cursor idUtente = dbClass.RicercaDato("SELECT id FROM utenti WHERE email = '" + corelatore.getEmail() + "';");
        idUtente.moveToNext();

        cvCoRelatore.put("utente_id", idUtente.getString(0));
        cvCoRelatore.put("organizzazione", corelatore.getOrganizzazione());

        try{
            long insertCoRelatore = db.insert("coRelatore", null, cvCoRelatore);
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

        cvUtente.put("nome", corelatore.getNome());
        cvUtente.put("cognome", corelatore.getCognome());
        cvUtente.put("email", corelatore.getEmail());
        cvUtente.put("password", corelatore.getPassword());
        cvUtente.put("codice_fiscale", corelatore.getCodiceFiscale());

        try {
            long updateUtente = db.update("utenti", cvUtente, "id = " + corelatore.getIdUtente(), null);

            ContentValues cvCoRel= new ContentValues();
            cvCoRel.put("organizzazione", corelatore.getOrganizzazione());
            long updateCoRel = db.update("utenti", cvCoRel, "id = " + corelatore.getIdCorelatore(), null);

            return updateUtente != -1 && updateCoRel !=-1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static CoRelatore IstanziaCoRelatore(UtenteRegistrato account, Database dbClass){
        CoRelatore CorelatoreLog = new CoRelatore();

        String query =  "SELECT u.id, c.id, nome, cognome, email, password, organizzazione, codice_fiscale " +
                "FROM utenti u, Corelatore c WHERE u.id=c.utente_id AND email = '" + account.getEmail() + "';";
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
