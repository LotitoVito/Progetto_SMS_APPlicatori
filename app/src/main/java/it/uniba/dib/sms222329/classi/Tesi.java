package it.uniba.dib.sms222329.classi;

import java.sql.Date;

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
}
