package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.classi.Tesista;

public class RichiestaTesiDatabase {

    public static boolean RichiestaTesi(Database dbClass, RichiestaTesi richiesta) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRichiesta = new ContentValues();

        cvRichiesta.put("messaggio", richiesta.getMessaggio());
        cvRichiesta.put("tesi_id", richiesta.getIdTesi());
        cvRichiesta.put("tesista_id", richiesta.getIdTesista());

        long insertRichiesta = db.insert("richiesta", null, cvRichiesta);
        if(insertRichiesta != -1){
            return true;
        }
        return false;
    }

    public static boolean RispostaRichiestaTesi(Database dbClass, RichiestaTesi risposta){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRisposta = new ContentValues();

        cvRisposta.put("accettata", risposta.isAccettata());
        cvRisposta.put("risposta", risposta.getRisposta());

        long updateRisposta = db.update("richiesta", cvRisposta, "id = '"+ risposta.getIdRichiesta() +"';", null);
        if(updateRisposta != -1){
            return true;
        }
        return false;
    }
}
