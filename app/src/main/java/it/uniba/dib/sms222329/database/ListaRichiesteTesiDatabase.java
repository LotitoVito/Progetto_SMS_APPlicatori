package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.classi.RichiestaTesi;

public class ListaRichiesteTesiDatabase {

    /**
     * Metodo usato per ricercare le richieste di tesi fatte dai tesisti per il relatore loggato
     * @param dbClass
     * @param idRelatore
     * @return  Restituisce la lista trovata
     */
    public static List<RichiestaTesi> ListaRichiesteTesiRelatore(Database dbClass, int idRelatore) {
        String query = "SELECT r." + Database.RICHIESTA_ID + ", r." + Database.RICHIESTA_MESSAGGIO + ", r." + Database.RICHIESTA_CAPACITASTUDENTE + ", r." + Database.RICHIESTA_TESIID + ", r." + Database.RICHIESTA_TESISTAID + ", r." + Database.RICHIESTA_ACCETTATA + ", r." + Database.RICHIESTA_RISPOSTA +
                " FROM " + Database.RICHIESTA + " r, " + Database.TESI + " t " +
                "WHERE r." + Database.RICHIESTA_TESIID + "=t." + Database.TESI_ID+
                " AND r." + Database.RICHIESTA_ACCETTATA + "=" + RichiestaTesi.IN_ATTESA + " AND t." + Database.TESI_RELATOREID + "=" + idRelatore + ";";

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
            richiestaTesi.setStato(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICHIESTA_ACCETTATA)));
            richiestaTesi.setRisposta(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICHIESTA_RISPOSTA)));

            listaRichiesteTesi.add(richiestaTesi);
        }
        return listaRichiesteTesi;
    }

    /**
     * Metodo usato per ricercare le richieste di tesi fatte dal tesista loggato
     * @param dbClass
     * @param idTesista
     * @return  Restituisce la lista trovata
     */
    public static List<RichiestaTesi> ListaRichiesteTesiTesista(Database dbClass, int idTesista) {
        String query = "SELECT r." + Database.RICHIESTA_ID + ", r." + Database.RICHIESTA_MESSAGGIO + ", r." + Database.RICHIESTA_CAPACITASTUDENTE + ", r." + Database.RICHIESTA_TESIID + ", r." + Database.RICHIESTA_TESISTAID + ", r." + Database.RICHIESTA_ACCETTATA + ", r." + Database.RICHIESTA_RISPOSTA +
                " FROM " + Database.RICHIESTA + " r " +
                " WHERE " + Database.RICHIESTA_TESISTAID + "=" + idTesista + ";";

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
            richiestaTesi.setStato(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICHIESTA_ACCETTATA)));
            richiestaTesi.setRisposta(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICHIESTA_RISPOSTA)));

            listaRichiesteTesi.add(richiestaTesi);

        }
        return listaRichiesteTesi;
    }
}
