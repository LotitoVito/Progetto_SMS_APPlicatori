package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.classi.TesiScelta;

public class TesiSceltaDatabase {

    public static boolean RegistrazioneTesiScelta(TesiScelta tesiScelta, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesiScelta = new ContentValues();

        cvTesiScelta.put(Database.TESISCELTA_TESIID, tesiScelta.getIdTesi());
        cvTesiScelta.put(Database.TESISCELTA_TESISTAID, tesiScelta.getIdTesista());

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

    public static boolean AccettaRichiesta(Database dbClass, TesiScelta tesi){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues tesiSceltaCv = new ContentValues();

        tesiSceltaCv.put(Database.TESISCELTA_CORELATOREID, tesi.getStatoCorelatore());

        long updateTesiScelta = db.update(Database.TESISCELTA, tesiSceltaCv, Database.TESISCELTA_ID + "=" + tesi.getIdTesiScelta(), null);
        if(updateTesiScelta != -1){
            return true;
        }
        return false;
    }

    public static boolean RifiutaRichiesta(Database dbClass, TesiScelta tesi){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues tesiSceltaCv = new ContentValues();

        tesiSceltaCv.put(Database.TESISCELTA_CORELATOREID, tesi.getStatoCorelatore());

        long updateTesiScelta = db.update(Database.TESISCELTA, tesiSceltaCv, Database.TESISCELTA_ID + "=" + tesi.getIdTesiScelta(), null);
        if(updateTesiScelta != -1){
            return true;
        }
        return false;
    }

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
}
