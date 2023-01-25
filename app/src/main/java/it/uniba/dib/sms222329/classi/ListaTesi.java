package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Comparator;

import it.uniba.dib.sms222329.database.Database;

public class ListaTesi {

    private ArrayList<Tesi> lista;

    public ListaTesi(Database dbClass) {
        String query = "SELECT * FROM tesi;";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()){
            Tesi tesiEstratta = new Tesi();

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

                                /*Log.d("Tesi ID", String.valueOf(tesiEstratta.getId()));
                                Log.d("tesi Titolo", tesiEstratta.getTitolo());
                                Log.d("Tesi Argomento", tesiEstratta.getArgomenti());
                                Log.d("Tesi Tempistiche", String.valueOf(tesiEstratta.getTempistiche()));
                                Log.d("Tesi Mediamin", String.valueOf(tesiEstratta.getMediaVotiMinima()));
                                Log.d("Tesi esami mancanti", String.valueOf(tesiEstratta.getEsamiMancantiNecessari()));
                                Log.d("Tesi capacità", tesiEstratta.getCapacitaRichieste());
                                Log.d("Tesi Stato disp", String.valueOf(tesiEstratta.isStatoDisponibilita()));
                                Log.d("Tesi visualizz", String.valueOf(tesiEstratta.getNumeroVisualizzazioni()));
                                Log.d("Tesi id Rel", String.valueOf(tesiEstratta.getIdRelatore()));
                                Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");*/

            listaTesiEstratte.add(tesiEstratta);
        }
        listaTesiEstratte.sort(Comparator.comparing(Tesi::getNumeroVisualizzazioni));
        this.lista = listaTesiEstratte;
    }



    public ArrayList<Tesi> getLista() {
        return lista;
    }



    //ordinamenti vari
    public ArrayList<Tesi> OrdinaPerTitolo(Database dbClass){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query ="SELECT * FROM Tesi ORDER BY titolo;";
        Cursor cursore = db.rawQuery(query, null);


        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()) {
            Tesi tesiEstratta = new Tesi();

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

            listaTesiEstratte.add(tesiEstratta);
        }
        this.lista = listaTesiEstratte;
        return this.lista;
    }

    public ArrayList<Tesi> OrdinaPerArgomento(Database dbClass){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query ="SELECT * FROM Tesi ORDER BY argomento;";
        Cursor cursore = db.rawQuery(query, null);


        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()) {
            Tesi tesiEstratta = new Tesi();

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

            listaTesiEstratte.add(tesiEstratta);
        }
        this.lista = listaTesiEstratte;
        return this.lista;
    }

    public ArrayList<Tesi> OrdinaPerTempistiche(Database dbClass){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query ="SELECT * FROM Tesi ORDER BY tempistiche;";
        Cursor cursore = db.rawQuery(query, null);


        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()) {
            Tesi tesiEstratta = new Tesi();

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

            listaTesiEstratte.add(tesiEstratta);
        }
        this.lista = listaTesiEstratte;
        return this.lista;
    }



    //vincoli vari
    public ArrayList<Tesi> vincoloVotoTesi(int vincoloVotoMin, Database dbClass){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query ="SELECT * FROM Tesi WHERE media_voto_minima <= " + vincoloVotoMin + ";";
        Cursor cursore = db.rawQuery(query, null);


        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()) {
            Tesi tesiEstratta = new Tesi();

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

            listaTesiEstratte.add(tesiEstratta);
        }
        this.lista = listaTesiEstratte;
        return this.lista;
    }


    public ArrayList<Tesi> vincoloEsamiMancanti(int vincoloEsamiMancanti, Database dbClass){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query ="SELECT * FROM Tesi WHERE esami_necessari <= " + vincoloEsamiMancanti + ";"; //all'utente mancano tot esami che devono essere inferiori al vincolo o uguali
        Cursor cursore = db.rawQuery(query, null);


        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()) {
            Tesi tesiEstratta = new Tesi();

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

            listaTesiEstratte.add(tesiEstratta);
        }
        this.lista = listaTesiEstratte;
        return this.lista;
    }

    public ArrayList<Tesi> vincoloDisponibilità(int disponibilità, Database dbClass){ //visualizza solo tesi disponibili
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query ="SELECT * FROM Tesi WHERE stato == " + disponibilità + ";";
        Cursor cursore = db.rawQuery(query, null);


        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()) {
            Tesi tesiEstratta = new Tesi();

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

            listaTesiEstratte.add(tesiEstratta);
        }
        this.lista = listaTesiEstratte;
        return this.lista;
    }


    public ArrayList<Tesi> vincoloRelatore(int idRelatore, Database dbClass){ //visualizza solo tesi disponibili
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query ="SELECT * FROM Tesi WHERE relatore_id == " + idRelatore + ";";
        Cursor cursore = db.rawQuery(query, null);


        ArrayList<Tesi> listaTesiEstratte = new ArrayList<Tesi>();

        while(cursore.moveToNext()) {
            Tesi tesiEstratta = new Tesi();

            tesiEstratta.setId(cursore.getInt(0));
            tesiEstratta.setTitolo(cursore.getString(1));
            tesiEstratta.setArgomenti(cursore.getString(2));
            tesiEstratta.setTempistiche(cursore.getInt(3));
            tesiEstratta.setMediaVotiMinima(cursore.getFloat(4));
            tesiEstratta.setEsamiMancantiNecessari(cursore.getInt(5));
            tesiEstratta.setCapacitaRichieste(cursore.getString(6));
            tesiEstratta.setStatoDisponibilita(cursore.getInt(7) != 0);
            tesiEstratta.setNumeroVisualizzazioni(cursore.getInt(8));
            tesiEstratta.setIdRelatore(cursore.getInt(9));

            listaTesiEstratte.add(tesiEstratta);
        }
        this.lista = listaTesiEstratte;
        return this.lista;
    }
}


