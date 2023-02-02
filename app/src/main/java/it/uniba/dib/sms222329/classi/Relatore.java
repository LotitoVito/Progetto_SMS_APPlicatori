package it.uniba.dib.sms222329.classi;

import java.util.ArrayList;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RelatoreDatabase;

public class Relatore extends UtenteRegistrato {

    private int idRelatore;
    private String matricola;
    private ArrayList<Integer> corsiRelatore;       //Nella tabella del db fa riferimento ad universitacorso_id ovvero l'id della coppia uni/corso

    public Relatore(String matricola, String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente, ArrayList<Integer> corsiRelatore) {
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

    public ArrayList<Integer> getCorsiRelatore() {return corsiRelatore;}

    public void setCorsiRelatore(ArrayList<Integer> corsiRelatore) {this.corsiRelatore = corsiRelatore;}

    public boolean modRelatore(String matricola, String nome, String cognome, String codiceFiscale,
                               String email, String password, ArrayList<Integer> corsiRelatore, Database db) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.codiceFiscale=codiceFiscale;
        this.matricola=matricola;
        this.corsiRelatore = corsiRelatore;

        if(RelatoreDatabase.modRelatore(this, db)){
            return true;
        }
        return false;
    }
}
