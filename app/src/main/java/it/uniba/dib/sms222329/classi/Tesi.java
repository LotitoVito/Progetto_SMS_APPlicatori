package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.sql.Date;

import it.uniba.dib.sms222329.database.Database;

public class Tesi {

    private String id;
    private String argomenti;
    private Date dataPubblicazione;
    private boolean statoDisponibilita;
    private int numeroVisualizzazioni;
    private String idRelatore;
    private String[] idCorelatore;
    private String linkMateriale;
    private Vincoli vincoli;
    private String QRCode;

    public Tesi(String id, String argomenti, Date dataPubblicazione, boolean statoDisponibilita, int numeroVisualizzazioni, String idRelatore, String[] idCorelatore, String linkMateriale, Vincoli vincoli, String QRCode) {
        this.id = id;
        this.argomenti = argomenti;
        this.dataPubblicazione = dataPubblicazione;
        this.statoDisponibilita = statoDisponibilita;
        this.numeroVisualizzazioni = numeroVisualizzazioni;
        this.idRelatore = idRelatore;
        this.idCorelatore = idCorelatore;
        this.linkMateriale = linkMateriale;
        this.vincoli = vincoli;
        this.QRCode = QRCode;
    }

    public Tesi(String argomenti, Date dataPubblicazione, boolean statoDisponibilita, int numeroVisualizzazioni, String idRelatore, String[] idCorelatore, Vincoli vincoli) {
        this.argomenti = argomenti;
        this.dataPubblicazione = new java.sql.Date(System.currentTimeMillis());
        this.statoDisponibilita = true;
        this.numeroVisualizzazioni = 0;
        this.idRelatore = idRelatore;
        this.idCorelatore = idCorelatore;
        this.vincoli = vincoli;
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

    public void setDataPubblicazione(Date dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public boolean isStatoDisponibilita() {
        return statoDisponibilita;
    }

    public void setStatoDisponibilita(boolean statoDisponibilita) {
        this.statoDisponibilita = statoDisponibilita;
    }

    public int getNumeroVisualizzazioni() {
        return numeroVisualizzazioni;
    }

    public void setNumeroVisualizzazioni(int numeroVisualizzazioni) {
        this.numeroVisualizzazioni = numeroVisualizzazioni;
    }

    public String getIdRelatore() {
        return idRelatore;
    }

    public void setIdRelatore(String idRelatore) {
        this.idRelatore = idRelatore;
    }

    public String[] getIdCorelatore() {
        return idCorelatore;
    }

    public void setIdCorelatore(String[] idCorelatore) {
        this.idCorelatore = idCorelatore;
    }

    public String getLinkMateriale() {
        return linkMateriale;
    }

    public void setLinkMateriale(String linkMateriale) {
        this.linkMateriale = linkMateriale;
    }

    public Vincoli getVincoli() {
        return vincoli;
    }

    public void setVincoli(Vincoli vincoli) {
        this.vincoli = vincoli;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public boolean VerificaRelatore(Database dbClass){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query = "SELECT Matricola FROM Relatore WHERE Matricola = '" + this.idRelatore + "';";
        Cursor cursore = db.rawQuery(query, null);

        if (cursore.getCount() != 0) {
            return true;
        }
        return false;
    }

    public boolean VerificaCorelatori(Database dbClass){
        SQLiteDatabase db = dbClass.getReadableDatabase();
        String query = "SELECT Matricola FROM Relatore WHERE Matricola = '" + this.idRelatore + "';";
        Cursor cursore = db.rawQuery(query, null);

        if (cursore.getCount() != 0) {
            return true;
        }
        return false;
    }

    public boolean RegistrazioneTesi(Database dbClass) {
        SQLiteDatabase db = dbClass.getWritableDatabase();
        ContentValues cvTesi = new ContentValues();

        cvTesi.put("IDTesi", this.id);
        cvTesi.put("Argomenti", this.argomenti);
        cvTesi.put("DataPubblicazione", String.valueOf(this.dataPubblicazione));
        cvTesi.put("StatoDisponibilita", this.statoDisponibilita);
        cvTesi.put("NumeroVisualizzazioni", this.numeroVisualizzazioni);
        cvTesi.put("MatricolaRelatore", this.idRelatore);
        cvTesi.put("LinkMateriale", this.linkMateriale);
        cvTesi.put("QRCode", this.QRCode);

        long insertTesi = db.insert("Tesi", null, cvTesi);
        if(insertTesi != -1){
            ContentValues cvCorelatoriTesi = new ContentValues();

            for(int i=0; i<idCorelatore.length; i++){
                cvCorelatoriTesi.put("IDTesi", this.id);
                cvCorelatoriTesi.put("IDCorelatore", this.idCorelatore[i]);

                long insertCorelatoriTesi = db.insert("CorelatoriTesi", null, cvCorelatoriTesi);
                if(insertTesi == -1){
                    return false;
                }
            }

            ContentValues cvVincoli = new ContentValues();

            cvVincoli.put("IDTesi", this.id);
            cvVincoli.put("Tempistiche", this.vincoli.getTempistiche());
            cvVincoli.put("MediaVotiMinima", this.vincoli.getMediaVotiMinima());
            cvVincoli.put("EsamiNecessari", this.vincoli.getEsamiNecessari());
            cvVincoli.put("SkillRichieste", this.vincoli.getCapacitaRichieste());

            long insertVincoli = db.insert("Vincoli", null, cvVincoli);
            if(insertVincoli != -1){
                return true;
            }
        }
        return false;
    }
}
