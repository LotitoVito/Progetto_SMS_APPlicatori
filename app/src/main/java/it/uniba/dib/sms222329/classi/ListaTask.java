package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.uniba.dib.sms222329.database.Database;

public class ListaTask {

    private ArrayList<Task> listaTask;

    public List<Task> listaTask(Database dbClass) {
        String query = "SELECT * FROM " + Database.TASK + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Task> listaTask = new ArrayList<Task>();

        while (cursore.moveToNext()) {
           Task taskEstratto = new Task();

            taskEstratto.setIdTask(cursore.getInt(0));
            taskEstratto.setDescrizione(cursore.getString(1));
            //taskEstratto.setDataInizio(cursore.getFloat(2));
            //taskEstratto.setDataFine(cursore.getInt(3));
            taskEstratto.setLinkMateriale(cursore.getString(4));
            taskEstratto.setStato(cursore.getInt(5));
            taskEstratto.setIdTesiScelta(cursore.getInt(6));

            listaTask.add(taskEstratto);

        }
        return listaTask;
    }

    public ArrayList<Task> getListaTask() { //ordinamento standard
        return listaTask;
    }

}