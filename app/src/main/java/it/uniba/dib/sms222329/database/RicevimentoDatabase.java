package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.Ricevimento;

public class RicevimentoDatabase {

    public static boolean RichiestaRicevimento(Database dbClass, Ricevimento richiesta) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues richiestaCv = new ContentValues();

        richiestaCv.put(Database.RICEVIMENTI_DATA, String.valueOf(richiesta.getData()));
        richiestaCv.put(Database.RICEVIMENTI_ORARIO, String.valueOf(richiesta.getOrario()));
        richiestaCv.put(Database.RICEVIMENTI_ARGOMENTO, richiesta.getArgomento());
        richiestaCv.put(Database.RICEVIMENTI_TASKID, richiesta.getIdTask());
        richiestaCv.put(Database.RICEVIMENTI_ACCETTAZIONE, Ricevimento.IN_ATTESA_RELATORE);
        richiestaCv.put(Database.RICEVIMENTI_MESSAGGIO, richiesta.getMessaggio());

        long insertRichiesta = db.insert(Database.RICEVIMENTI, null, richiestaCv);
        if(insertRichiesta != -1){
            return true;
        }
        return false;
    }

    public static boolean AccettaRicevimento(Database dbClass, Ricevimento ricevimento){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues ricevimentoCv = new ContentValues();

        ricevimentoCv.put(Database.RICEVIMENTI_ACCETTAZIONE, Ricevimento.ACCETTATO);

        long updateRicevimento = db.update(Database.RICEVIMENTI, ricevimentoCv, Database.RICEVIMENTI_ID +" = '"+ ricevimento.getIdRicevimento() +"';", null);
        if(updateRicevimento != -1){
            return true;
        }
        return false;
    }

    public static boolean RifiutaRicevimento(Database dbClass, Ricevimento ricevimento){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues ricevimentoCv = new ContentValues();

        ricevimentoCv.put(Database.RICEVIMENTI_ACCETTAZIONE, Ricevimento.RIFIUTATO);

        long updateRicevimento = db.update(Database.RICEVIMENTI, ricevimentoCv, Database.RICEVIMENTI_ID +" = '"+ ricevimento.getIdRicevimento() +"';", null);
        if(updateRicevimento != -1){
            return true;
        }
        return false;
    }

    public static boolean ModificaRicevimento(Database dbClass, Ricevimento ricevimento){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues ricevimentoCv = new ContentValues();

        ricevimentoCv.put(Database.RICEVIMENTI_DATA, String.valueOf(ricevimento.getData()));
        ricevimentoCv.put(Database.RICEVIMENTI_ORARIO, String.valueOf(ricevimento.getOrario()));
        ricevimentoCv.put(Database.RICEVIMENTI_ACCETTAZIONE, Ricevimento.IN_ATTESA_TESISTA);

        long updateRicevimento = db.update(Database.RICEVIMENTI, ricevimentoCv, Database.RICEVIMENTI_ID +" = '"+ ricevimento.getIdRicevimento() +"';", null);
        if(updateRicevimento != -1){
            return true;
        }
        return false;
    }
}
