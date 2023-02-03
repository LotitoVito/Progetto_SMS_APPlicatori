package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.classi.TesiScelta;

public class ListaTesiScelteDatabase {

    public static List<TesiScelta> ListaTesiScelteDatabase(Database dbClass) {
        String query = "SELECT * FROM " + Database.TESISCELTA + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        List<TesiScelta> listaTesiScelte = new ArrayList<TesiScelta>();

        while (cursore.moveToNext()) {
            TesiScelta tesiSceltaEstratta = new TesiScelta();

            tesiSceltaEstratta.setIdTesiScelta(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_ID)));
            //tesiSceltaEstratta.setDataPubblicazione(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_DATAPUBBLICAZIONE)));
            tesiSceltaEstratta.setRiassunto(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_ABSTRACT)));
            //tesiSceltaEstratta.setDownload(cursore.getBlob(3));
            tesiSceltaEstratta.setIdTesi(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_TESIID)));
            tesiSceltaEstratta.setIdCorelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_CORELATOREID)));
            tesiSceltaEstratta.setIdTesista(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_TESISTAID)));

            listaTesiScelte.add(tesiSceltaEstratta);
        }
        return listaTesiScelte;
    }

}
