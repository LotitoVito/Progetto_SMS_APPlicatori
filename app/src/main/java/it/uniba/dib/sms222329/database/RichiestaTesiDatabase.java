package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.classi.Tesista;

public class RichiestaTesiDatabase {

    public static boolean RichiestaTesi(RichiestaTesi richiesta, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRichiesta = new ContentValues();

        cvRichiesta.put(Database.RICHIESTA_MESSAGGIO, richiesta.getMessaggio());
        cvRichiesta.put(Database.RICHIESTA_TESIID, richiesta.getIdTesi());
        cvRichiesta.put(Database.RICHIESTA_TESISTAID, richiesta.getIdTesista());

        long insertRichiesta = db.insert(Database.RICHIESTA, null, cvRichiesta);
        if(insertRichiesta != -1){
            return true;
        }
        return false;
    }

    public static boolean AccettaRichiestaTesi(RichiestaTesi risposta,Database dbClass){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRisposta = new ContentValues();

        cvRisposta.put(Database.RICHIESTA_ACCETTATA, risposta.getAccettata());
        cvRisposta.put(Database.RICHIESTA_RISPOSTA, risposta.getRisposta());

        long updateRisposta = db.update(Database.RICHIESTA, cvRisposta, Database.RICHIESTA_ID + " = '"+ risposta.getIdRichiesta() +"';", null);
        if(updateRisposta != -1){

            TesiScelta tesiScelta = new TesiScelta(risposta.getIdTesi(), risposta.getIdTesista());
            if(TesiSceltaDatabase.RegistrazioneTesiScelta(tesiScelta, dbClass)){
                return true;
            }
        }
        return false;
    }

    public static boolean RifiutaRichiestaTesi(RichiestaTesi risposta,Database dbClass){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRisposta = new ContentValues();

        cvRisposta.put(Database.RICHIESTA_ACCETTATA, risposta.getAccettata());
        cvRisposta.put(Database.RICHIESTA_RISPOSTA, risposta.getRisposta());

        long updateRisposta = db.update(Database.RICHIESTA, cvRisposta, Database.RICHIESTA_ID + " = '"+ risposta.getIdRichiesta() +"';", null);
        if(updateRisposta != -1){
            return true;
        }
        return false;
    }
}
