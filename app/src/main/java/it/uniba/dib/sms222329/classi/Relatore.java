package it.uniba.dib.sms222329.classi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import it.uniba.dib.sms222329.database.Database;

public class Relatore extends Supervisore {

    private int idRelatore;
    private String matricola;
    private List corsiRelatore;

    public Relatore(String matricola, String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente, List corsiRelatore) {
        super(nome, cognome, codiceFiscale, email, password, tipoUtente);
        this.matricola = matricola;
        this.corsiRelatore = corsiRelatore;
    }

    public Relatore(){ super();}

    public int getIdRelatore() {return idRelatore;}

    public void setIdRelatore(int idRelatore) {this.idRelatore = idRelatore;}

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public List getCorsiRelatore() {return corsiRelatore;}

    public void setCorsiRelatore(List corsiRelatore) {this.corsiRelatore = corsiRelatore;}

    public void modRelatore(String matricola, String nome, String cognome, String codiceFiscale,
                            String email, String password, List corsiRelatore) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.codiceFiscale=codiceFiscale;
        this.matricola=matricola;
        this.corsiRelatore = corsiRelatore;
    }
}
