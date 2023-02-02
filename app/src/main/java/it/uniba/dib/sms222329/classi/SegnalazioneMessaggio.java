package it.uniba.dib.sms222329.classi;

import java.sql.Date;
import java.time.LocalDateTime;

public class SegnalazioneMessaggio extends SegnalazioneChat {

    private  int idMessaggio;
    private String messaggio;
    private LocalDateTime timestamp;
    private int idMittente;                 //idUtente della tabella Utenti

    public SegnalazioneMessaggio() {
    }

    public SegnalazioneMessaggio(int idMessaggio, String messaggio, LocalDateTime timestamp, int idMittente) {
        super();
        this.idMessaggio = idMessaggio;
        this.messaggio = messaggio;
        this.timestamp = timestamp;
        this.idMittente = idMittente;
    }

    public SegnalazioneMessaggio(int idSegnalazioneChat, String messaggio, int idMittente) {
        super(idSegnalazioneChat);
        this.messaggio = messaggio;
        this.idMittente = idMittente;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getIdMittente() {
        return idMittente;
    }

    public void setIdMittente(int idMittente) {
        this.idMittente = idMittente;
    }
}
