package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.database.Database;

public class ListaMessaggiSegnalazione {

    private ArrayList<SegnalazioneMessaggio> listaMessaggiSegnalazione;

    public List<SegnalazioneMessaggio> listamessaggi(Database dbClass, int idChat) {
        String query = "SELECT * FROM " + Database.MESSAGGISEGNALAZIONE + " WHERE " + Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID + "=" + idChat + " ORDER BY " + Database.MESSAGGISEGNALAZIONE_TIMESTAMP +";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<SegnalazioneMessaggio> listaMessaggiSegnalazione = new ArrayList<SegnalazioneMessaggio>();

        while (cursore.moveToNext()) {
            SegnalazioneMessaggio messaggioEstratto = new SegnalazioneMessaggio();

            messaggioEstratto.setIdMessaggio(cursore.getInt(0));
            messaggioEstratto.setMessaggio(cursore.getString(1));
            LocalDateTime timestamp = LocalDateTime.parse(cursore.getString(2), Utility.convertFromStringDateTime);
            messaggioEstratto.setTimestamp(timestamp);
            messaggioEstratto.setIdMittente(cursore.getInt(3));
            messaggioEstratto.setIdSegnalazioneChat(cursore.getInt(4));

            listaMessaggiSegnalazione.add(messaggioEstratto);

        }
        return listaMessaggiSegnalazione;
    }

    public ArrayList<SegnalazioneMessaggio> getListaMessaggiSegnalazione() { //ordinamento standard
        return listaMessaggiSegnalazione;
    }

}