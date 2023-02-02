package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.classi.Tesista;

public class ListaTesistiDatabase {

    public static List<Tesista> ListaTesisti(Database dbClass) {
        String query = "SELECT * FROM " + Database.TESISTA + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Tesista> listaTesisti = new ArrayList<Tesista>();

        while (cursore.moveToNext()) {
            Tesista tesistaEstratto = new Tesista();

            tesistaEstratto.setIdTesista(cursore.getInt(0));
            tesistaEstratto.setMatricola(cursore.getString(1));
            tesistaEstratto.setMedia(cursore.getFloat(2));
            tesistaEstratto.setNumeroEsamiMancanti(cursore.getInt(3));
            tesistaEstratto.setIdUtente(cursore.getInt(4));
            tesistaEstratto.setIdUniversitaCorso(cursore.getInt(5));

            listaTesisti.add(tesistaEstratto);

        }
        return listaTesisti;
    }

}
