package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.Utility;
import it.uniba.dib.sms222329.classi.SegnalazioneMessaggio;

public class ListaSegnalazioniMessaggiDatabase {

    public static List<SegnalazioneMessaggio> ListaMessaggi(Database dbClass, int idChat) {
        String query = "SELECT * FROM " + Database.MESSAGGISEGNALAZIONE + " WHERE " + Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID + "=" + idChat + " ORDER BY " + Database.MESSAGGISEGNALAZIONE_TIMESTAMP +";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<SegnalazioneMessaggio> listaMessaggiSegnalazione = new ArrayList<SegnalazioneMessaggio>();

        while (cursore.moveToNext()) {
            SegnalazioneMessaggio messaggioEstratto = new SegnalazioneMessaggio();

            messaggioEstratto.setIdMessaggio(cursore.getInt(cursore.getColumnIndexOrThrow(Database.MESSAGGISEGNALAZIONE_ID)));
            messaggioEstratto.setMessaggio(cursore.getString(cursore.getColumnIndexOrThrow(Database.MESSAGGISEGNALAZIONE_MESSAGGIO)));
            LocalDateTime timestamp = LocalDateTime.parse(cursore.getString(cursore.getColumnIndexOrThrow(Database.MESSAGGISEGNALAZIONE_TIMESTAMP)), Utility.convertFromStringDateTime);
            messaggioEstratto.setTimestamp(timestamp);
            messaggioEstratto.setIdMittente(cursore.getInt(cursore.getColumnIndexOrThrow(Database.MESSAGGISEGNALAZIONE_UTENTEID)));
            messaggioEstratto.setIdSegnalazioneChat(cursore.getInt(cursore.getColumnIndexOrThrow(Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID)));

            listaMessaggiSegnalazione.add(messaggioEstratto);

        }
        Log.d("test", "a" + idChat);
        return listaMessaggiSegnalazione;
    }
}
