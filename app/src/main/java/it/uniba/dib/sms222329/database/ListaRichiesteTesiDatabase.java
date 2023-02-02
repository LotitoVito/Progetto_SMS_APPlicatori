package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.classi.RichiestaTesi;

public class ListaRichiesteTesiDatabase {

    public static List<RichiestaTesi> ListaRichiesteTesi(Database dbClass) {
        String query = "SELECT * FROM " + Database.RICHIESTA + " WHERE " + Database.RICHIESTA_ACCETTATA + "=" + RichiestaTesi.IN_ATTESA + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<RichiestaTesi> listaRichiesteTesi = new ArrayList<RichiestaTesi>();

        while (cursore.moveToNext()) {
            RichiestaTesi richiestaTesi = new RichiestaTesi();

            richiestaTesi.setIdRichiesta(cursore.getInt(0));
            richiestaTesi.setMessaggio(cursore.getString(1));
            richiestaTesi.setCapacitàStudente(cursore.getString(2));
            richiestaTesi.setIdTesi(cursore.getInt(3));
            richiestaTesi.setIdTesista(cursore.getInt(4));
            richiestaTesi.setAccettata(cursore.getInt(5));
            richiestaTesi.setRisposta(cursore.getString(6));

            listaRichiesteTesi.add(richiestaTesi);

        }
        return listaRichiesteTesi;
    }
}
