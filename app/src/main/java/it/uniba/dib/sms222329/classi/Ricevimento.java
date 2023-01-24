package it.uniba.dib.sms222329.classi;

import java.sql.Date;

public class Ricevimento {

    private int idRicevimento;
    private Date data;
    //private orario
    private String argomento;
    private int idTask;

    public Ricevimento(int idRicevimento, Date data, String argomento, int idTask) {
        this.idRicevimento = idRicevimento;
        this.data = data;
        this.argomento = argomento;
        this.idTask = idTask;
    }

    public int getIdRicevimento() {
        return idRicevimento;
    }

    public void setIdRicevimento(int idRicevimento) {
        this.idRicevimento = idRicevimento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getArgomento() {
        return argomento;
    }

    public void setArgomento(String argomento) {
        this.argomento = argomento;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
}
