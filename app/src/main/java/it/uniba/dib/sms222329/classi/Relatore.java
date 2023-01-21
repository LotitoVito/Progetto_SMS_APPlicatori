package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import it.uniba.dib.sms222329.database.Database;

public class Relatore extends Supervisore {

    private int idRelatore;
    private String matricola;
    private List corsiRelatore;

    public Relatore(String matricola, String nome, String cognome, String codiceFiscale, String email, String password, List corsiRelatore) {
        super(nome, cognome, codiceFiscale, email, password);
        this.matricola = matricola;
        this.corsiRelatore = corsiRelatore;
    }

    public Relatore(){ super();}

    public int getIdRelatore() {return idRelatore;}

    public void setIdRelatore(int idRelatore) {this.idRelatore = idRelatore;}

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public List getCorsiRelatore() {return corsiRelatore;}

    public void setCorsiRelatore(List corsiRelatore) {this.corsiRelatore = corsiRelatore;}

    //Registrazione account su database
    public boolean RegistrazioneRelatore(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvRelatore = new ContentValues();

        Cursor idUtente = dbClass.RicercaDato("SELECT id FROM utenti WHERE email = '" + this.email + "';");
        idUtente.moveToNext();

        cvRelatore.put("utente_id", idUtente.getString(0));
        cvRelatore.put("matricola", this.matricola);

        try{
            long insertRelatore = db.insert("relatore", null, cvRelatore);
            if(insertRelatore != -1){
                ContentValues cvCorsiRelatore = new ContentValues();

                Cursor idRelatore = dbClass.RicercaDato("SELECT id FROM relatore WHERE utente_id = '" + idUtente.getString(0) + "';");
                idRelatore.moveToNext();

                for(int i=0; i<corsiRelatore.size(); i++){
                    cvCorsiRelatore.put("relatore_id", idRelatore.getString(0));
                    cvCorsiRelatore.put("universitacorso_id", (String) this.corsiRelatore.get(i));

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

    public boolean modRelatore(Database dbClass, String matricola, String nome, String cognome,
                               String codiceFiscale, String email, String password, List corsiRelatore) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.codiceFiscale=codiceFiscale;
        this.matricola=matricola;

        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvUtente = new ContentValues();

        cvUtente.put("nome", this.nome);
        cvUtente.put("cognome", this.cognome);
        cvUtente.put("email", this.email);
        cvUtente.put("password", this.password);
        cvUtente.put("codice_fiscale", this.codiceFiscale);


        try{
            long updateUtente = db.update("utenti", cvUtente, "id = " + this.idUtente, null);

            ContentValues cvRelatore = new ContentValues();
            cvRelatore.put("matricola", this.matricola);
            long updateRelatore = db.update("relatore", cvRelatore, "id = " + this.idRelatore, null);

            ContentValues cvLista = new ContentValues();
            long updateLista = 0;
            for(int i=0;i<corsiRelatore.size();i++){
                cvLista.put("universitacorso_id", Integer.parseInt(corsiRelatore.get(i).toString()));
                updateLista = db.update("corsiRelatore", cvLista, "relatore_id = " + this.idRelatore, null);
            }

            return updateUtente != -1 && updateRelatore!= -1 && updateLista!=-1;
        }catch(Exception e){

        }
        return false;
    }
}
