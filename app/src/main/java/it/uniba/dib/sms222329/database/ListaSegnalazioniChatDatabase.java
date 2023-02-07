package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.classi.SegnalazioneChat;

public class ListaSegnalazioniChatDatabase {

    public static List<SegnalazioneChat> ListaSegnalazioniChat(Database dbClass, int utenteId) {
        String query = "SELECT c." + Database.SEGNALAZIONECHAT_ID + ", c." + Database.SEGNALAZIONECHAT_OGGETTO + ", c." + Database.SEGNALAZIONECHAT_TESISCELTAID +
                " FROM " + Database.SEGNALAZIONECHAT + " c, " + Database.MESSAGGISEGNALAZIONE + " m "+
                " WHERE c." + Database.SEGNALAZIONECHAT_ID + "=m." + Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID +
                " AND m." + Database.MESSAGGISEGNALAZIONE_UTENTEID + "=" + utenteId +
                " GROUP BY m." + Database.MESSAGGISEGNALAZIONE_UTENTEID + ", m." + Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID +
                " ORDER BY MAX(m." + Database.MESSAGGISEGNALAZIONE_TIMESTAMP + ") DESC;";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<SegnalazioneChat> listaSegnalazioniEstratte = new ArrayList<SegnalazioneChat>();

        while(cursore.moveToNext()){
            SegnalazioneChat segnalazioneChatEstratta = new SegnalazioneChat();

            segnalazioneChatEstratta.setIdSegnalazioneChat(cursore.getInt(cursore.getColumnIndexOrThrow(Database.SEGNALAZIONECHAT_ID)));
            segnalazioneChatEstratta.setOggetto(cursore.getString(cursore.getColumnIndexOrThrow(Database.SEGNALAZIONECHAT_OGGETTO)));
            segnalazioneChatEstratta.setIdTesi(cursore.getInt(cursore.getColumnIndexOrThrow(Database.SEGNALAZIONECHAT_TESISCELTAID)));

            /*Log.d("Segnalazione ID", String.valueOf(segnalazioneChatEstratta.getIdSegnalazioneChat()));
            Log.d("Segnalazione Oggetto", segnalazioneChatEstratta.getOggetto());
            Log.d("Segnalazione idTesiScelta", String.valueOf(segnalazioneChatEstratta.getIdTesi()));
            Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");*/

            listaSegnalazioniEstratte.add(segnalazioneChatEstratta);
        }
        return listaSegnalazioniEstratte;
    }

    public static List<SegnalazioneChat> ListaSegnalazioniChatRelatore(Database dbClass, int utenteId, int relatoreId) {
        String query = "SELECT DISTINCT c." + Database.SEGNALAZIONECHAT_ID + ", c." + Database.SEGNALAZIONECHAT_OGGETTO + ", c." + Database.SEGNALAZIONECHAT_TESISCELTAID +
                    " FROM " + Database.SEGNALAZIONECHAT + " c, " + Database.MESSAGGISEGNALAZIONE + " m, " + Database.TESI + " t " +
                    " WHERE c." + Database.SEGNALAZIONECHAT_ID + "=m." + Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID + " AND c." + Database.SEGNALAZIONECHAT_TESISCELTAID + "=t." + Database.TESI_ID +
                    " AND (m." + Database.MESSAGGISEGNALAZIONE_UTENTEID + "=" + utenteId + " OR t." + Database.TESI_RELATOREID + "=" + relatoreId + ")" +
                    " GROUP BY m." + Database.MESSAGGISEGNALAZIONE_UTENTEID + ", m." + Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID + ", t." + Database.TESI_RELATOREID +
                    " ORDER BY MAX(m." + Database.MESSAGGISEGNALAZIONE_TIMESTAMP + ") DESC;";

        String queryRicevute =  "SELECT * " +
                        " FROM " + Database.SEGNALAZIONECHAT + " c, " + Database.MESSAGGISEGNALAZIONE + " m, " + Database.TESI + " t " +
                        " WHERE c." + Database.SEGNALAZIONECHAT_ID + "=m." + Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID + " AND c." + Database.SEGNALAZIONECHAT_TESISCELTAID + "=t." + Database.TESI_ID +
                        " AND t." + Database.TESI_RELATOREID + "=" + relatoreId;

        /*String 1 = "SELECT c." + Database.SEGNALAZIONECHAT_ID + ", c." + Database.SEGNALAZIONECHAT_OGGETTO + ", c." + Database.SEGNALAZIONECHAT_TESISCELTAID +
                        " FROM (" + queryInviate + " UNION " + queryRicevute + ") AS risultato " +
                        " GROUP BY m." + Database.MESSAGGISEGNALAZIONE_UTENTEID + ", m." + Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID + ", t." + Database.TESI_RELATOREID +
                        " ORDER BY MAX(m." + Database.MESSAGGISEGNALAZIONE_TIMESTAMP + ") ASC;";*/

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<SegnalazioneChat> listaSegnalazioniEstratte = new ArrayList<SegnalazioneChat>();

        while(cursore.moveToNext()){
            SegnalazioneChat segnalazioneChatEstratta = new SegnalazioneChat();

            segnalazioneChatEstratta.setIdSegnalazioneChat(cursore.getInt(cursore.getColumnIndexOrThrow(Database.SEGNALAZIONECHAT_ID)));
            segnalazioneChatEstratta.setOggetto(cursore.getString(cursore.getColumnIndexOrThrow(Database.SEGNALAZIONECHAT_OGGETTO)));
            segnalazioneChatEstratta.setIdTesi(cursore.getInt(cursore.getColumnIndexOrThrow(Database.SEGNALAZIONECHAT_TESISCELTAID)));

            Log.d("Segnalazione ID", String.valueOf(cursore.getInt(cursore.getColumnIndexOrThrow(Database.SEGNALAZIONECHAT_ID))));
            Log.d("Segnalazione Oggetto", cursore.getString(cursore.getColumnIndexOrThrow(Database.SEGNALAZIONECHAT_OGGETTO)));
            Log.d("Segnalazione idTesiScelta", String.valueOf(cursore.getInt(cursore.getColumnIndexOrThrow(Database.SEGNALAZIONECHAT_TESISCELTAID))));
            Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");

            listaSegnalazioniEstratte.add(segnalazioneChatEstratta);
        }
        return listaSegnalazioniEstratte;
    }
}
