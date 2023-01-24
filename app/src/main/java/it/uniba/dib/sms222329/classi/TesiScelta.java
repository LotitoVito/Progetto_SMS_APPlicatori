package it.uniba.dib.sms222329.classi;

import java.sql.Date;

public class TesiScelta extends Tesi{

    private int idTesiScelta;
    private int idTesista;
    private int idCorelatore;
    private Date dataPubblicazione;
    private String riassunto;

    public TesiScelta(Tesi tesi, int idTesiScelta, int idTesista, int idCorelatore, Date dataPubblicazione, String riassunto) {
        super(tesi.getId(), tesi.getTitolo(), tesi.getArgomenti(), tesi.getIdRelatore());
        this.idTesiScelta = idTesiScelta;
        this.idTesista = idTesista;
        this.idCorelatore = idCorelatore;
        this.dataPubblicazione = dataPubblicazione;
        this.riassunto = riassunto;
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

    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(Date dataPubblicazione) {this.dataPubblicazione = dataPubblicazione;}

    public String getRiassunto() {
        return riassunto;
    }

    public void setRiassunto(String riassunto) {
        this.riassunto = riassunto;
    }
}
