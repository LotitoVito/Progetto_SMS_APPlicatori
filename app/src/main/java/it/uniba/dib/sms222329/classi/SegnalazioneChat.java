package it.uniba.dib.sms222329.classi;

public class SegnalazioneChat {

    private int idSegnalazioneChat;
    private String oggetto;
    private int idTesi;

    public SegnalazioneChat(int idSegnalazioneChat, String oggetto, int idTesi) {
        this.idSegnalazioneChat = idSegnalazioneChat;
        this.oggetto = oggetto;
        this.idTesi = idTesi;
    }

    public int getIdSegnalazioneChat() {
        return idSegnalazioneChat;
    }

    public void setIdSegnalazioneChat(int idSegnalazioneChat) {
        this.idSegnalazioneChat = idSegnalazioneChat;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public int getIdTesi() {
        return idTesi;
    }

    public void setIdTesi(int idTesi) {
        this.idTesi = idTesi;
    }
}
