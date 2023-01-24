package it.uniba.dib.sms222329.classi;

import java.sql.Date;

public class MessaggioSegnalazione extends SegnalazioneChat {

    private  int idMessaggio;
    private String messaggio;
    private Date timestamp;
    private int idMittente;
    private int idSegnalazioneChat;

    public MessaggioSegnalazione(int idMessaggio, String messaggio, Date timestamp, int idMittente, int idSegnalazioneChat) {
        super();
        this.idMessaggio = idMessaggio;
        this.messaggio = messaggio;
        this.timestamp = timestamp;
        this.idMittente = idMittente;
        this.idSegnalazioneChat = idSegnalazioneChat;
    }

    public int getIdMessaggio() {
        return idMessaggio;
    }

    public void setIdMessaggio(int idMessaggio) {
        this.idMessaggio = idMessaggio;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getIdMittente() {
        return idMittente;
    }

    public void setIdMittente(int idMittente) {
        this.idMittente = idMittente;
    }

    public int getIdSegnalazioneChat() {
        return idSegnalazioneChat;
    }

    public void setIdSegnalazioneChat(int idSegnalazioneChat) {
        this.idSegnalazioneChat = idSegnalazioneChat;
    }
}
