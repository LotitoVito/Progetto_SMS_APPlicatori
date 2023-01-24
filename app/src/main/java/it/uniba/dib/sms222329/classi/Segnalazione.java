package it.uniba.dib.sms222329.classi;

public class Segnalazione {

    private int idSegnalazione;
    private String messaggio;
    private boolean riservato;
    //private timestamp
    private int idTesiScelta;

    public Segnalazione(int idSegnalazione, String messaggio, boolean riservato, int idTesiScelta) {
        this.idSegnalazione = idSegnalazione;
        this.messaggio = messaggio;
        this.riservato = riservato;
        this.idTesiScelta = idTesiScelta;
    }

    public int getIdSegnalazione() {
        return idSegnalazione;
    }

    public void setIdSegnalazione(int idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public boolean isRiservato() {
        return riservato;
    }

    public void setRiservato(boolean riservato) {
        this.riservato = riservato;
    }

    public int getIdTesiScelta() {
        return idTesiScelta;
    }

    public void setIdTesiScelta(int idTesiScelta) {
        this.idTesiScelta = idTesiScelta;
    }
}
