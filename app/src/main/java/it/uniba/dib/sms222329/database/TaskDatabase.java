package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.Task;

public class TaskDatabase {

    public static boolean CreaTask(Database dbClass, Task task){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues taskCv = new ContentValues();

        taskCv.put("descrizione", task.getDescrizione());
        taskCv.put("data_inizio", String.valueOf(task.getDataInizio()));
        taskCv.put("data_fine", String.valueOf(task.getDataFine()));
        taskCv.put("link_materiale", task.getLinkMateriale());
        taskCv.put("stato", task.getStato());
        taskCv.put("tesi_scelta_id", task.getIdTesiScelta());

        long insertTask = db.insert("task", null, taskCv);
        if(insertTask != -1){
            return true;
        }
        return false;
    }
}
