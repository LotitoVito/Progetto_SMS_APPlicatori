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

        chatCv.put("oggetto", chat.getOggetto());
        chatCv.put("tesi_scelta_id", chat.getIdTesi());

        long insertChat = db.insert("segnalazioneChat", null, chatCv);
        if(insertChat != -1){
            String query = "SELECT MAX(id) FROM segnalazioneChat WHERE oggetto = '"+ chat.getOggetto() +"' AND tesi_scelta_id = '"+ chat.getIdTesi() +"';";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToNext();

            ContentValues messaggioCv = new ContentValues();

            messaggioCv.put("messaggio", messaggio.getMessaggio());
            messaggioCv.put("utente_id", messaggio.getIdMittente());
            messaggioCv.put("segnalazione_id", cursor.getInt(0));

            long insertMessaggio = db.insert("messaggiSegnalazione", null, messaggioCv);
            if(insertMessaggio != -1){
                return true;
            }
        }
        return false;
    }

    public static boolean MessaggioChat(Database dbClass, MessaggioSegnalazione messaggio){
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues messaggioCv = new ContentValues();

        messaggioCv.put("messaggio", messaggio.getMessaggio());
        messaggioCv.put("utente_id", messaggio.getIdMittente());
        messaggioCv.put("segnalazione_id", messaggio.getIdSegnalazioneChat());

        long insertMessaggio = db.insert("messaggiSegnalazione", null, messaggioCv);
        if(insertMessaggio != -1){
            return true;
        }
        return false;
    }
}
