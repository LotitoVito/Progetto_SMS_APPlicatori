package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.sql.Date;

import it.uniba.dib.sms222329.activities.MainActivity;
import it.uniba.dib.sms222329.database.Database;

public class Tesi {

    private String id;
    private String argomenti;
    private Date dataPubblicazione;
    private boolean statoDisponibilita;
    private int numeroVisualizzazioni;
    private String idRelatore;
    private String idCorelatore;
    private String linkMateriale;
    private String QRCode;
    private String tempistiche;
    private int mediaVotiMinima;
    private int esamiMancantiNecessari;
    private String capacitaRichieste;


    public Tesi(String id, String argomenti, Date dataPubblicazione, boolean statoDisponibilita, int numeroVisualizzazioni,
                String idRelatore, String idCorelatore, String linkMateriale, String QRCode,
                String tempistiche, int mediaVotiMinima, int esamiMancantiNecessari, String capacitaRichieste) {
        this.id = id;
        this.argomenti = argomenti;
        this.dataPubblicazione = dataPubblicazione;
        this.statoDisponibilita = statoDisponibilita;
        this.numeroVisualizzazioni = numeroVisualizzazioni;
        this.idRelatore = idRelatore;
        this.idCorelatore = idCorelatore;
        this.linkMateriale = linkMateriale;
        this.QRCode = QRCode;
        this.tempistiche = tempistiche;
        this.mediaVotiMinima = mediaVotiMinima;
        this.esamiMancantiNecessari = esamiMancantiNecessari;
        this.capacitaRichieste = capacitaRichieste;
    }

    //Usato per al registrazione
    public Tesi(String argomenti, String idRelatore, String idCorelatore, String tempistiche,
                int mediaVotiMinima, int esamiMancantiNecessari, String capacitaRichieste) {
        this.argomenti = argomenti;
        this.dataPubblicazione = new java.sql.Date(System.currentTimeMillis());
        this.statoDisponibilita = true;
        this.numeroVisualizzazioni = 0;
        this.idRelatore = idRelatore;
        this.idCorelatore = idCorelatore;
        this.tempistiche = tempistiche;
        this.mediaVotiMinima = mediaVotiMinima;
        this.esamiMancantiNecessari = esamiMancantiNecessari;
        this.capacitaRichieste = capacitaRichieste;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArgomenti() {
        return argomenti;
    }

    public void setArgomenti(String argomenti) {
        this.argomenti = argomenti;
    }

    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(Date dataPubblicazione) {this.dataPubblicazione = dataPubblicazione;}

    public boolean isStatoDisponibilita() {
        return statoDisponibilita;
    }

    public void setStatoDisponibilita(boolean statoDisponibilita) {this.statoDisponibilita = statoDisponibilita;}

    public int getNumeroVisualizzazioni() {
        return numeroVisualizzazioni;
    }

    public void setNumeroVisualizzazioni(int numeroVisualizzazioni) {this.numeroVisualizzazioni = numeroVisualizzazioni;}

    public String getIdRelatore() {
        return idRelatore;
    }

    public void setIdRelatore(String idRelatore) {
        this.idRelatore = idRelatore;
    }

    public String getIdCorelatore() {return idCorelatore;}

    public void setIdCorelatore(String idCorelatore) {this.idCorelatore = idCorelatore;}

    public String getLinkMateriale() {
        return linkMateriale;
    }

    public void setLinkMateriale(String linkMateriale) {
        this.linkMateriale = linkMateriale;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getTempistiche() {return tempistiche;}

    public void setTempistiche(String tempistiche) {this.tempistiche = tempistiche;}

    public int getMediaVotiMinima() {return mediaVotiMinima;}

    public void setMediaVotiMinima(int mediaVotiMinima) {this.mediaVotiMinima = mediaVotiMinima;}

    public int getEsamiMancantiNecessari() {return esamiMancantiNecessari;}

    public void setesamiMancantiNecessari(int esamiMancantiNecessari) {this.esamiMancantiNecessari = esamiMancantiNecessari;}

    public String getCapacitaRichieste() {return capacitaRichieste;}

    public void setCapacitaRichieste(String capacitaRichieste) {this.capacitaRichieste = capacitaRichieste;}

    public boolean RegistrazioneTesi(Database dbClass) {
        if(dbClass.VerificaDatoEsistente("Matricola", "Relatore", this.idRelatore) &&
        dbClass.VerificaDatoEsistente("ID", "CoRelatore", this.idCorelatore)){

            SQLiteDatabase db = dbClass.getWritableDatabase();
            ContentValues cvTesi = new ContentValues();

            cvTesi.put("Argomenti", this.argomenti);
            cvTesi.put("DataPubblicazione", String.valueOf(this.dataPubblicazione));
            cvTesi.put("StatoDisponibilita", this.statoDisponibilita);
            cvTesi.put("NumeroVisualizzazioni", this.numeroVisualizzazioni);
            cvTesi.put("MatricolaRelatore", this.idRelatore);
            cvTesi.put("IDCorelatore", this.idCorelatore);
            cvTesi.put("LinkMateriale", this.linkMateriale);
            cvTesi.put("QRCode", this.QRCode);
            cvTesi.put("Tempistiche", this.tempistiche);
            cvTesi.put("MediaVotiMinima", this.mediaVotiMinima);
            cvTesi.put("EsamiMancantiNecessari", this.esamiMancantiNecessari);
            cvTesi.put("SkillRichieste", this.capacitaRichieste);

            long insertTesi = db.insert("Tesi", null, cvTesi);
            if(insertTesi != -1){
                return true;
            }
        }
        //inserire Toast errore
        return false;
    }
}
