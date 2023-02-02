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

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

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

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

            listaTesiEstratte.add(tesiEstratta);
        }
        return listaTesiEstratte;
    }
}
