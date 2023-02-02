package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.classi.Task;

public class ListaTaskDatabase {

    public static List<Task> ListaTask(Database dbClass) {
        String query = "SELECT * FROM " + Database.TASK + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Task> listaTaskEstratti = new ArrayList<Task>();

        while(cursore.moveToNext()){
            Task taskEstratto = new Task();

            taskEstratto.setIdTask(cursore.getInt(0));
            taskEstratto.setDescrizione(cursore.getString(1));
            //taskEstratto.setDataInizio(cursore.getDATA(2));
            //taskEstratto.setDataFine(cursore.getDATA(3));
            taskEstratto.setLinkMateriale(cursore.getString(4));
            taskEstratto.setStato(cursore.getInt(5));
            taskEstratto.setIdTesiScelta(cursore.getInt(6));

            /*Log.d("Task ID", String.valueOf(taskEstratto.getIdTask()));
            Log.d("Task descrizione", taskEstratto.getDescrizione());
            Log.d("Task inizio data", String.valueOf(taskEstratto.getDataInizio()));
            Log.d("Task fine data", String.valueOf(taskEstratto.getDataFine()));
            Log.d("Task link materiale", taskEstratto.getLinkMateriale());
            Log.d("Task stato", String.valueOf(taskEstratto.getStato()));
            Log.d("Task id tesi scelta", String.valueOf(taskEstratto.getIdTesiScelta()));
            Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");*/

            listaTaskEstratti.add(taskEstratto);

        }
        return listaTaskEstratti;
    }
}
