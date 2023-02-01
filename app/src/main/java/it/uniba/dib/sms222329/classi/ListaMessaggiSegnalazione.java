package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.uniba.dib.sms222329.database.Database;

public class ListaMessaggiSegnalazione {

    private ArrayList<MessaggioSegnalazione> listaMessaggiSegnalazione;

    public List<MessaggioSegnalazione> listaTesisti(Database dbClass) {
        String query = "SELECT * FROM " + Database.MESSAGGISEGNALAZIONE + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<MessaggioSegnalazione> listaMessaggiSegnalazione = new ArrayList<MessaggioSegnalazione>();

        while (cursore.moveToNext()) {
            MessaggioSegnalazione messaggioEstratto = new MessaggioSegnalazione();

            messaggioEstratto.setIdMessaggio(cursore.getInt(0));
            messaggioEstratto.setMessaggio(cursore.getString(1));
            //messaggioEstratto.setTimestamp(cursore.getFloat(2));
            messaggioEstratto.setIdMittente(cursore.getInt(3));
            messaggioEstratto.setIdSegnalazioneChat(cursore.getInt(4));

            listaMessaggiSegnalazione.add(messaggioEstratto);

        }
        return listaMessaggiSegnalazione;
    }

    public ArrayList<MessaggioSegnalazione> getListaMessaggiSegnalazione() { //ordinamento standard
        return listaMessaggiSegnalazione;
    }

}