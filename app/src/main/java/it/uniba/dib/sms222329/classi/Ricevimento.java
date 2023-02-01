package it.uniba.dib.sms222329.classi;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Ricevimento {

    public static final int ACCETTATO = 1;
    public static final int IN_ATTESA = 2;
    public static final int RIFIUTATO = 3;

    private int idRicevimento;
    private LocalDate data;
    private LocalTime orario;
    private String argomento;
    private int idTask;
    private int accettazione;
    private String messaggio;

    public Ricevimento(int idRicevimento, LocalTime orario, LocalDate data, String argomento, int idTask, int accettazione, String messaggio) {
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

    public Ricevimento(LocalDate data, LocalTime orario, String argomento, int idTask, int accettazione, String messaggio) {
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {this.data = data;}

    public LocalTime getOrario() {return orario;}

    public void setOrario(LocalTime orario) {this.orario = orario;}

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
