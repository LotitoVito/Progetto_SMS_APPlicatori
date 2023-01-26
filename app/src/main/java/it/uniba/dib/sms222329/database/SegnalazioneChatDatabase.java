package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.MessaggioSegnalazione;
import it.uniba.dib.sms222329.classi.SegnalazioneChat;

public class SegnalazioneChatDatabase {

    public static boolean AvviaChat(Database dbClass, SegnalazioneChat chat, MessaggioSegnalazione messaggio){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues chatCv = new ContentValues();

        chatCv.put(Database.SEGNALAZIONECHAT_OGGETTO, chat.getOggetto());
        chatCv.put(Database.SEGNALAZIONECHAT_TESISCELTAID, chat.getIdTesi());

        long insertChat = db.insert(Database.SEGNALAZIONECHAT, null, chatCv);
        if(insertChat != -1){
            String query = "SELECT MAX(" + Database.SEGNALAZIONECHAT_ID + ") FROM " + Database.SEGNALAZIONECHAT + " WHERE " + Database.SEGNALAZIONECHAT_OGGETTO + " = '"+ chat.getOggetto() +"' AND " + Database.SEGNALAZIONECHAT_TESISCELTAID + " = '"+ chat.getIdTesi() +"';";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToNext();

            ContentValues messaggioCv = new ContentValues();

            messaggioCv.put(Database.MESSAGGISEGNALAZIONE_MESSAGGIO, messaggio.getMessaggio());
            messaggioCv.put(Database.MESSAGGISEGNALAZIONE_UTENTEID, messaggio.getIdMittente());
            messaggioCv.put(Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID, cursor.getInt(0));

            long insertMessaggio = db.insert(Database.MESSAGGISEGNALAZIONE, null, messaggioCv);
            if(insertMessaggio != -1){
                return true;
            }
        }
        return false;
    }

    public static boolean MessaggioChat(Database dbClass, MessaggioSegnalazione messaggio){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues messaggioCv = new ContentValues();

        messaggioCv.put(Database.MESSAGGISEGNALAZIONE_MESSAGGIO, messaggio.getMessaggio());
        messaggioCv.put(Database.MESSAGGISEGNALAZIONE_UTENTEID, messaggio.getIdMittente());
        messaggioCv.put(Database.MESSAGGISEGNALAZIONE_SEGNALAZIONEID, messaggio.getIdSegnalazioneChat());

        long insertMessaggio = db.insert(Database.MESSAGGISEGNALAZIONE, null, messaggioCv);
        if(insertMessaggio != -1){
            return true;
        }
        return false;
    }
}
