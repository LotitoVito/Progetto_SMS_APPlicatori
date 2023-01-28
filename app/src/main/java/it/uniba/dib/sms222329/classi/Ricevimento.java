package it.uniba.dib.sms222329.classi;

import java.sql.Time;
import java.util.Date;

public class Ricevimento {

    private int idRicevimento;
    private Date data;
    private Time orario;
    private String argomento;
    private int idTask;
    private int accettazione;
    private String messaggio;

    public Ricevimento(int idRicevimento,Time orario, Date data, String argomento, int idTask, int accettazione, String messaggio) {
        this.idRicevimento = idRicevimento;
        this.orario = orario;
        this.data = data;
        this.argomento = argomento;
        this.idTask = idTask;
        this.accettazione = accettazione;
        this.messaggio = messaggio;
    }

    public Ricevimento(){

    }

    public Ricevimento(Date data, Time orario, String argomento, int idTask, int accettazione, String messaggio) {
        this.data = data;
        this.orario = orario;
        this.argomento = argomento;
        this.idTask = idTask;
        this.accettazione = accettazione;
        this.messaggio = messaggio;
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

    public Time getOrario() {return orario;}

    public void setOrario(Time orario) {this.orario = orario;}

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

    public int getAccettazione() {return accettazione;}

    public void setAccettazione(int accettazione) {this.accettazione = accettazione;}

    public String getMessaggio() {return messaggio;}

    public void setMessaggio(String messaggio) {this.messaggio = messaggio;}
}
