package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import it.uniba.dib.sms222329.database.Database;

public class ListaClassificaRicevimento {

    private ArrayList<Ricevimento> listaRicevimenti;

    public void ListaRicevimenti(Database dbClass) {
        String query = "SELECT * FROM " + Database.RICEVIMENTI + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Ricevimento> listaRicevimentiEstratti = new ArrayList<Ricevimento>();

        while (cursore.moveToNext()) {
            Ricevimento ricevimentoEstratto = new Ricevimento();

            ricevimentoEstratto.setIdRicevimento(cursore.getInt(0));
            //ricevimentoEstratto.setData(cursore.getInt(1));
            //ricevimentoEstratto.setOrario(cursore.getInt(2));
            ricevimentoEstratto.setArgomento(cursore.getString(3));
            ricevimentoEstratto.setIdTask(cursore.getInt(4));
            //TODO aggiungi set accettazione e messaggio una volta implementati sul db, anche nelle stampe di prova qui sotto


                                /*Log.d("Ricevimento ID", String.valueOf(ricevimentoEstratto.getIdRicevimento()));
                                Log.d("Ricevimento data", String.valueOf(ricevimentoEstratto.getData()));
                                Log.d("Ricevimento orario", String.valueOf(ricevimentoEstratto.getOrario()));
                                Log.d("Ricevimento argomento", ricevimentoEstratto.getArgomento());
                                Log.d("Ricevimento id task", String.valueOf(ricevimentoEstratto.getIdTask())));
                                Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");*/

            listaRicevimentiEstratti.add(ricevimentoEstratto);

        }
        this.listaRicevimenti = listaRicevimentiEstratti;
    }

    public ArrayList<Ricevimento> getListaRicevimenti() { //ordinamento standard
        return listaRicevimenti;
    }


//ordina per data e orario

    public ArrayList<Ricevimento> OrdinaPerData(Database dbClass) {
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query = "SELECT * FROM " + Database.RICEVIMENTI + " ORDER BY " + Database.RICEVIMENTI_DATA + " AND " + Database.RICEVIMENTI_ORARIO + ";";
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Ricevimento> listaRicevimentiEstratti = new ArrayList<Ricevimento>();

        while (cursore.moveToNext()) {
            Ricevimento ricevimentoEstratto = new Ricevimento();

            ricevimentoEstratto.setIdRicevimento(cursore.getInt(0));
            //ricevimentoEstratto.setData(cursore.getInt(1));
            //ricevimentoEstratto.setOrario(cursore.getInt(2));
            ricevimentoEstratto.setArgomento(cursore.getString(3));
            ricevimentoEstratto.setIdTask(cursore.getInt(4));
            //TODO aggiungi set accettazione e messaggio una volta implementati sul db, anche nelle stampe di prova qui sotto

            listaRicevimentiEstratti.add(ricevimentoEstratto);
        }
        return listaRicevimentiEstratti;
    }


    //ordina per argomento
    public ArrayList<Ricevimento> OrdinaPerArgomento(Database dbClass) {
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query = "SELECT * FROM " + Database.RICEVIMENTI + " ORDER BY " + Database.RICEVIMENTI_ARGOMENTO + ";";
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Ricevimento> listaRicevimentiEstratti = new ArrayList<Ricevimento>();

        while (cursore.moveToNext()) {
            Ricevimento ricevimentoEstratto = new Ricevimento();

            ricevimentoEstratto.setIdRicevimento(cursore.getInt(0));
            //ricevimentoEstratto.setData(cursore.getInt(1));
            //ricevimentoEstratto.setOrario(cursore.getInt(2));
            ricevimentoEstratto.setArgomento(cursore.getString(3));
            ricevimentoEstratto.setIdTask(cursore.getInt(4));
            //TODO aggiungi set accettazione e messaggio una volta implementati sul db, anche nelle stampe di prova qui sotto

            listaRicevimentiEstratti.add(ricevimentoEstratto);
        }
        return listaRicevimentiEstratti;

    }

}