package it.uniba.dib.sms222329.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222329.classi.Relatore;
import it.uniba.dib.sms222329.classi.UtenteRegistrato;

public class RelatoreDatabase {

    public static boolean RegistrazioneRelatore(Relatore relatore, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRelatore = new ContentValues();

        Cursor idUtente = dbClass.RicercaDato("SELECT id FROM utenti WHERE email = '" + relatore.getEmail() + "';");
        idUtente.moveToNext();

        cvRelatore.put("utente_id", idUtente.getString(0));
        cvRelatore.put("matricola", relatore.getMatricola());

        try{
            long insertRelatore = db.insert("relatore", null, cvRelatore);
            if(insertRelatore != -1){
                ContentValues cvCorsiRelatore = new ContentValues();

                Cursor idRelatore = dbClass.RicercaDato("SELECT id FROM relatore WHERE utente_id = '" + idUtente.getString(0) + "';");
                idRelatore.moveToNext();

                for(int i=0; i<relatore.getCorsiRelatore().size(); i++){
                    cvCorsiRelatore.put("relatore_id", idRelatore.getString(0));
                    cvCorsiRelatore.put("universitacorso_id", relatore.getCorsiRelatore().get(i));

                    long insertCorsiRelatore = db.insert("corsiRelatore", null, cvCorsiRelatore);
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

    public static boolean modRelatore(Relatore account, Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put("nome", account.getNome());
        cvUtente.put("cognome", account.getCognome());
        cvUtente.put("email", account.getEmail());
        cvUtente.put("password", account.getPassword());
        cvUtente.put("codice_fiscale", account.getCodiceFiscale());


        try{
            long updateUtente = db.update("utenti", cvUtente, "id = " + account.getIdUtente(), null);

            ContentValues cvRelatore = new ContentValues();
            cvRelatore.put("matricola", account.getMatricola());
            long updateRelatore = db.update("relatore", cvRelatore, "id = " + account.getIdRelatore(), null);

            ContentValues cvLista = new ContentValues();
            long updateLista = 0;
            for(int i=0;i<account.getCorsiRelatore().size();i++){
                cvLista.put("universitacorso_id", Integer.parseInt(account.getCorsiRelatore().get(i).toString()));
                updateLista = db.update("corsiRelatore", cvLista, "relatore_id = " + account.getIdRelatore(), null);
            }

            return updateUtente != -1 && updateRelatore!= -1 && updateLista!=-1;
        }catch(Exception e){

        }
        return false;
    }

    public static Relatore IstanziaRelatore(UtenteRegistrato account, Database dbClass){
        Relatore relatoreLog = new Relatore();

        String query = "SELECT u.id, r.id, matricola, nome, cognome, email, password, codice_fiscale FROM utenti u, relatore r WHERE u.id=r.utente_id AND email = '" + account.getEmail() + "';";
        SQLiteDatabase db = dbClass.getReadableDatabase();
        Cursor cursore = db.rawQuery(query, null);
        cursore.moveToNext();

        relatoreLog.setIdUtente(cursore.getInt(0));
        relatoreLog.setIdRelatore(cursore.getInt(1));
        relatoreLog.setMatricola(cursore.getString(2));
        relatoreLog.setNome(cursore.getString(3));
        relatoreLog.setCognome(cursore.getString(4));
        relatoreLog.setEmail(cursore.getString(5));
        relatoreLog.setPassword(cursore.getString(6));
        relatoreLog.setCodiceFiscale(cursore.getString(7));

        query = "SELECT universitacorso_id FROM corsiRelatore WHERE relatore_id = '" + relatoreLog.getIdRelatore() + "';";
        cursore = db.rawQuery(query, null);
        ArrayList<Integer> lista = new ArrayList<>();
        while(cursore.moveToNext()){
            lista.add(cursore.getInt(0));
        }
        relatoreLog.setCorsiRelatore(lista);

        return relatoreLog;
    }
}
