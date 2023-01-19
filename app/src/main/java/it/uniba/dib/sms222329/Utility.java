package it.uniba.dib.sms222329;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;

public class Utility {

    private Utility() {}

    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static ArrayList<Tesi> RicercaTesi(Database dbClass) {
        //come faccio fermare la ricerca e la restituzione??
        //while fino a quando restituisce 0?
        String query = "SELECT * FROM tesi;";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        Tesi tesiEstratta = new Tesi();
        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()){
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

            Log.d("Tesi ID", String.valueOf(tesiEstratta.getId()));
            Log.d("tesi Titolo", tesiEstratta.getTitolo());
            Log.d("Tesi Argomento", tesiEstratta.getArgomenti());
            Log.d("Tesi Tempistiche", String.valueOf(tesiEstratta.getTempistiche()));
            Log.d("Tesi Mediamin", String.valueOf(tesiEstratta.getMediaVotiMinima()));
            Log.d("Tesi esami mancanti", String.valueOf(tesiEstratta.getEsamiMancantiNecessari()));
            Log.d("Tesi capacità", tesiEstratta.getCapacitaRichieste());
            Log.d("Tesi Stato disp", String.valueOf(tesiEstratta.isStatoDisponibilita()));
            Log.d("Tesi visualizz", String.valueOf(tesiEstratta.getNumeroVisualizzazioni()));
            Log.d("Tesi id Rel", String.valueOf(tesiEstratta.getIdRelatore()));
            Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");

            listaTesiEstratte.add(tesiEstratta);
        }
        // Collections.sort(listaTesiEstratte, tesiEstratta.getTitolo());
        return listaTesiEstratte;
    }
}

