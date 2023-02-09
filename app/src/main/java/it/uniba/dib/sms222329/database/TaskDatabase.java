package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import it.uniba.dib.sms222329.classi.Task;
import it.uniba.dib.sms222329.classi.TesiScelta;

public class TaskDatabase {

    /**
     * Metodo usato per assegnare un nuovo task in una tesi scelta specifica su database
     * @param dbClass
     * @param task
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
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

    /**
     * Metodo usato per modificare un task su database
     * @param dbClass
     * @param task
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean ModificaTask(Database dbClass, Task task){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues taskCv = new ContentValues();

        taskCv.put(Database.TASK_TITOLO, task.getTitolo());
        taskCv.put(Database.TASK_DESCRIZIONE, task.getDescrizione());
        taskCv.put(Database.TASK_DATAFINE, String.valueOf(task.getDataFine()));
        taskCv.put(Database.TASK_STATO, task.getStato());

        long updateTask = db.update(Database.TASK, taskCv, Database.TASK_ID + "=" + task.getIdTask(), null);
        if(updateTask != -1){
            return true;
        }
        return false;
    }

    public static boolean UploadTask(Database dbClass, Task task, String valore){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues taskCv = new ContentValues();

        taskCv.put(Database.TASK_LINKMATERIALE, valore);

        long updateTesiScelta = db.update(Database.TASK, taskCv, Database.TASK_ID + "=" + task.getIdTask(), null);
        if(updateTesiScelta != -1){
            return true;
        }
        return false;
    }

    public static String DownloadTask(Database dbClass, Task task){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + Database.TASK_LINKMATERIALE +
                " FROM " + Database.TASK +
                " WHERE " + Database.TASK_ID + "=" + task.getIdTask() + ";", null);
        cursor.moveToFirst();
        String key = cursor.getString(cursor.getColumnIndexOrThrow(Database.TASK_LINKMATERIALE));
        if(key == null) {
            key = "Empty";
        }
        return key;
    }
}
