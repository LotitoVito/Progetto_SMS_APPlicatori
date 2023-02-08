package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import it.uniba.dib.sms222329.classi.RichiestaTesi;
import it.uniba.dib.sms222329.classi.TesiScelta;

public class RichiestaTesiDatabase {

    /**
     * Metodo usato per registrare su database una richiesta di tesi da parte del tesista
     * @param richiesta
     * @param dbClass
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean RichiestaTesi(RichiestaTesi richiesta, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRichiesta = new ContentValues();

        cvRichiesta.put(Database.RICHIESTA_MESSAGGIO, richiesta.getMessaggio());
        cvRichiesta.put(Database.RICHIESTA_CAPACITASTUDENTE, richiesta.getCapacitàStudente());
        cvRichiesta.put(Database.RICHIESTA_TESIID, richiesta.getIdTesi());
        cvRichiesta.put(Database.RICHIESTA_TESISTAID, richiesta.getIdTesista());
        cvRichiesta.put(Database.RICHIESTA_ACCETTATA, richiesta.getStato());

        long insertRichiesta = db.insert(Database.RICHIESTA, null, cvRichiesta);
        if(insertRichiesta != -1){
            return true;
        }
        return false;
    }

    /**
     * Metodo usato per accettare una richiesta di tesi su database da parte del relatore
     * @param risposta
     * @param dbClass
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean AccettaRichiestaTesi(RichiestaTesi risposta,Database dbClass){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRisposta = new ContentValues();

        //Log.d("test", String.valueOf(risposta.g));

        cvRisposta.put(Database.RICHIESTA_ACCETTATA, risposta.getStato());
        cvRisposta.put(Database.RICHIESTA_RISPOSTA, risposta.getRisposta());

        long updateRisposta = db.update(Database.RICHIESTA, cvRisposta, Database.RICHIESTA_ID + " = '"+ risposta.getIdRichiesta() +"';", null);
        if(updateRisposta != -1){

            TesiScelta tesiScelta = new TesiScelta(risposta.getIdTesi(), risposta.getIdTesista(), risposta.getCapacitàStudente());
            if(TesiSceltaDatabase.RegistrazioneTesiScelta(tesiScelta, dbClass)){
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo usato per rifiutare una richiesta di tesi su database da parte del relatore
     * @param risposta
     * @param dbClass
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean RifiutaRichiestaTesi(RichiestaTesi risposta,Database dbClass){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRisposta = new ContentValues();

        Log.d("test", String.valueOf(risposta.getStato()));

        cvRisposta.put(Database.RICHIESTA_ACCETTATA, risposta.getStato());
        cvRisposta.put(Database.RICHIESTA_RISPOSTA, risposta.getRisposta());

        long updateRisposta = db.update(Database.RICHIESTA, cvRisposta, Database.RICHIESTA_ID + " = '"+ risposta.getIdRichiesta() +"';", null);
        if(updateRisposta != -1){
            return true;
        }
        return false;
    }
}
