package it.uniba.dib.sms222329.classi;

import java.time.LocalDate;
import java.time.LocalTime;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RicevimentoDatabase;

public class Ricevimento {

    /** Costanti per lo stato del ricevimento */
    public static final int ACCETTATO = 1;
    /** Costanti per lo stato del ricevimento */
    public static final int RIFIUTATO = 2;
    /** Costanti per lo stato del ricevimento */
    public static final int IN_ATTESA_RELATORE = 3;
    /** Costanti per lo stato del ricevimento */
    public static final int IN_ATTESA_TESISTA = 4;

    private int idRicevimento;
    private LocalDate data;
    private LocalTime orario;
    private int idTask;
    private int stato;
    private String messaggio;

    /**
     * Costruttore vuoto
     */
    public Ricevimento(){}

    /**
     * Costruttore con tutti i parametri
     * @param idRicevimento
     * @param orario
     * @param data
     * @param idTask
     * @param stato
     * @param messaggio
     */
    public Ricevimento(int idRicevimento, LocalTime orario, LocalDate data, int idTask, int stato, String messaggio) {
        this.idRicevimento = idRicevimento;
        this.orario = orario;
        this.data = data;
        this.idTask = idTask;
        this.stato = stato;
        this.messaggio = messaggio;
    }

    /**
     * Costruttore per la registrazione
     * @param data
     * @param orario
     * @param idTask
     * @param stato
     * @param messaggio
     */
    public Ricevimento(LocalDate data, LocalTime orario, int idTask, int stato, String messaggio) {
        this.data = data;
        this.orario = orario;
        this.idTask = idTask;
        this.stato = stato;
        this.messaggio = messaggio;
    }

    public int getIdRicevimento() {return idRicevimento;}

    public void setIdRicevimento(int idRicevimento) {this.idRicevimento = idRicevimento;}

    public LocalDate getData() {return data;}

    public void setData(LocalDate data) {this.data = data;}

    public LocalTime getOrario() {return orario;}

    public void setOrario(LocalTime orario) {this.orario = orario;}

    public int getIdTask() {return idTask;}

    public void setIdTask(int idTask) {this.idTask = idTask;}

    public int getStato() {return stato;}

    public void setStato(int stato) {this.stato = stato;}

    public String getMessaggio() {return messaggio;}

    public void setMessaggio(String messaggio) {this.messaggio = messaggio;}

    /**
     * Metodo di modifica del ricevimento; modifica i valori dell'oggetto istanziato e richiama il metodo ModificaRicevimento()
     * per aggiornare gli stessi dati sul database.
     * @param db
     * @param data
     * @param orario
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean ModificaRicevimento(Database db, LocalDate data, LocalTime orario){
        this.data = data;
        this.orario = orario;
        this.stato = IN_ATTESA_TESISTA;

        if(RicevimentoDatabase.ModificaRicevimento(db, this)){
            return true;
        }
        return false;
    }
}
