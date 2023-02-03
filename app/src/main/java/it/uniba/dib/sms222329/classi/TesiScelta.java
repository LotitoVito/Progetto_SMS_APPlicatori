package it.uniba.dib.sms222329.classi;

import java.time.LocalDate;

public class TesiScelta extends Tesi{

    private int idTesiScelta;
    private int idTesista;
    private int idCorelatore;
    private byte[] file;
    private LocalDate dataPubblicazione;
    private String riassunto;

    public TesiScelta() {}

    public TesiScelta(int idTesi, int idTesiScelta, int idTesista, int idCorelatore, byte[] file, LocalDate dataPubblicazione, String riassunto) {
        super(idTesi);
        this.idTesiScelta = idTesiScelta;
        this.idTesista = idTesista;
        this.idCorelatore = idCorelatore;
        this.dataPubblicazione = dataPubblicazione;
        this.riassunto = riassunto;
    }

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
}
