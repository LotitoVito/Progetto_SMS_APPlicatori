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

    /**
     * Metodo usato per ricercare i ricevimento del tesista loggato in una certa data
     * @param dbClass
     * @param dataQuery data per cui ricercare i ricevimenti
     * @param idTesista
     * @return  Restituisce la lista trovata
     */
    public static List<Ricevimento> ListaRicevimentiGiornalieriTesista(Database dbClass, String dataQuery, int idTesista) {
        String query = "SELECT r." + Database.RICEVIMENTI_ID + ", r." + Database.RICEVIMENTI_DATA + ", r." + Database.RICEVIMENTI_ORARIO + ", r." + Database.RICEVIMENTI_TASKID + ", r." + Database.RICEVIMENTI_ACCETTAZIONE + ", r." + Database.RICEVIMENTI_MESSAGGIO +
                " FROM " + Database.RICEVIMENTI + " r, " + Database.TASK + " t, " + Database.TESISCELTA + " ts " +
                "WHERE r." + Database.RICEVIMENTI_TASKID + "=t." + Database.TASK_ID + " AND t." + Database.TASK_TESISCELTAID + "=ts." + Database.TESISCELTA_ID +
                " AND r." + Database.RICEVIMENTI_DATA + " LIKE('" + dataQuery + "') AND ts." + Database.TESISCELTA_TESISTAID + "=" + idTesista + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Ricevimento> listaRicevimentiEstratti = new ArrayList<Ricevimento>();

        while (cursore.moveToNext()) {
            Ricevimento ricevimentoEstratto = new Ricevimento();

            ricevimentoEstratto.setIdRicevimento(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ID)));
            LocalDate data = LocalDate.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_DATA)), Utility.convertFromStringDate);
            ricevimentoEstratto.setData(data);
            LocalTime orario = LocalTime.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ORARIO)));
            ricevimentoEstratto.setOrario(orario);
            ricevimentoEstratto.setIdTask(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_TASKID)));
            ricevimentoEstratto.setStato(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ACCETTAZIONE)));
            ricevimentoEstratto.setMessaggio(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_MESSAGGIO)));

            listaRicevimentiEstratti.add(ricevimentoEstratto);

        }
        return listaRicevimentiEstratti;
    }

    /**
     * Metodo usato per ricercare i ricevimento del relatore loggato in una certa data
     * @param dbClass
     * @param dataQuery data per cui ricercare i ricevimenti
     * @param idRelatore
     * @return  Restituisce la lista trovata
     */
    public static List<Ricevimento> ListaRicevimentiGiornalieriRelatore(Database dbClass, String dataQuery, int idRelatore) {
        String query = "SELECT r." + Database.RICEVIMENTI_ID + ", r." + Database.RICEVIMENTI_DATA + ", r." + Database.RICEVIMENTI_ORARIO + ", r." + Database.RICEVIMENTI_TASKID + ", r." + Database.RICEVIMENTI_ACCETTAZIONE + ", r." + Database.RICEVIMENTI_MESSAGGIO +
                " FROM " + Database.RICEVIMENTI + " r, " + Database.TASK + " t, " + Database.TESISCELTA + " ts, " + Database.TESI + " te " +
                "WHERE r." + Database.RICEVIMENTI_TASKID + "=t." + Database.TASK_ID + " AND t." + Database.TASK_TESISCELTAID + "=ts." + Database.TESISCELTA_ID + " AND ts." + Database.TESISCELTA_TESIID + "=te." + Database.TESI_ID +
                " AND r." + Database.RICEVIMENTI_DATA + " LIKE('" + dataQuery + "') AND te." + Database.TESI_RELATOREID + "=" + idRelatore + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Ricevimento> listaRicevimentiEstratti = new ArrayList<Ricevimento>();

        while (cursore.moveToNext()) {
            Ricevimento ricevimentoEstratto = new Ricevimento();

            ricevimentoEstratto.setIdRicevimento(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ID)));
            LocalDate data = LocalDate.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_DATA)), Utility.convertFromStringDate);
            ricevimentoEstratto.setData(data);
            LocalTime orario = LocalTime.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ORARIO)));
            ricevimentoEstratto.setOrario(orario);
            ricevimentoEstratto.setIdTask(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_TASKID)));
            ricevimentoEstratto.setStato(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ACCETTAZIONE)));
            ricevimentoEstratto.setMessaggio(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_MESSAGGIO)));

            listaRicevimentiEstratti.add(ricevimentoEstratto);
        }
        return listaRicevimentiEstratti;
    }

    /**
     * Metodo usato per ricercare i ricevimento del corelatore loggato in una certa data
     * @param dbClass
     * @param dataQuery data per cui ricercare i ricevimenti
     * @param idCorelatore
     * @return  Restituisce la lista trovata
     */
    public static List<Ricevimento> ListaRicevimentiGiornalieriCorelatore(Database dbClass, String dataQuery, int idCorelatore) {
        String query = "SELECT r." + Database.RICEVIMENTI_ID + ", r." + Database.RICEVIMENTI_DATA + ", r." + Database.RICEVIMENTI_ORARIO + ", r." + Database.RICEVIMENTI_TASKID + ", r." + Database.RICEVIMENTI_ACCETTAZIONE + ", r." + Database.RICEVIMENTI_MESSAGGIO +
                " FROM " + Database.RICEVIMENTI + " r, " + Database.TASK + " t, " + Database.TESISCELTA + " ts " +
                "WHERE r." + Database.RICEVIMENTI_TASKID + "=t." + Database.TASK_ID + " AND t." + Database.TASK_TESISCELTAID + "=ts." + Database.TESISCELTA_ID +
                " AND r." + Database.RICEVIMENTI_DATA + " LIKE('" + dataQuery + "') AND ts." + Database.TESISCELTA_CORELATOREID + "=" + idCorelatore + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Ricevimento> listaRicevimentiEstratti = new ArrayList<Ricevimento>();

        while (cursore.moveToNext()) {
            Ricevimento ricevimentoEstratto = new Ricevimento();

            ricevimentoEstratto.setIdRicevimento(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ID)));
            LocalDate data = LocalDate.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_DATA)), Utility.convertFromStringDate);
            ricevimentoEstratto.setData(data);
            LocalTime orario = LocalTime.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ORARIO)));
            ricevimentoEstratto.setOrario(orario);
            ricevimentoEstratto.setIdTask(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_TASKID)));
            ricevimentoEstratto.setStato(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ACCETTAZIONE)));
            ricevimentoEstratto.setMessaggio(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_MESSAGGIO)));

            listaRicevimentiEstratti.add(ricevimentoEstratto);

        }
        return listaRicevimentiEstratti;
    }

    /**
     * Metodo usato per ricercare i ricevimento del tesista loggato in una certa data
     * @param dbClass
     * @param idTesista
     * @return  Restituisce la lista trovata
     */
    public static List<Ricevimento> ListaRicevimentiTesista(Database dbClass, int idTesista) {
        String query = "SELECT r." + Database.RICEVIMENTI_ID + ", r." + Database.RICEVIMENTI_DATA + ", r." + Database.RICEVIMENTI_ORARIO + ", r." + Database.RICEVIMENTI_TASKID + ", r." + Database.RICEVIMENTI_ACCETTAZIONE + ", r." + Database.RICEVIMENTI_MESSAGGIO +
                " FROM " + Database.RICEVIMENTI + " r, " + Database.TASK + " t, " + Database.TESISCELTA + " ts " +
                "WHERE r." + Database.RICEVIMENTI_TASKID + "=t." + Database.TASK_ID + " AND t." + Database.TASK_TESISCELTAID + "=ts." + Database.TESISCELTA_ID +
                " AND r." + Database.RICEVIMENTI_DATA + " >= '" + LocalDate.now() + "' AND ts." + Database.TESISCELTA_TESISTAID + "=" + idTesista + ";";

        Log.d("test", String.valueOf(LocalDate.now()));

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Ricevimento> listaRicevimentiEstratti = new ArrayList<Ricevimento>();

        while (cursore.moveToNext()) {
            Ricevimento ricevimentoEstratto = new Ricevimento();

            ricevimentoEstratto.setIdRicevimento(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ID)));
            LocalDate data = LocalDate.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_DATA)), Utility.convertFromStringDate);
            ricevimentoEstratto.setData(data);
            LocalTime orario = LocalTime.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ORARIO)));
            ricevimentoEstratto.setOrario(orario);
            ricevimentoEstratto.setIdTask(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_TASKID)));
            ricevimentoEstratto.setStato(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ACCETTAZIONE)));
            ricevimentoEstratto.setMessaggio(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_MESSAGGIO)));

            listaRicevimentiEstratti.add(ricevimentoEstratto);

        }
        return listaRicevimentiEstratti;
    }

    /**
     * Metodo usato per ricercare i ricevimento del relatore loggato in una certa data
     * @param dbClass
     * @param idRelatore
     * @return  Restituisce la lista trovata
     */
    public static List<Ricevimento> ListaRicevimentiRelatore(Database dbClass, int idRelatore) {
        String query = "SELECT r." + Database.RICEVIMENTI_ID + ", r." + Database.RICEVIMENTI_DATA + ", r." + Database.RICEVIMENTI_ORARIO + ", r." + Database.RICEVIMENTI_TASKID + ", r." + Database.RICEVIMENTI_ACCETTAZIONE + ", r." + Database.RICEVIMENTI_MESSAGGIO +
                " FROM " + Database.RICEVIMENTI + " r, " + Database.TASK + " t, " + Database.TESISCELTA + " ts, " + Database.TESI + " te " +
                "WHERE r." + Database.RICEVIMENTI_TASKID + "=t." + Database.TASK_ID + " AND t." + Database.TASK_TESISCELTAID + "=ts." + Database.TESISCELTA_ID + " AND ts." + Database.TESISCELTA_TESIID + "=te." + Database.TESI_ID +
                " AND r." + Database.RICEVIMENTI_DATA + " >= '" + LocalDate.now() + "' AND te." + Database.TESI_RELATOREID + "=" + idRelatore + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Ricevimento> listaRicevimentiEstratti = new ArrayList<Ricevimento>();

        while (cursore.moveToNext()) {
            Ricevimento ricevimentoEstratto = new Ricevimento();

            ricevimentoEstratto.setIdRicevimento(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ID)));
            LocalDate data = LocalDate.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_DATA)), Utility.convertFromStringDate);
            ricevimentoEstratto.setData(data);
            LocalTime orario = LocalTime.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ORARIO)));
            ricevimentoEstratto.setOrario(orario);
            ricevimentoEstratto.setIdTask(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_TASKID)));
            ricevimentoEstratto.setStato(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ACCETTAZIONE)));
            ricevimentoEstratto.setMessaggio(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_MESSAGGIO)));

            listaRicevimentiEstratti.add(ricevimentoEstratto);
        }
        return listaRicevimentiEstratti;
    }

    /**
     * Metodo usato per ricercare i ricevimento del corelatore loggato in una certa data
     * @param dbClass
     * @param idCorelatore
     * @return  Restituisce la lista trovata
     */
    public static List<Ricevimento> ListaRicevimentiCorelatore(Database dbClass, int idCorelatore) {
        String query = "SELECT r." + Database.RICEVIMENTI_ID + ", r." + Database.RICEVIMENTI_DATA + ", r." + Database.RICEVIMENTI_ORARIO + ", r." + Database.RICEVIMENTI_TASKID + ", r." + Database.RICEVIMENTI_ACCETTAZIONE + ", r." + Database.RICEVIMENTI_MESSAGGIO +
                " FROM " + Database.RICEVIMENTI + " r, " + Database.TASK + " t, " + Database.TESISCELTA + " ts " +
                "WHERE r." + Database.RICEVIMENTI_TASKID + "=t." + Database.TASK_ID + " AND t." + Database.TASK_TESISCELTAID + "=ts." + Database.TESISCELTA_ID +
                " AND r." + Database.RICEVIMENTI_DATA + " >= '" + LocalDate.now() + "' AND ts." + Database.TESISCELTA_CORELATOREID + "=" + idCorelatore + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Ricevimento> listaRicevimentiEstratti = new ArrayList<Ricevimento>();

        while (cursore.moveToNext()) {
            Ricevimento ricevimentoEstratto = new Ricevimento();

            ricevimentoEstratto.setIdRicevimento(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ID)));
            LocalDate data = LocalDate.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_DATA)), Utility.convertFromStringDate);
            ricevimentoEstratto.setData(data);
            LocalTime orario = LocalTime.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ORARIO)));
            ricevimentoEstratto.setOrario(orario);
            ricevimentoEstratto.setIdTask(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_TASKID)));
            ricevimentoEstratto.setStato(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_ACCETTAZIONE)));
            ricevimentoEstratto.setMessaggio(cursore.getString(cursore.getColumnIndexOrThrow(Database.RICEVIMENTI_MESSAGGIO)));

            listaRicevimentiEstratti.add(ricevimentoEstratto);

        }
        return listaRicevimentiEstratti;
    }
}
