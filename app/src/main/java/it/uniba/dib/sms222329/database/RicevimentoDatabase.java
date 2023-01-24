package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.Ricevimento;

public class RicevimentoDatabase {

    public static boolean RichiestaRicevimento(Database dbClass, Ricevimento richiesta) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues richiestaCv = new ContentValues();

        richiestaCv.put("data", String.valueOf(richiesta.getData()));
        richiestaCv.put("orario", String.valueOf(richiesta.getOrario()));
        richiestaCv.put("argomento", richiesta.getArgomento());
        richiestaCv.put("task_id", richiesta.getIdTask());
        richiestaCv.put("accettazione", 2);
        richiestaCv.put("messaggio", richiesta.getMessaggio());

        long insertRichiesta = db.insert("ricevimenti", null, richiestaCv);
        if(insertRichiesta != -1){
            return true;
        }
        return false;
    }

    public static boolean AccettaRicevimento(Database dbClass, Ricevimento ricevimento){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues ricevimentoCv = new ContentValues();

        ricevimentoCv.put("accettazione", 1);

        long updateRicevimento = db.update("ricevimenti", ricevimentoCv, "id = '"+ ricevimento.getIdRicevimento() +"';", null);
        if(updateRicevimento != -1){
            return true;
        }
        return false;
    }

    public static boolean RifiutaRicevimento(Database dbClass, Ricevimento ricevimento){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues ricevimentoCv = new ContentValues();

        ricevimentoCv.put("accettazione", 0);

        long updateRicevimento = db.update("ricevimenti", ricevimentoCv, "id = '"+ ricevimento.getIdRicevimento() +"';", null);
        if(updateRicevimento != -1){
            return true;
        }
        return false;
    }

    public static boolean ModificaRicevimento(Database dbClass, Ricevimento ricevimento){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues ricevimentoCv = new ContentValues();

        ricevimentoCv.put("data", String.valueOf(ricevimento.getData()));
        ricevimentoCv.put("orario", String.valueOf(ricevimento.getOrario()));
        ricevimentoCv.put("accettazione", 2);

        long updateRicevimento = db.update("ricevimenti", ricevimentoCv, "id = '"+ ricevimento.getIdRicevimento() +"';", null);
        if(updateRicevimento != -1){
            return true;
        }
        return false;
    }
}
