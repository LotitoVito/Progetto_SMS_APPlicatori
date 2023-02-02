package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Ricevimento;

public class ListaRicevimentiDatabase {

    public static List<Ricevimento> ListaRicevimenti(Database dbClass) {
        String query = "SELECT * FROM " + Database.RICEVIMENTI + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Ricevimento> listaRicevimentiEstratti = new ArrayList<Ricevimento>();

        while (cursore.moveToNext()) {
            Ricevimento ricevimentoEstratto = new Ricevimento();

            ricevimentoEstratto.setIdRicevimento(cursore.getInt(0));
            LocalDate data = LocalDate.parse(cursore.getString(1), Utility.convertFromStringDate);
            ricevimentoEstratto.setData(data);
            LocalTime orario = LocalTime.parse(cursore.getString(2));
            ricevimentoEstratto.setOrario(orario);
            ricevimentoEstratto.setArgomento(cursore.getString(3));
            ricevimentoEstratto.setIdTask(cursore.getInt(4));
            ricevimentoEstratto.setAccettazione(cursore.getInt(5));
            ricevimentoEstratto.setMessaggio(cursore.getString(6));

            /*Log.d("Ricevimento ID", String.valueOf(ricevimentoEstratto.getIdRicevimento()));
            Log.d("Ricevimento data", String.valueOf(ricevimentoEstratto.getData()));
            Log.d("Ricevimento orario", String.valueOf(ricevimentoEstratto.getOrario()));
            Log.d("Ricevimento argomento", ricevimentoEstratto.getArgomento());
            Log.d("Ricevimento id task", String.valueOf(ricevimentoEstratto.getIdTask()));
            Log.d("Ricevimento accettazione", String.valueOf(ricevimentoEstratto.getAccettazione()));
            Log.d("Ricevimento messaggio", ricevimentoEstratto.getMessaggio());
            Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");*/

            listaRicevimentiEstratti.add(ricevimentoEstratto);

        }
        return listaRicevimentiEstratti;
    }
}
