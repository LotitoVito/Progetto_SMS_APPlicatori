package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
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
}
