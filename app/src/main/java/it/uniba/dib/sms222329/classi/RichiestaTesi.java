package it.uniba.dib.sms222329.classi;

public class RichiestaTesi {

    private int idRichiesta;
    private String messaggio;
    private int idTesi;
    private int idTesista;
    private int accettata;
    private String risposta;

    public RichiestaTesi(int idRichiesta, String messaggio, int idTesi, int idTesista, int accettata, String risposta) {
        this.idRichiesta = idRichiesta;
        this.messaggio = messaggio;
        this.idTesi = idTesi;
        this.idTesista = idTesista;
        this.accettata = accettata;
        this.risposta = risposta;
    }

    public RichiestaTesi(){}

    public int getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(int idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public int getIdTesi() {
        return idTesi;
    }

    public void setIdTesi(int idTesi) {
        this.idTesi = idTesi;
    }

    public int getIdTesista() {
        return idTesista;
    }

    public void setIdTesista(int idTesista) {
        this.idTesista = idTesista;
    }

    public int isAccettata() {
        return accettata;
    }

    public void setAccettata(int accettata) {
        this.accettata = accettata;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public void RispostaRichiestaTesi(int accettata, String risposta){
        this.accettata = accettata;
        this.risposta = risposta;
    }
}
