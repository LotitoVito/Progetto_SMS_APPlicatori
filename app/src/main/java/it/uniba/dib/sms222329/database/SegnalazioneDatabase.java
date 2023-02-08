package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.uniba.dib.sms222329.classi.SegnalazioneMessaggio;
import it.uniba.dib.sms222329.classi.SegnalazioneChat;

public class SegnalazioneDatabase {

    /**
     * Metodo per la registrazione di una nuova chat in base alla tesi selezionata, registra anche il primo messaggio mandato
     * @param dbClass
     * @param chat
     * @param messaggio
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean AvviaChat(Database dbClass, SegnalazioneChat chat, SegnalazioneMessaggio messaggio){
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

    /**
     * Metodo usato per registrare un nuovo messaggio in una chat specifica
     * @param dbClass
     * @param messaggio
     * @return  Restituisce true se l'operazione va a buon fine, altrimento false
     */
    public static boolean MessaggioChat(Database dbClass, SegnalazioneMessaggio messaggio){
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
