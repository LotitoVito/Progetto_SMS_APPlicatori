package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;

public class RelatoreDatabase {

    /**
     * Metodo per la registrazione dell'account del Relatore sul database nella tabella del Relatore e CorsiRelatore
     * @param relatore
     * @param dbClass
     * @return  Restituisce true se l'operazione va a buon fine, altrimenti false
     */
    public static boolean RegistrazioneRelatore(Relatore relatore, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRelatore = new ContentValues();

        Cursor idUtente = dbClass.RicercaDato("SELECT " + Database.UTENTI_ID + " FROM " + Database.UTENTI + " WHERE " + Database.UTENTI_EMAIL + " = '" + relatore.getEmail() + "';");
        idUtente.moveToNext();

        //Registro tabella Relatore
        cvRelatore.put(Database.RELATORE_UTENTEID, idUtente.getString(idUtente.getColumnIndexOrThrow(Database.UTENTI_ID)));
        cvRelatore.put(Database.RELATORE_MATRICOLA, relatore.getMatricola());

        try{
            long insertRelatore = db.insert(Database.RELATORE, null, cvRelatore);
            if(insertRelatore != -1){
                ContentValues cvCorsiRelatore = new ContentValues();

                Cursor idRelatore = dbClass.RicercaDato("SELECT " + Database.RELATORE_ID + " FROM " + Database.RELATORE + " WHERE " + Database.RELATORE_UTENTEID + " = '" + idUtente.getString(0) + "';");
                idRelatore.moveToNext();

                //Registro tabella CorsiRelatore
                for(int i=0; i<relatore.getCorsiRelatore().size(); i++){
                    cvCorsiRelatore.put(Database.CORSIRELATORE_RELATOREID, idRelatore.getString(idRelatore.getColumnIndexOrThrow(Database.RELATORE_ID)));
                    cvCorsiRelatore.put(Database.CORSIRELATORE_UNIVERSITACORSOID, relatore.getCorsiRelatore().get(i));

                    long insertCorsiRelatore = db.insert(Database.CORSIRELATORE, null, cvCorsiRelatore);
                    if (insertCorsiRelatore == -1){
                        return false;
                    }
                }
                return  true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metodo di modifica dell'account del Relatore sul database
     * @param account
     * @param dbClass
     * @return  Restituisce true se l'operazione va a buon fine, altrimenti false
     */
    public static boolean modRelatore(Relatore account, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put(Database.UTENTI_NOME, account.getNome());
        cvUtente.put(Database.UTENTI_COGNOME, account.getCognome());
        cvUtente.put(Database.UTENTI_EMAIL, account.getEmail());
        cvUtente.put(Database.UTENTI_PASSWORD, account.getPassword());
        cvUtente.put(Database.UTENTI_CODICEFISCALE, account.getCodiceFiscale());

        try{
            long updateUtente = db.update(Database.UTENTI, cvUtente, Database.UTENTI_ID+" = " + account.getIdUtente() + ";", null);

            if(updateUtente != -1){
                ContentValues cvRelatore = new ContentValues();
                cvRelatore.put(Database.RELATORE_MATRICOLA, account.getMatricola());
                long updateRelatore = db.update(Database.RELATORE, cvRelatore, Database.RELATORE_ID +" = " + account.getIdRelatore() + ";", null);

                if(updateRelatore!= -1){
                    ContentValues cvLista = new ContentValues();
                    db.delete(Database.CORSIRELATORE, Database.CORSIRELATORE_RELATOREID + " = '"+ account.getIdRelatore() + "';", null);
                    for(int i=0;i<account.getCorsiRelatore().size();i++){
                        cvLista.put(Database.CORSIRELATORE_UNIVERSITACORSOID, Integer.parseInt(account.getCorsiRelatore().get(i).toString()));
                        cvLista.put(Database.CORSIRELATORE_RELATOREID, account.getIdRelatore());
                        long updateLista = db.insert(Database.CORSIRELATORE,null, cvLista);
                        if(updateLista==-1){
                            return false;
                        }
                    }
                    return true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metodo per istanziare l'account del Relatore, ricerca i dati in base alle informazioni di login poassate nell'oggetto account e
     * istanzia il relatore
     * @param account
     * @param dbClass
     * @return  Restituisce l'oggetto Relatore
     */
    public static Relatore IstanziaRelatore(UtenteRegistrato account, Database dbClass){
        Relatore relatoreLog = new Relatore();

        String query =  "SELECT r." + Database.RELATORE_UTENTEID + ", r." + Database.RELATORE_ID + ", " + Database.RELATORE_MATRICOLA + ", " + Database.UTENTI_NOME + ", " + Database.UTENTI_COGNOME + ", " + Database.UTENTI_EMAIL + ", " + Database.UTENTI_PASSWORD + ", " + Database.UTENTI_CODICEFISCALE + " " +
                        "FROM " + Database.UTENTI + " u, " + Database.RELATORE + " r " +
                        "WHERE u." + Database.UTENTI_ID + "=r." + Database.RELATORE_UTENTEID + " AND " + Database.UTENTI_EMAIL + " = '" + account.getEmail() + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        cursore.moveToNext();

        relatoreLog.setIdUtente(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RELATORE_UTENTEID)));
        relatoreLog.setIdRelatore(cursore.getInt(cursore.getColumnIndexOrThrow(Database.RELATORE_ID)));
        relatoreLog.setMatricola(cursore.getString(cursore.getColumnIndexOrThrow(Database.RELATORE_MATRICOLA)));
        relatoreLog.setNome(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_NOME)));
        relatoreLog.setCognome(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_COGNOME)));
        relatoreLog.setEmail(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_EMAIL)));
        relatoreLog.setPassword(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_PASSWORD)));
        relatoreLog.setCodiceFiscale(cursore.getString(cursore.getColumnIndexOrThrow(Database.UTENTI_CODICEFISCALE)));

        query = "SELECT " + Database.CORSIRELATORE_UNIVERSITACORSOID + " FROM " + Database.CORSIRELATORE + " WHERE " + Database.CORSIRELATORE_RELATOREID + " = '" + relatoreLog.getIdRelatore() + "';";
        cursore = db.rawQuery(query, null);
        ArrayList<Integer> lista = new ArrayList<>();
        while(cursore.moveToNext()){
            lista.add(cursore.getInt(cursore.getColumnIndexOrThrow(Database.CORSIRELATORE_UNIVERSITACORSOID)));
        }
        relatoreLog.setCorsiRelatore(lista);

        return relatoreLog;
    }
}
