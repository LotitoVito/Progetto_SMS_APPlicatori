package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Comparator;

import it.uniba.dib.sms222329.classi.Tesi;

public class ListaTesiDatabase {

    /**
     * Metodo usato per recuperare la lista di proposte di tesi in base alla query passata; la query conterrà diversi vincoli
     * @param query
     * @param dbClass
     * @return  Restituisce la lista trovata
     */
    public static ArrayList<Tesi> ListaTesi(String query, Database dbClass){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);


        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()) {
            Tesi tesiEstratta = new Tesi();

            tesiEstratta.setIdTesi(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESI_ID)));
            tesiEstratta.setTitolo(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESI_TITOLO)));
            tesiEstratta.setArgomenti(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESI_ARGOMENTO)));
            tesiEstratta.setTempistiche(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESI_TEMPISTICHE)));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(cursore.getColumnIndexOrThrow(Database.TESI_MEDIAVOTOMINIMA)));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESI_ESAMINECESSARI)));
            tesiEstratta.setCapacitaRichieste(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESI_SKILLRICHIESTE)));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESI_STATO)) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESI_VISUALIZZAZIONI)));
            tesiEstratta.setIdRelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESI_RELATOREID)));

            listaTesiEstratte.add(tesiEstratta);
        }
        return listaTesiEstratte;
    }
}
