package it.uniba.dib.sms222329.classi;

import java.time.LocalDate;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TaskDatabase;

public class Task {

    public static final int ASSEGNATO = 1;
    public static final int IN_COMPLEMAMENTO = 2;
    public static final int COMPLETATO = 3;

    private int idTask;
    private String titolo;
    private String descrizione;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private byte[] linkMateriale;
    private int stato;
    private int idTesiScelta;

    public Task(){}

    public Task(int idTask, String titolo, String descrizione, LocalDate dataInizio, LocalDate dataFine, byte[] linkMateriale, int stato, int idTesiScelta) {
        this.idTask = idTask;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.linkMateriale = linkMateriale;
        this.stato = stato;
        this.idTesiScelta = idTesiScelta;
    }

    public Task(String titolo, String descrizione, byte[] linkMateriale, int idTesiScelta) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataInizio = LocalDate.now();
        this.linkMateriale = linkMateriale;
        this.stato = ASSEGNATO;
        this.idTesiScelta = idTesiScelta;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getTitolo() {return titolo;}

    public void setTitolo(String titolo) {this.titolo = titolo;}

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public byte[] getLinkMateriale() {
        return linkMateriale;
    }

    public void setLinkMateriale(byte[] linkMateriale) {
        this.linkMateriale = linkMateriale;
    }

    public int getStato() {return stato;}

    public void setStato(int stato) {
        this.stato = stato;
    }

    public int getIdTesiScelta() {
        return idTesiScelta;
    }

    public void setIdTesiScelta(int idTesiScelta) {
        this.idTesiScelta = idTesiScelta;
    }

    public boolean ModificaTask(String titolo, String descrizione, int stato, Database db){
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.stato = stato;
        if(stato == COMPLETATO){
            this.dataFine = LocalDate.now();
        }

        if(TaskDatabase.ModificaTask(db, this)){
            return true;
        }
        return false;
    }

    public boolean ModificaTask(int stato, Database db){
        this.stato = stato;
        if(stato == COMPLETATO){
            this.dataFine = LocalDate.now();
        }

        if(TaskDatabase.ModificaTask(db, this)){
            return true;
        }
        return false;
    }
}
