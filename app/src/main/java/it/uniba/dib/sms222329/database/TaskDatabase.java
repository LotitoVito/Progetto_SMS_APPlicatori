package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.Task;

public class TaskDatabase {

    public static boolean CreaTask(Database dbClass, Task task){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues taskCv = new ContentValues();

        taskCv.put(Database.TASK_TITOLO, task.getTitolo());
        taskCv.put(Database.TASK_DESCRIZIONE, task.getDescrizione());
        taskCv.put(Database.TASK_DATAINIZIO, String.valueOf(task.getDataInizio()));
        taskCv.put(Database.TASK_DATAFINE, String.valueOf(task.getDataFine()));
        taskCv.put(Database.TASK_LINKMATERIALE, task.getLinkMateriale());
        taskCv.put(Database.TASK_STATO, task.getStato());
        taskCv.put(Database.TASK_TESISCELTAID, task.getIdTesiScelta());

        long insertTask = db.insert(Database.TASK, null, taskCv);
        if(insertTask != -1){
            return true;
        }
        return false;
    }
}
