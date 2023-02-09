package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.Tesi;

public class TesiDatabase {

    /**
     * Metodo usato dal relatore per registrare su database una nuova proposta di tesi
     * @param tesi
     * @param dbClass
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean RegistrazioneTesi(Tesi tesi, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesi = new ContentValues();

        cvTesi.put(Database.TESI_TITOLO, tesi.getTitolo());
        cvTesi.put(Database.TESI_ARGOMENTO, tesi.getArgomenti());
        cvTesi.put(Database.TESI_TEMPISTICHE, tesi.getTempistiche());
        cvTesi.put(Database.TESI_MEDIAVOTOMINIMA, tesi.getMediaVotiMinima());
        cvTesi.put(Database.TESI_ESAMINECESSARI, tesi.getEsamiNecessari());
        cvTesi.put(Database.TESI_SKILLRICHIESTE, tesi.getCapacitaRichieste());
        cvTesi.put(Database.TESI_STATO, tesi.getStatoDisponibilita());
        cvTesi.put(Database.TESI_VISUALIZZAZIONI, tesi.getNumeroVisualizzazioni());
        cvTesi.put(Database.TESI_RELATOREID, tesi.getIdRelatore());
        cvTesi.put(Database.TESI_UNIVERSITACORSOID, tesi.getIdUniversitaCorso());

        try{
            long insertTesi = db.insert(Database.TESI, null, cvTesi);
            if(insertTesi != -1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metodo usato dal relatore per modificare una proposta di tesi specifica
     * @param tesi
     * @param dbClass
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean ModificaTesi(Tesi tesi, Database dbClass){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesi = new ContentValues();

        cvTesi.put(Database.TESI_TITOLO, tesi.getTitolo());
        cvTesi.put(Database.TESI_ARGOMENTO, tesi.getArgomenti());
        cvTesi.put(Database.TESI_TEMPISTICHE, tesi.getTempistiche());
        cvTesi.put(Database.TESI_MEDIAVOTOMINIMA, tesi.getMediaVotiMinima());
        cvTesi.put(Database.TESI_ESAMINECESSARI, tesi.getEsamiNecessari());
        cvTesi.put(Database.TESI_SKILLRICHIESTE, tesi.getCapacitaRichieste());
        cvTesi.put(Database.TESI_STATO, tesi.getStatoDisponibilita());
        cvTesi.put(Database.TESI_UNIVERSITACORSOID, tesi.getIdUniversitaCorso());
        try{
            long updateTesi = db.update(Database.TESI, cvTesi, Database.TESI_ID + " = " + tesi.getIdTesi(), null);
            if(updateTesi != -1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metodo usato per incrementare il numero di visualizzazione su database di una proposta di tesi
     * @param tesi
     * @param dbClass
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean incrementaVisualizzazioni(Tesi tesi, Database dbClass){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Database.TESI_VISUALIZZAZIONI, tesi.getNumeroVisualizzazioni());
        try {
            long updateTesi = db.update(Database.TESI, cv, Database.TESI_ID + " = '"+ tesi.getIdTesi() +"';", null);
            if (updateTesi != -1){
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
