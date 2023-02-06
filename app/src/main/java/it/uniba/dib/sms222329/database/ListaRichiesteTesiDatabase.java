package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.classi.RichiestaTesi;

public class ListaRichiesteTesiDatabase {

    public static List<RichiestaTesi> ListaRichiesteTesiRelatore(Database dbClass, int idRelatore) {
        String query = "SELECT * FROM " + Database.RICHIESTA + " " +
                "WHERE " + Database.RICHIESTA_ACCETTATA + "=" + RichiestaTesi.IN_ATTESA + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<RichiestaTesi> listaRichiesteTesi = new ArrayList<RichiestaTesi>();

        while (cursore.moveToNext()) {
            RichiestaTesi richiestaTesi = new RichiestaTesi();

            richiestaTesi.setIdRichiesta(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICHIESTA_ID)));
            richiestaTesi.setMessaggio(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICHIESTA_MESSAGGIO)));
            richiestaTesi.setCapacitàStudente(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICHIESTA_CAPACITASTUDENTE)));
            richiestaTesi.setIdTesi(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICHIESTA_TESIID)));
            richiestaTesi.setIdTesista(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICHIESTA_TESISTAID)));
            richiestaTesi.setAccettata(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICHIESTA_ACCETTATA)));
            richiestaTesi.setRisposta(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICHIESTA_RISPOSTA)));

            listaRichiesteTesi.add(richiestaTesi);

        }
        return listaRichiesteTesi;
    }

    public static List<RichiestaTesi> ListaRichiesteTesiTesista(Database dbClass, int idTesista) {
        String query = "SELECT * FROM " + Database.RICHIESTA + " " +
                "WHERE " + Database.RICHIESTA_TESISTAID + "=" + idTesista + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<RichiestaTesi> listaRichiesteTesi = new ArrayList<RichiestaTesi>();

        while (cursore.moveToNext()) {
            RichiestaTesi richiestaTesi = new RichiestaTesi();

            richiestaTesi.setIdRichiesta(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICHIESTA_ID)));
            richiestaTesi.setMessaggio(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICHIESTA_MESSAGGIO)));
            richiestaTesi.setCapacitàStudente(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICHIESTA_CAPACITASTUDENTE)));
            richiestaTesi.setIdTesi(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICHIESTA_TESIID)));
            richiestaTesi.setIdTesista(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICHIESTA_TESISTAID)));
            richiestaTesi.setAccettata(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICHIESTA_ACCETTATA)));
            richiestaTesi.setRisposta(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICHIESTA_RISPOSTA)));

            listaRichiesteTesi.add(richiestaTesi);

        }
        return listaRichiesteTesi;
    }
}
