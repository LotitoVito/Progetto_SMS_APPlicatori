package it.uniba.dib.sms222329.classi;

public class SegnalazioneChat {

    private int idSegnalazioneChat;
    private String oggetto;
    private int idTesiSceta;

    public SegnalazioneChat(int idSegnalazioneChat, String oggetto, int idTesiSceta) {
        this.idSegnalazioneChat = idSegnalazioneChat;
        this.oggetto = oggetto;
        this.idTesiSceta = idTesiSceta;
    }

    public SegnalazioneChat() {
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
        return idTesiSceta;
    }

    public void setIdTesi(int idTesiSceta) {
        this.idTesiSceta = idTesiSceta;
    }
}
