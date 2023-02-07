package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.Task;

public class ListaTaskDatabase {

    public static List<Task> ListaTask(Database dbClass, int idTesiScelta) {
        String query = "SELECT * FROM " + Database.TASK + " WHERE " + Database.TASK_TESISCELTAID + "=" + idTesiScelta + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Task> listaTaskEstratti = new ArrayList<Task>();

        while(cursore.moveToNext()){
            Task taskEstratto = new Task();

            taskEstratto.setIdTask(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TASK_ID)));
            taskEstratto.setTitolo(cursore.getString(cursore.getColumnIndexOrThrow(Database.TASK_TITOLO)));
            taskEstratto.setDescrizione(cursore.getString(cursore.getColumnIndexOrThrow(Database.TASK_DESCRIZIONE)));
            LocalDate dataInizio = LocalDate.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.TASK_DATAINIZIO)), Utility.convertFromStringDate);
            taskEstratto.setDataInizio(dataInizio);
            LocalDate dataFine = null;
            try{
                dataFine = LocalDate.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.TASK_DATAFINE)), Utility.convertFromStringDate);
            }catch (Exception e){
                e.printStackTrace();
            }
            taskEstratto.setDataFine(dataFine);
            taskEstratto.setLinkMateriale(cursore.getBlob(cursore.getColumnIndexOrThrow(Database.TASK_LINKMATERIALE)));
            taskEstratto.setStato(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TASK_STATO)));
            taskEstratto.setIdTesiScelta(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TASK_TESISCELTAID)));

            /*Log.d("Task ID", String.valueOf(taskEstratto.getIdTask()));
            Log.d("Task descrizione", taskEstratto.getDescrizione());
            Log.d("Task inizio data", String.valueOf(taskEstratto.getDataInizio()));
            Log.d("Task fine data", String.valueOf(taskEstratto.getDataFine()));
            Log.d("Task link materiale", String.valueOf(taskEstratto.getLinkMateriale()));
            Log.d("Task stato", String.valueOf(taskEstratto.getStato()));
            Log.d("Task id tesi scelta", String.valueOf(taskEstratto.getIdTesiScelta()));
            Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");*/

            listaTaskEstratti.add(taskEstratto);

        }
        return listaTaskEstratti;
    }
}
