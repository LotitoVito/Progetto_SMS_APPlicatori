package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

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
            Date data = new Date(cursore.getLong(1));
            ricevimentoEstratto.setData(data);
            Time orario = new Time(cursore.getLong(2));
            ricevimentoEstratto.setOrario(orario);
            ricevimentoEstratto.setArgomento(cursore.getString(3));
            ricevimentoEstratto.setIdTask(cursore.getInt(4));
            ricevimentoEstratto.setAccettazione(cursore.getInt(5));
            ricevimentoEstratto.setMessaggio(cursore.getString(6));

            Log.d("Ricevimento ID", String.valueOf(ricevimentoEstratto.getIdRicevimento()));
            Log.d("Ricevimento data", String.valueOf(ricevimentoEstratto.getData()));
            Log.d("Ricevimento orario", String.valueOf(ricevimentoEstratto.getOrario()));
            Log.d("Ricevimento argomento", ricevimentoEstratto.getArgomento());
            Log.d("Ricevimento id task", String.valueOf(ricevimentoEstratto.getIdTask()));
            Log.d("Ricevimento accettazione", String.valueOf(ricevimentoEstratto.getAccettazione()));
            Log.d("Ricevimento messaggio", ricevimentoEstratto.getMessaggio());
            Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");

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
            ricevimentoEstratto.setAccettazione(cursore.getInt(5));
            ricevimentoEstratto.setMessaggio(cursore.getString(6));

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
            Date data = new Date(cursore.getLong(1)*1000);
            ricevimentoEstratto.setData((java.sql.Date) data);
            Time orario = new Time(cursore.getLong(2)*4);
            ricevimentoEstratto.setOrario(orario);
            ricevimentoEstratto.setArgomento(cursore.getString(3));
            ricevimentoEstratto.setIdTask(cursore.getInt(4));
            ricevimentoEstratto.setAccettazione(cursore.getInt(5));
            ricevimentoEstratto.setMessaggio(cursore.getString(6));


            //prova a stampare data e ora

            listaRicevimentiEstratti.add(ricevimentoEstratto);
        }
        return listaRicevimentiEstratti;

    }

}