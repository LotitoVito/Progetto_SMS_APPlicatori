package it.uniba.dib.sms222329.classi;

import java.time.LocalDate;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TaskDatabase;

public class Task {

    /** Costanti per lo stato del task*/
    public static final int ASSEGNATO = 0;
    /** Costanti per lo stato del task*/
    public static final int INIZIATO = 1;
    /** Costanti per lo stato del task*/
    public static final int IN_COMPLEMAMENTO = 2;
    /** Costanti per lo stato del task*/
    public static final int IN_REVISIONE = 3;
    /** Costanti per lo stato del task*/
    public static final int COMPLETATO = 4;

    private int idTask;
    private String titolo;
    private String descrizione;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String linkMateriale;
    private int stato;
    private int idTesiScelta;

    /**
     * Costruttore vuoto
     */
    public Task(){}

    /**
     * Costruttore con tutti i parametri
     * @param idTask
     * @param titolo
     * @param descrizione
     * @param dataInizio
     * @param dataFine
     * @param linkMateriale
     * @param stato
     * @param idTesiScelta
     */
    public Task(int idTask, String titolo, String descrizione, LocalDate dataInizio, LocalDate dataFine, String linkMateriale, int stato, int idTesiScelta) {
        this.idTask = idTask;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.linkMateriale = linkMateriale;
        this.stato = stato;
        this.idTesiScelta = idTesiScelta;
    }

    /**
     * Costruttore per la registrazione
     * @param titolo
     * @param descrizione
     * @param dataFine
     * @param linkMateriale
     * @param idTesiScelta
     */
    public Task(String titolo, String descrizione, LocalDate dataFine, String linkMateriale, int idTesiScelta) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataInizio = LocalDate.now();
        this.dataFine = dataFine;
        this.linkMateriale = linkMateriale;
        this.stato = ASSEGNATO;
        this.idTesiScelta = idTesiScelta;
    }

    public int getIdTask() {return idTask;}

    public void setIdTask(int idTask) {this.idTask = idTask;}

    public String getTitolo() {return titolo;}

    public void setTitolo(String titolo) {this.titolo = titolo;}

    public String getDescrizione() {return descrizione;}

    public void setDescrizione(String descrizione) {this.descrizione = descrizione;}

    public LocalDate getDataInizio() {return dataInizio;}

    public void setDataInizio(LocalDate dataInizio) {this.dataInizio = dataInizio;}

    public LocalDate getDataFine() {return dataFine;}

    public void setDataFine(LocalDate dataFine) {this.dataFine = dataFine;}

    public String getLinkMateriale() {return linkMateriale;}

    public void setLinkMateriale(String linkMateriale) {this.linkMateriale = linkMateriale;}

    public int getStato() {return stato;}

    public void setStato(int stato) {this.stato = stato;}

    public int getIdTesiScelta() {return idTesiScelta;}

    public void setIdTesiScelta(int idTesiScelta) {this.idTesiScelta = idTesiScelta;}

    /**
     * Metodo di modifica del task da parte del Relatore; modifica i valori dell'oggetto istanziato e richiama il metodo ModificaTask()
     * per aggiornare gli stessi dati sul database.
     * @param titolo
     * @param descrizione
     * @param dataFine
     * @param stato
     * @param db
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean ModificaTask(String titolo, String descrizione, LocalDate dataFine, int stato, Database db){
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataFine = dataFine;
        this.stato = stato;

        if(TaskDatabase.ModificaTask(db, this)){
            return true;
        }
        return false;
    }

    /**
     * Metodo di modifica del task da parte del Tesista; modifica i valori dell'oggetto istanziato e richiama il metodo ModificaTask()
     * per aggiornare gli stessi dati sul database.
     * @param stato
     * @param db
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean ModificaTask(int stato, Database db){
        this.stato = stato;

        if(TaskDatabase.ModificaTask(db, this)){
            return true;
        }
        return false;
    }
}
