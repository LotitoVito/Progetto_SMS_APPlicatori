package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import it.uniba.dib.sms222329.database.Database;

public class ListaClassificaSegnalazione {

    private ArrayList<SegnalazioneChat> lista;

    public ListaClassificaSegnalazione() {
    }

    public List<SegnalazioneChat> ListaSegnalazioni(Database dbClass) {
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

    public ArrayList<SegnalazioneChat> getLista() { //ordinamento standard
        return lista;
    }

    //ordina per oggetto segnalazione
    public ArrayList<SegnalazioneChat> OrdinaPerOggetto (Database dbClass){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query ="SELECT * FROM " + Database.SEGNALAZIONECHAT + " ORDER BY " + Database.SEGNALAZIONECHAT_OGGETTO + ";";
        Cursor cursore = db.rawQuery(query, null);

            ArrayList<SegnalazioneChat> listaSegnalazioniEstratte = new ArrayList<SegnalazioneChat>();

        while(cursore.moveToNext()) {
            SegnalazioneChat segnalazioneChatEstratta = new SegnalazioneChat();

            segnalazioneChatEstratta.setIdSegnalazioneChat(cursore.getInt(0));
            segnalazioneChatEstratta.setOggetto(cursore.getString(1));
            segnalazioneChatEstratta.setIdTesi(cursore.getInt(2));
            listaSegnalazioniEstratte.add(segnalazioneChatEstratta);
        }
        this.lista = listaSegnalazioniEstratte;
        return this.lista;

    }




    //ordina per tesi scelta

    public ArrayList<SegnalazioneChat> OrdinaPerTesiScelta(Database dbClass){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query ="SELECT * FROM " + Database.SEGNALAZIONECHAT + " ORDER BY " + Database.SEGNALAZIONECHAT_TESISCELTAID + ";";
        Cursor cursore = db.rawQuery(query, null);

        ArrayList<SegnalazioneChat> listaSegnalazioniEstratte = new ArrayList<SegnalazioneChat>();

        while(cursore.moveToNext()) {
            SegnalazioneChat segnalazioneChatEstratta = new SegnalazioneChat();

            segnalazioneChatEstratta.setIdSegnalazioneChat(cursore.getInt(0));
            segnalazioneChatEstratta.setOggetto(cursore.getString(1));
            segnalazioneChatEstratta.setIdTesi(cursore.getInt(2));
            listaSegnalazioniEstratte.add(segnalazioneChatEstratta);
        }
        this.lista = listaSegnalazioniEstratte;
        return this.lista;

    }




}
