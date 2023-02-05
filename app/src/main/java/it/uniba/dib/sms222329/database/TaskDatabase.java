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

    public static boolean CaricaFile(Database dbClass, File file, int idTask) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ByteArrayOutputStream bos = null;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, array);
        byte[] buffer = array.toByteArray();
        /*try{
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }

        }catch (Exception e){
            e.printStackTrace();
        }*/

        ContentValues fileCv = new ContentValues();
        fileCv.put(Database.TASK_LINKMATERIALE, buffer);
        long updateTask = db.update(Database.TASK, fileCv, Database.TASK_ID + "=" + idTask, null);
        if(updateTask != -1){
            return true;
        }
        return false;
    }


}
