package it.uniba.dib.sms222329.classi;

import android.util.Log;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RichiestaTesiDatabase;

public class RichiestaTesi {

    /** Costanti per lo stato della richiestaTesi */
    public static final int IN_ATTESA = 1;
    /** Costanti per lo stato della richiestaTesi */
    public static final int ACCETTATO = 2;
    /** Costanti per lo stato della richiestaTesi */
    public static final int RIFIUTATO = 3;

    private int idRichiesta;
    private String messaggio;
    private String capacitàStudente;
    private int idTesi;
    private int idTesista;
    private int stato;
    private String risposta;

    /**
     * Costruttore vuoto
     */
    public RichiestaTesi(){}

    /**
     * Costruttore con tutti i parametri
     * @param idRichiesta
     * @param messaggio
     * @param capacitàStudente
     * @param idTesi
     * @param idTesista
     * @param stato
     * @param risposta
     */
    public RichiestaTesi(int idRichiesta, String messaggio, String capacitàStudente, int idTesi, int idTesista, int stato, String risposta) {
        this.idRichiesta = idRichiesta;
        this.messaggio = messaggio;
        this.capacitàStudente = capacitàStudente;
        this.idTesi = idTesi;
        this.idTesista = idTesista;
        this.stato = stato;
        this.risposta = risposta;
    }

    /**
     * Costruttore per la registrazione
     * @param messaggio
     * @param capacitàStudente
     * @param idTesi
     * @param idTesista
     */
    public RichiestaTesi(String messaggio, String capacitàStudente, int idTesi, int idTesista) {
        this.messaggio = messaggio;
        this.capacitàStudente = capacitàStudente;
        this.idTesi = idTesi;
        this.idTesista = idTesista;
        this.stato = IN_ATTESA;
    }

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

    public int getStato() {return stato;}

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

    public void setStato(int stato) {
        this.stato = stato;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    /**
     * Metodo di accettazione della richiesta; modifica i valori dell'oggetto istanziato e richiama il metodo AccettaRichiestaTesi()
     * per aggiornare gli stessi dati sul database.
     * @param risposta
     * @param db
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean AccettaRichiestaTesi(String risposta, Database db){
        this.stato = ACCETTATO;
        this.risposta = risposta;

        if(RichiestaTesiDatabase.AccettaRichiestaTesi(this, db)){
            return true;
        }
        return false;
    }

    /**
     * Metodo di rifiuto della richiesta; modifica i valori dell'oggetto istanziato e richiama il metodo RifiutaRichiestaTesi()
     * per aggiornare gli stessi dati sul database.
     * @param risposta
     * @param db
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean RifiutaRichiestaTesi(String risposta, Database db){
        this.stato = RIFIUTATO;
        this.risposta = risposta;

        if(RichiestaTesiDatabase.RifiutaRichiestaTesi(this, db)){
            return true;
        }
        return false;
    }
}
