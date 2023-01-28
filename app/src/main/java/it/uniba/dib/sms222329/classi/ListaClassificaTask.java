package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import it.uniba.dib.sms222329.database.Database;

public class ListaClassificaTask {


    private ArrayList<Task> listaTask;



    public void ListaSegnalazioni(Database dbClass) {
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
        this.listaTask = listaTaskEstratti;
    }

    public ArrayList<Task> getListaTask() { //ordinamento standard
        return listaTask;
    }


    //ordinamento per descrizione
    public ArrayList<Task> OrdinaPerDescrizione(Database dbClass) {
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query = "SELECT * FROM " + Database.TASK + " ORDER BY " + Database.TASK_DESCRIZIONE + ";";
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Task> listaTaskEstratti = new ArrayList<Task>();

        while (cursore.moveToNext()) {
            Task taskEstratto = new Task();

            taskEstratto.setIdTask(cursore.getInt(0));
            taskEstratto.setDescrizione(cursore.getString(1));
            //taskEstratto.setDataInizio(cursore.getDATA(2));
            //taskEstratto.setDataFine(cursore.getDATA(3));
            taskEstratto.setLinkMateriale(cursore.getString(4));
            taskEstratto.setStato(cursore.getInt(5));
            taskEstratto.setIdTesiScelta(cursore.getInt(6));

            listaTaskEstratti.add(taskEstratto);

        }
        return listaTaskEstratti;
    }

    //ordinamento per tesi scelta
    public ArrayList<Task> OrdinaPerTesiScelta(Database dbClass) {
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query = "SELECT * FROM " + Database.TASK + " ORDER BY " + Database.TASK_TESISCELTAID + ";";
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Task> listaTaskEstratti = new ArrayList<Task>();

        while (cursore.moveToNext()) {
            Task taskEstratto = new Task();

            taskEstratto.setIdTask(cursore.getInt(0));
            taskEstratto.setDescrizione(cursore.getString(1));
            //taskEstratto.setDataInizio(cursore.getDATA(2));
            //taskEstratto.setDataFine(cursore.getDATA(3));
            taskEstratto.setLinkMateriale(cursore.getString(4));
            taskEstratto.setStato(cursore.getInt(5));
            taskEstratto.setIdTesiScelta(cursore.getInt(6));

            listaTaskEstratti.add(taskEstratto);

        }
        return listaTaskEstratti;
    }




    //visualizza solo task aperti
    public ArrayList<Task> OrdinaPerStato(Database dbClass) {
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query = "SELECT * FROM " + Database.TASK + " WHERE " + Database.TASK_STATO + "==1;";
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Task> listaTaskEstratti = new ArrayList<Task>();

        while (cursore.moveToNext()) {
            Task taskEstratto = new Task();

            taskEstratto.setIdTask(cursore.getInt(0));
            taskEstratto.setDescrizione(cursore.getString(1));
            //taskEstratto.setDataInizio(cursore.getDATA(2));
            //taskEstratto.setDataFine(cursore.getDATA(3));
            taskEstratto.setLinkMateriale(cursore.getString(4));
            taskEstratto.setStato(cursore.getInt(5));
            taskEstratto.setIdTesiScelta(cursore.getInt(6));

            listaTaskEstratti.add(taskEstratto);

        }
        return listaTaskEstratti;
    }



}
