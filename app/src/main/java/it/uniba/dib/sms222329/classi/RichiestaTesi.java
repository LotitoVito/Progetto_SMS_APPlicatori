package it.uniba.dib.sms222329.classi;

public class RichiestaTesi {

    private int idRichiesta;
    private String messaggio;
    private String capacitàStudente;
    private int idTesi;
    private int idTesista;
    private boolean accettata;
    private String risposta;

    public RichiestaTesi(int idRichiesta, String messaggio, String capacitàStudente, int idTesi, int idTesista, boolean accettata, String risposta) {
        this.idRichiesta = idRichiesta;
        this.messaggio = messaggio;
        this.capacitàStudente = capacitàStudente;
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

    public String getCapacitàStudente() {return capacitàStudente;}

    public void setCapacitàStudente(String capacitàStudente) {this.capacitàStudente = capacitàStudente;}

    public boolean getAccettata() {return accettata;}

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

    public boolean isAccettata() {
        return accettata;
    }

    public void setAccettata(boolean accettata) {
        this.accettata = accettata;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public void RispostaRichiestaTesi(boolean accettata, String risposta){
        this.accettata = accettata;
        this.risposta = risposta;
    }
}
