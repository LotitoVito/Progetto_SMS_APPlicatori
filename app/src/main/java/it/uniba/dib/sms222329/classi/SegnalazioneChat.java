package it.uniba.dib.sms222329.classi;

public class SegnalazioneChat {

    private int idSegnalazioneChat;
    private String oggetto;
    private int idTesiScelta;

    public SegnalazioneChat(int idSegnalazioneChat, String oggetto, int idTesiScelta) {
        this.idSegnalazioneChat = idSegnalazioneChat;
        this.oggetto = oggetto;
        this.idTesiScelta = idTesiScelta;
    }

    public SegnalazioneChat() {
    }

    public SegnalazioneChat(int idSegnalazioneChat) {
        this.idSegnalazioneChat = idSegnalazioneChat;
    }

    public int getIdSegnalazioneChat() {
        return idSegnalazioneChat;
    }

    public void setIdSegnalazioneChat(int idSegnalazioneChat) {this.idSegnalazioneChat = idSegnalazioneChat;}

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public int getIdTesi() {
        return idTesiScelta;
    }

    public void setIdTesi(int idTesiScelta) {
        this.idTesiScelta = idTesiScelta;
    }
}
