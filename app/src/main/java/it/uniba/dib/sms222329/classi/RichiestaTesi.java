package it.uniba.dib.sms222329.classi;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RichiestaTesiDatabase;

public class RichiestaTesi {

    public static final int ACCETTATO = 1;
    public static final int IN_ATTESA = 2;
    public static final int RIFIUTATO = 3;

    private int idRichiesta;
    private String messaggio;
    private String capacitàStudente;
    private int idTesi;
    private int idTesista;
    private int accettata;
    private String risposta;

    public RichiestaTesi(int idRichiesta, String messaggio, String capacitàStudente, int idTesi, int idTesista, int accettata, String risposta) {
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

    public int getAccettata() {return accettata;}

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

    public void setAccettata(int accettata) {
        this.accettata = accettata;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public boolean AccettaRichiestaTesi(String risposta, Database db){
        this.accettata = ACCETTATO;
        this.risposta = risposta;

        if(RichiestaTesiDatabase.AccettaRichiestaTesi(this, db)){
            return true;
        }
        return false;
    }

    public boolean RifiutaRichiestaTesi(String risposta, Database db){
        this.accettata = RIFIUTATO;
        this.risposta = risposta;

        if(RichiestaTesiDatabase.RifiutaRichiestaTesi(this, db)){
            return true;
        }
        return false;
    }
}
