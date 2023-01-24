package it.uniba.dib.sms222329.classi;

import java.sql.Date;

public class Task {

    private int idTask;
    private String descrizione;
    private Date dataInizio;
    private Date dataFine;
    private String linkMateriale;
    private boolean stato;
    private int idTesiScelta;

    public Task(int idTask, String descrizione, Date dataInizio, Date dataFine, String linkMateriale, boolean stato, int idTesiScelta) {
        this.idTask = idTask;
        this.descrizione = descrizione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.linkMateriale = linkMateriale;
        this.stato = stato;
        this.idTesiScelta = idTesiScelta;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public String getLinkMateriale() {
        return linkMateriale;
    }

    public void setLinkMateriale(String linkMateriale) {
        this.linkMateriale = linkMateriale;
    }

    public boolean isStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public int getIdTesiScelta() {
        return idTesiScelta;
    }

    public void setIdTesiScelta(int idTesiScelta) {
        this.idTesiScelta = idTesiScelta;
    }
}
