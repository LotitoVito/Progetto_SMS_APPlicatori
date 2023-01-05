package it.uniba.dib.sms222329.classi;

public class Vincoli {

    private String tempistiche;
    private int mediaVotiMinima;
    private int esamiNecessari;
    private String capacitaRichieste;

    public Vincoli(String tempistiche, int mediaVotiMinima, int esamiNecessari, String capacitaRichieste) {
        this.tempistiche = tempistiche;
        this.mediaVotiMinima = mediaVotiMinima;
        this.esamiNecessari = esamiNecessari;
        this.capacitaRichieste = capacitaRichieste;
    }

    public String getTempistiche() {
        return tempistiche;
    }

    public void setTempistiche(String tempistiche) {
        this.tempistiche = tempistiche;
    }

    public int getMediaVotiMinima() {
        return mediaVotiMinima;
    }

    public void setMediaVotiMinima(int mediaVotiMinima) {
        this.mediaVotiMinima = mediaVotiMinima;
    }

    public int getEsamiNecessari() {
        return esamiNecessari;
    }

    public void setEsamiNecessari(int esamiNecessari) {
        this.esamiNecessari = esamiNecessari;
    }

    public String getCapacitaRichieste() {
        return capacitaRichieste;
    }

    public void setCapacitaRichieste(String capacitaRichieste) {
        this.capacitaRichieste = capacitaRichieste;
    }
}
