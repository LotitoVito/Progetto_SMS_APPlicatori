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

            tesiSceltaEstratta.setIdTesiScelta(cursore.getInt(0));
            //tesiSceltaEstratta.setDataPubblicazione(cursore.getString(1));
            tesiSceltaEstratta.setRiassunto(cursore.getString(2));
            //tesiSceltaEstratta.setDownload(cursore.getBlob(3));
            tesiSceltaEstratta.setIdTesi(cursore.getInt(4));
            tesiSceltaEstratta.setIdCorelatore(cursore.getInt(5));
            tesiSceltaEstratta.setIdTesista(cursore.getInt(6));

            listaTesiScelte.add(tesiSceltaEstratta);
        }
        return listaTesiScelte;
    }

}
