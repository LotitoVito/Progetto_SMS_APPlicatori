package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.TesiScelta;
import it.uniba.dib.sms222329.fragment.calendario.CalendarUtils;

public class ListaTesiScelteDatabase {

    public static List<TesiScelta> ListaTesiScelteDatabase(Database dbClass) {
        String query = "SELECT * FROM " + Database.TESISCELTA + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        List<TesiScelta> listaTesiScelte = new ArrayList<TesiScelta>();

        while (cursore.moveToNext()) {
            TesiScelta tesiSceltaEstratta = new TesiScelta();

            tesiSceltaEstratta.setIdTesiScelta(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_ID)));
            String dataStringa = cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_DATAPUBBLICAZIONE));
            if(dataStringa != null){
                LocalDate data = LocalDate.parse(dataStringa, Utility.convertFromStringDate);
                tesiSceltaEstratta.setDataPubblicazione(data);
            }
            tesiSceltaEstratta.setRiassunto(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_ABSTRACT)));
            tesiSceltaEstratta.setFile(cursore.getBlob(cursore.getColumnIndexOrThrow(Database.TESISCELTA_DOWNLOAD)));
            tesiSceltaEstratta.setIdTesi(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_TESIID)));
            tesiSceltaEstratta.setIdCorelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_CORELATOREID)));
            tesiSceltaEstratta.setStatoCorelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_STATOCORELATORE)));
            tesiSceltaEstratta.setIdTesista(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_TESISTAID)));
            tesiSceltaEstratta.setCapacitàStudente(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_CAPACITATESISTA)));

            listaTesiScelte.add(tesiSceltaEstratta);
        }
        return listaTesiScelte;
    }

    public static List<TesiScelta> ListaTesiScelteCompletateDatabase(Database dbClass) {
        String query = "SELECT * FROM " + Database.TESISCELTA + " ts, " + Database.TESI + " t " +
                "WHERE ts." + Database.TESISCELTA_TESIID + "=t." + Database.TESI_ID + " AND "+ Database.TESISCELTA_DATAPUBBLICAZIONE + "!='';";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        List<TesiScelta> listaTesiScelte = new ArrayList<TesiScelta>();

        while (cursore.moveToNext()) {
            TesiScelta tesiSceltaEstratta = new TesiScelta();

            tesiSceltaEstratta.setIdTesiScelta(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_ID)));
            String dataStringa = cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_DATAPUBBLICAZIONE));
            if(dataStringa != null){
                LocalDate data = LocalDate.parse(dataStringa, Utility.convertFromStringDate);
                tesiSceltaEstratta.setDataPubblicazione(data);
            }
            tesiSceltaEstratta.setRiassunto(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_ABSTRACT)));
            tesiSceltaEstratta.setFile(cursore.getBlob(cursore.getColumnIndexOrThrow(Database.TESISCELTA_DOWNLOAD)));
            tesiSceltaEstratta.setIdTesi(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_TESIID)));
            tesiSceltaEstratta.setIdCorelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_CORELATOREID)));
            tesiSceltaEstratta.setStatoCorelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_STATOCORELATORE)));
            tesiSceltaEstratta.setIdTesista(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_TESISTAID)));
            tesiSceltaEstratta.setTitolo(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESI_TITOLO)));
            tesiSceltaEstratta.setCapacitàStudente(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_CAPACITATESISTA)));

            listaTesiScelte.add(tesiSceltaEstratta);
        }
        return listaTesiScelte;
    }

    public static List<TesiScelta> ListaRichiesteTesiCorelatore(Database dbClass, int idCorelatore) {
        String query = "SELECT * FROM " + Database.TESISCELTA + " " +
                "WHERE " + Database.TESISCELTA_CORELATOREID + "=" + idCorelatore + " " +
                "AND " + Database.TESISCELTA_STATOCORELATORE + "=" + TesiScelta.IN_ATTESA + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<TesiScelta> listaRichieste = new ArrayList<TesiScelta>();

        while (cursore.moveToNext()) {
            TesiScelta richiestaTesi = new TesiScelta();

            richiestaTesi.setIdTesiScelta(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_ID)));
            richiestaTesi.setIdTesi(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_TESIID)));
            richiestaTesi.setIdTesista(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_TESISTAID)));
            richiestaTesi.setIdCorelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_CORELATOREID)));
            richiestaTesi.setStatoCorelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_STATOCORELATORE)));
            richiestaTesi.setCapacitàStudente(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_CAPACITATESISTA)));
            try{
                LocalDate data = LocalDate.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_DATAPUBBLICAZIONE)), Utility.convertFromStringDate);
                richiestaTesi.setDataPubblicazione(data);
            }catch (Exception e){
                e.printStackTrace();
            }
            richiestaTesi.setRiassunto(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_ABSTRACT)));

            listaRichieste.add(richiestaTesi);

        }
        return listaRichieste;
    }

    public static List<TesiScelta> ListaTesistiCorelatore(Database dbClass, int idCorelatore) {
        String query = "SELECT * FROM " + Database.TESISCELTA + " " +
                "WHERE " + Database.TESISCELTA_CORELATOREID + "=" + idCorelatore + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<TesiScelta> listaRichieste = new ArrayList<TesiScelta>();

        while (cursore.moveToNext()) {
            TesiScelta richiestaTesi = new TesiScelta();

            richiestaTesi.setIdTesiScelta(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_ID)));
            richiestaTesi.setIdTesi(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_TESIID)));
            richiestaTesi.setIdTesista(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_TESISTAID)));
            richiestaTesi.setIdCorelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_CORELATOREID)));
            richiestaTesi.setStatoCorelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.TESISCELTA_STATOCORELATORE)));
            richiestaTesi.setCapacitàStudente(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_CAPACITATESISTA)));
            try{
                LocalDate data = LocalDate.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_DATAPUBBLICAZIONE)), Utility.convertFromStringDate);
                richiestaTesi.setDataPubblicazione(data);
            }catch (Exception e){
                e.printStackTrace();
            }
            richiestaTesi.setRiassunto(cursore.getString(cursore.getColumnIndexOrThrow(Database.TESISCELTA_ABSTRACT)));

            listaRichieste.add(richiestaTesi);

        }
        return listaRichieste;
    }

}
