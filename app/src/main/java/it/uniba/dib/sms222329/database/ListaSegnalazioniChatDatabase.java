package it.uniba.dib.sms222329.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.classi.SegnalazioneChat;

public class ListaSegnalazioniChatDatabase {

    public static List<SegnalazioneChat> ListaSegnalazioniChat(Database dbClass) {
        String query = "SELECT * FROM " + Database.SEGNALAZIONECHAT + ";";

        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<SegnalazioneChat> listaSegnalazioniEstratte = new ArrayList<SegnalazioneChat>();

        while(cursore.moveToNext()){
            SegnalazioneChat segnalazioneChatEstratta = new SegnalazioneChat();

            segnalazioneChatEstratta.setIdSegnalazioneChat(cursore.getInt(0));
            segnalazioneChatEstratta.setOggetto(cursore.getString(1));
            segnalazioneChatEstratta.setIdTesi(cursore.getInt(2));

            /*Log.d("Segnalazione ID", String.valueOf(segnalazioneChatEstratta.getIdSegnalazioneChat()));
            Log.d("Segnalazione Oggetto", segnalazioneChatEstratta.getOggetto());
            Log.d("Segnalazione idTesiScelta", String.valueOf(segnalazioneChatEstratta.getIdTesi()));
            Log.d("Tesi NNNNNNNNNNNNNNNNNNNNN", "\n\n\n\n");*/

            listaSegnalazioniEstratte.add(segnalazioneChatEstratta);
        }
        return listaSegnalazioniEstratte;
    }


}
