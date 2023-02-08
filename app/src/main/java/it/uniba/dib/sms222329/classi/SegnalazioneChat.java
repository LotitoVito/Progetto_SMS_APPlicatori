package it.uniba.dib.sms222329.classi;

public class SegnalazioneChat {

    private int idSegnalazioneChat;
    private String oggetto;
    private int idTesi;

    /**
     * Costruttore vuoto
     */
    public SegnalazioneChat() {}

    /**
     * Costruttore con tutti i parametri
     * @param idSegnalazioneChat
     * @param oggetto
     * @param idTesi
     */
    public SegnalazioneChat(int idSegnalazioneChat, String oggetto, int idTesi) {
        this.idSegnalazioneChat = idSegnalazioneChat;
        this.oggetto = oggetto;
        this.idTesi = idTesi;
    }

    /**
     * Costruttore per MessaggiSegnalazione
     * @param idSegnalazioneChat
     */
    public SegnalazioneChat(int idSegnalazioneChat) {
        this.idSegnalazioneChat = idSegnalazioneChat;
    }

    /**
     * Costruttore per la registrazione
     * @param oggetto
     * @param idTesi
     */
    public SegnalazioneChat(String oggetto, int idTesi) {
        this.oggetto = oggetto;
        this.idTesi = idTesi;
    }

    public int getIdSegnalazioneChat() {return idSegnalazioneChat;}

    public void setIdSegnalazioneChat(int idSegnalazioneChat) {this.idSegnalazioneChat = idSegnalazioneChat;}

    public String getOggetto() {return oggetto;}

    public void setOggetto(String oggetto) {this.oggetto = oggetto;}

    public int getIdTesi() {return idTesi;}

    public void setIdTesi(int idTesi) {this.idTesi = idTesi;}
}
