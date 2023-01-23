package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.Tesi;

public class TesiDatabase {

    public static boolean RegistrazioneTesi(Tesi tesi, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesi = new ContentValues();

        cvTesi.put("titolo", tesi.getTitolo());
        cvTesi.put("argomento", tesi.getArgomenti());
        cvTesi.put("tempistiche", tesi.getTempistiche());
        cvTesi.put("media_voto_minima", tesi.getMediaVotiMinima());
        cvTesi.put("esami_necessari", tesi.getEsamiMancantiNecessari());
        cvTesi.put("skill_richieste", tesi.getCapacitaRichieste());
        cvTesi.put("stato", tesi.getStatoDisponibilita());
        cvTesi.put("visualizzazioni", tesi.getNumeroVisualizzazioni());
        cvTesi.put("relatore_id", tesi.getIdRelatore());

        try{
            long insertTesi = db.insert("tesi", null, cvTesi);
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

        cvTesi.put("titolo", tesi.getTitolo());
        cvTesi.put("argomento", tesi.getArgomenti());
        cvTesi.put("stato", tesi.getStatoDisponibilita());
        cvTesi.put("tempistiche", tesi.getTempistiche());
        cvTesi.put("media_voto_minima", tesi.getMediaVotiMinima());
        cvTesi.put("esami_necessari", tesi.getEsamiMancantiNecessari());
        cvTesi.put("skill_richieste", tesi.getCapacitaRichieste());

        try{
            long updateTesi = db.update("Tesi", cvTesi, "IDTesi = " + tesi.getId(), null);
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
        Cursor cursor = db.rawQuery("SELECT * FROM tesi WHERE id='"+id+"';", null);
        return cursor;
    }

    public static void incrementaVisualizzazioni(Tesi tesi, Database dbClass){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("visualizzazioni", tesi.getNumeroVisualizzazioni());
        db.update("tesi", cv, "id = '"+ tesi.getId() +"';", null);
    }
}
