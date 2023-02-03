package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Comparator;

import it.uniba.dib.sms222329.classi.Tesi;

public class ListaTesiDatabase {

    public static ArrayList<Tesi> ListaTesi(Database dbClass) {
        String query = "SELECT * FROM " + Database.TESI + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()){
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

            /*Log.d("Tesi ID", String.valueOf(tesiEstratta.getId()));
            Log.d("tesi Titolo", tesiEstratta.getTitolo());
            Log.d("Tesi Argomento", tesiEstratta.getArgomenti());
            Log.d("Tesi Tempistiche", String.valueOf(tesiEstratta.getTempistiche()));
            Log.d("Tesi Mediamin", String.valueOf(tesiEstratta.getMediaVotiMinima()));
            Log.d("Tesi esami mancanti", String.valueOf(tesiEstratta.getEsamiMancantiNecessari()));
            Log.d("Tesi capacità", tesiEstratta.getCapacitaRichieste());
            Log.d("Tesi Stato disp", String.valueOf(tesiEstratta.isStatoDisponibilita()));
            Log.d("Tesi visualizz", String.valueOf(tesiEstratta.getNumeroVisualizzazioni()));
            Log.d("Tesi id Rel", String.valueOf(tesiEstratta.getIdRelatore()));
            Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");*/

            listaTesiEstratte.add(tesiEstratta);
        }
        listaTesiEstratte.sort(Comparator.comparing(Tesi::getNumeroVisualizzazioni));
        return listaTesiEstratte;
    }

    public static ArrayList<Tesi> ListaTesi(String query, Database dbClass){ //visualizza solo tesi disponibili
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
