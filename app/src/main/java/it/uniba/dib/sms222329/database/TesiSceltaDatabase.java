package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.database.DatabaseReference;

import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.classi.TesiScelta;

public class TesiSceltaDatabase {

    /**
     * Metodo usato per registrare su database una tesi scelta nel momento in cui il relatore accetta la richiesta
     * @param tesiScelta
     * @param dbClass
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean RegistrazioneTesiScelta(TesiScelta tesiScelta, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesiScelta = new ContentValues();

        cvTesiScelta.put(Database.TESISCELTA_TESIID, tesiScelta.getIdTesi());
        cvTesiScelta.put(Database.TESISCELTA_TESISTAID, tesiScelta.getIdTesista());
        cvTesiScelta.put(Database.TESISCELTA_CAPACITATESISTA, tesiScelta.getCapacitàStudente());

        try{
            long insertTesi = db.insert(Database.TESISCELTA, null, cvTesiScelta);
            if(insertTesi != -1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metodo usato dal relatore per inviare una proposta ad un corelatore di aggiungersi ad una tesi scelta speicifica
     * @param dbClass
     * @param tesi
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean AggiungiCorelatore(Database dbClass, TesiScelta tesi){
        SQLiteDatabase db = dbClass.getWritableDatabase();

        ContentValues tesiSceltaCv = new ContentValues();
        tesiSceltaCv.put(Database.TESISCELTA_CORELATOREID, tesi.getIdCorelatore());
        tesiSceltaCv.put(Database.TESISCELTA_STATOCORELATORE, tesi.getStatoCorelatore());

        long updateTesiScelta = db.update(Database.TESISCELTA, tesiSceltaCv, Database.TESISCELTA_ID + "=" + tesi.getIdTesiScelta(), null);
        if(updateTesiScelta != -1){
            return true;
        }
        return false;
    }

    /**
     * Metodo usato dal relatore per rimuovere un corelatore da una tesi scelta specifica
     * @param dbClass
     * @param tesi
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean RimuoviCorelatore(Database dbClass, TesiScelta tesi){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues tesiSceltaCv = new ContentValues();

        tesiSceltaCv.put(Database.TESISCELTA_CORELATOREID, tesi.getIdCorelatore());
        tesiSceltaCv.put(Database.TESISCELTA_STATOCORELATORE, tesi.getStatoCorelatore());

        long updateTesiScelta = db.update(Database.TESISCELTA, tesiSceltaCv, Database.TESISCELTA_ID + "=" + tesi.getIdTesiScelta(), null);
        if(updateTesiScelta != -1){
            return true;
        }
        return false;
    }

    /**
     * Metodo usato dal corelatore per accettare una richiesta di far parte di una tesi scelta specifica
     * @param dbClass
     * @param tesi
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean AccettaRichiesta(Database dbClass, TesiScelta tesi){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues tesiSceltaCv = new ContentValues();

        tesiSceltaCv.put(Database.TESISCELTA_STATOCORELATORE, tesi.getStatoCorelatore());

        long updateTesiScelta = db.update(Database.TESISCELTA, tesiSceltaCv, Database.TESISCELTA_ID + "=" + tesi.getIdTesiScelta(), null);
        if(updateTesiScelta != -1){
            return true;
        }
        return false;
    }

    /**
     * Metodo usato dal corelatore per rifiutare una richiesta di far parte di una tesi scelta specifica
     * @param dbClass
     * @param tesi
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean RifiutaRichiesta(Database dbClass, TesiScelta tesi){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues tesiSceltaCv = new ContentValues();

        tesiSceltaCv.put(Database.TESISCELTA_STATOCORELATORE, tesi.getStatoCorelatore());

        long updateTesiScelta = db.update(Database.TESISCELTA, tesiSceltaCv, Database.TESISCELTA_ID + "=" + tesi.getIdTesiScelta(), null);
        if(updateTesiScelta != -1){
            return true;
        }
        return false;
    }

    /**
     * Metodo usato dal tesista per consegnare una tesi scelta specifica
     * @param dbClass
     * @param tesi
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean ConsegnaTesiScelta(Database dbClass, TesiScelta tesi){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues tesiSceltaCv = new ContentValues();

        tesiSceltaCv.put(Database.TESISCELTA_ABSTRACT, tesi.getRiassunto());
        tesiSceltaCv.put(Database.TESISCELTA_DATAPUBBLICAZIONE, String.valueOf(tesi.getDataPubblicazione()));

        long updateTesiScelta = db.update(Database.TESISCELTA, tesiSceltaCv, Database.TESISCELTA_ID + "=" + tesi.getIdTesiScelta(), null);
        if(updateTesiScelta != -1){
            return true;
        }
        return false;
    }

    /**
     * Metodo usato per registrare l'upload del file pdf di una tesi scelta speicifica su database
     * @param dbClass
     * @param tesi
     * @param valore
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean UploadTesiScelta(Database dbClass, TesiScelta tesi, String valore){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues tesiSceltaCv = new ContentValues();

        tesiSceltaCv.put(Database.TESISCELTA_DOWNLOAD, valore);

        long updateTesiScelta = db.update(Database.TESISCELTA, tesiSceltaCv, Database.TESISCELTA_ID + "=" + tesi.getIdTesiScelta(), null);
        if(updateTesiScelta != -1){
            return true;
        }
        return false;
    }

    /**
     * Metodo usato per recuperare il link del file pdf salvato su firebase di una tesi scelta speicifica su database
     * @param dbClass
     * @param tesiScelta
     * @return
     */
    public static String DownloadTesiScelta(Database dbClass, TesiScelta tesiScelta){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + Database.TESISCELTA_DOWNLOAD +
                " FROM " + Database.TESISCELTA +
                " WHERE " + Database.TESISCELTA_ID + "=" + tesiScelta.getIdTesiScelta() + ";", null);
        cursor.moveToFirst();
        String key = cursor.getString(cursor.getColumnIndexOrThrow(Database.TESISCELTA_DOWNLOAD));
        if(key == null) {
            key = "Empty";
        }
        return key;
    }
}
