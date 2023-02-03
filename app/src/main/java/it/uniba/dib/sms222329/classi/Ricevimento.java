package it.uniba.dib.sms222329.classi;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RicevimentoDatabase;

public class Ricevimento {

    public static final int ACCETTATO = 1;
    public static final int RIFIUTATO = 2;
    public static final int IN_ATTESA_RELATORE = 3;
    public static final int IN_ATTESA_TESISTA = 4;

    private int idRicevimento;
    private LocalDate data;
    private LocalTime orario;
    private String argomento;
    private int idTask;
    private int accettazione;
    private String messaggio;

    public Ricevimento(){}

    public Ricevimento(int idRicevimento, LocalTime orario, LocalDate data, String argomento, int idTask, int accettazione, String messaggio) {
        this.idRicevimento = idRicevimento;
        this.orario = orario;
        this.data = data;
        this.argomento = argomento;
        this.idTask = idTask;
        this.accettazione = accettazione;
        this.messaggio = messaggio;
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

    public boolean ModificaRicevimento(Database db, LocalDate data, LocalTime orario){
        this.data = data;
        this.orario = orario;

        if(RicevimentoDatabase.ModificaRicevimento(db, this)){
            return true;
        }
        return false;
    }
}
