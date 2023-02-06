package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDate;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiSceltaDatabase;

public class TesiScelta extends Tesi{

    public static int RIFIUTATO = 0;
    public static int IN_ATTESA = 1;
    public static int ACCETTATO = 2;

    private int idTesiScelta;
    private int idTesista;
    private int idCorelatore;
    private int statoCorelatore;
    private byte[] file;
    private LocalDate dataPubblicazione;
    private String riassunto;

    public TesiScelta() {}

    public TesiScelta(int idTesi, int idTesiScelta, int idTesista, int idCorelatore, int statoCorelatore, LocalDate dataPubblicazione, String riassunto) {
        super(idTesi);
        this.idTesiScelta = idTesiScelta;
        this.idTesista = idTesista;
        this.idCorelatore = idCorelatore;
        this.statoCorelatore = statoCorelatore;
        this.dataPubblicazione = dataPubblicazione;
        this.riassunto = riassunto;
    }

    //Usato per la registrazione
    public TesiScelta(int idTesi, int idTesista) {
        super(idTesi);
        this.idTesista = idTesista;
    }

    public int getIdTesiScelta() {
        return idTesiScelta;
    }

    public void setIdTesiScelta(int idTesiScelta) {
        this.idTesiScelta = idTesiScelta;
    }

    public int getIdTesista() {
        return idTesista;
    }

    public void setIdTesista(int idTesista) {
        this.idTesista = idTesista;
    }

    public int getIdCorelatore() {
        return idCorelatore;
    }

    public void setIdCorelatore(int idCorelatore) {
        this.idCorelatore = idCorelatore;
    }

    public int getStatoCorelatore() {return statoCorelatore;}

    public void setStatoCorelatore(int statoCorelatore) {this.statoCorelatore = statoCorelatore;}

    public byte[] getFile() {return file;}

    public void setFile(byte[] file) {this.file = file;}

    public LocalDate getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(LocalDate dataPubblicazione) {this.dataPubblicazione = dataPubblicazione;}

    public String getRiassunto() {
        return riassunto;
    }

    public void setRiassunto(String riassunto) {
        this.riassunto = riassunto;
    }

    public boolean AggiungiCorelatore(Database dbClass, String emailCorelatore){
        Cursor cursore = dbClass.RicercaDato("SELECT c." + Database.CORELATORE_ID + " FROM " + Database.CORELATORE + " c, " + Database.UTENTI + " u " +
                "WHERE c." + Database.CORELATORE_UTENTEID + "=u." + Database.UTENTI_ID + " AND " + Database.UTENTI_EMAIL + "='" + emailCorelatore + "';");
        cursore.moveToFirst();

        Log.d("test", String.valueOf(cursore.getInt(cursore.getColumnIndexOrThrow(Database.CORELATORE_ID))));

        try{
            this.idCorelatore = cursore.getInt(cursore.getColumnIndexOrThrow(Database.CORELATORE_ID));
            this.statoCorelatore = IN_ATTESA;

            if(TesiSceltaDatabase.AggiungiCorelatore(dbClass, this)){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean RimuoviCorelatore(Database dbClass){
        this.idCorelatore = 0;
        this.statoCorelatore = RIFIUTATO;

        if(TesiSceltaDatabase.RimuoviCorelatore(dbClass, this)){
            return true;
        }
        return false;
    }

    public boolean AccettaRichiesta(Database dbClass){
        this.statoCorelatore = ACCETTATO;

        if(TesiSceltaDatabase.AccettaRichiesta(dbClass, this)){
            return true;
        }
        return false;
    }

    public boolean RifiutaRichiesta(Database dbClass){
        this.statoCorelatore = RIFIUTATO;

        if(TesiSceltaDatabase.RifiutaRichiesta(dbClass, this)){
            return true;
        }
        return false;
    }

    public boolean ConsegnaTesiScelta(Database dbClass, String riassunto){
        this.riassunto = riassunto;
        this.dataPubblicazione = LocalDate.now();

        if(TesiSceltaDatabase.ConsegnaTesiScelta(dbClass, this)){
            return true;
        }
        return false;
    }
}
