package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.uniba.dib.sms222329.database.Database;

public class ListaRichiesteTesi {

    private ArrayList<RichiestaTesi> listaRichiesteTesi;

    public List<RichiestaTesi> listaRichiesteTesi(Database dbClass) {
        String query = "SELECT * FROM " + Database.RICHIESTA + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<RichiestaTesi> listaRichiesteTesi = new ArrayList<RichiestaTesi>();

        while (cursore.moveToNext()) {
            RichiestaTesi richiestaTesi = new RichiestaTesi();

            richiestaTesi.setIdRichiesta(cursore.getInt(0));
            richiestaTesi.setMessaggio(cursore.getString(1));
            richiestaTesi.setIdTesi(cursore.getInt(2));
            richiestaTesi.setIdTesista(cursore.getInt(3));
            richiestaTesi.setAccettata(cursore.getInt(4));
            richiestaTesi.setMessaggio(cursore.getString(5));

            listaRichiesteTesi.add(richiestaTesi);

        }
        return listaRichiesteTesi;
    }

    public ArrayList<RichiestaTesi> getListaRichiesteTesi() { //ordinamento standard
        return listaRichiesteTesi;
    }

}