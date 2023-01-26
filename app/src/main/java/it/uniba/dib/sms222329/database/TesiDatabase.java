package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.Tesi;

public class TesiDatabase {

    public static boolean RegistrazioneTesi(Tesi tesi, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesi = new ContentValues();

        cvTesi.put(Database.TESI_TITOLO, tesi.getTitolo());
        cvTesi.put(Database.TESI_ARGOMENTO, tesi.getArgomenti());
        cvTesi.put(Database.TESI_TEMPISTICHE, tesi.getTempistiche());
        cvTesi.put(Database.TESI_MEDIAVOTOMINIMA, tesi.getMediaVotiMinima());
        cvTesi.put(Database.TESI_ESAMINECESSARI, tesi.getEsamiMancantiNecessari());
        cvTesi.put(Database.TESI_SKILLRICHIESTE, tesi.getCapacitaRichieste());
        cvTesi.put(Database.TESI_STATO, tesi.getStatoDisponibilita());
        cvTesi.put(Database.TESI_VISUALIZZAZIONI, tesi.getNumeroVisualizzazioni());
        cvTesi.put(Database.TESI_RELATOREID, tesi.getIdRelatore());

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

    public static boolean ModificaTesi(Tesi tesi, Database dbClass){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesi = new ContentValues();

        cvTesi.put(Database.TESI_TITOLO, tesi.getTitolo());
        cvTesi.put(Database.TESI_ARGOMENTO, tesi.getArgomenti());
        cvTesi.put(Database.TESI_TEMPISTICHE, tesi.getTempistiche());
        cvTesi.put(Database.TESI_MEDIAVOTOMINIMA, tesi.getMediaVotiMinima());
        cvTesi.put(Database.TESI_ESAMINECESSARI, tesi.getEsamiMancantiNecessari());
        cvTesi.put(Database.TESI_SKILLRICHIESTE, tesi.getCapacitaRichieste());
        cvTesi.put(Database.TESI_STATO, tesi.getStatoDisponibilita());
        try{
            long updateTesi = db.update(Database.TESI, cvTesi, Database.TESI_ID + " = " + tesi.getId(), null);
            if(updateTesi != -1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static Cursor visualizzaTesi(Database dbClass, int id){
        SQLiteDatabase db=dbClass.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Database.TESI + " WHERE " + Database.TESI_ID + "='" + id + "';", null);
        return cursor;
    }

    public static void incrementaVisualizzazioni(Tesi tesi, Database dbClass){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Database.TESI_VISUALIZZAZIONI, tesi.getNumeroVisualizzazioni());
        db.update(Database.TESI, cv, Database.TESI_ID + " = '"+ tesi.getId() +"';", null);
    }
}
