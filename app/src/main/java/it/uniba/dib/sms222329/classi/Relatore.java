package it.uniba.dib.sms222329.classi;

import java.util.ArrayList;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.RelatoreDatabase;

public class Relatore extends UtenteRegistrato {

    private int idRelatore;
    private String matricola;
    /**
     * Nella tabella del db fa riferimento ad universitacorso_id ovvero l'id della coppia uni/corso
     */
    private ArrayList<Integer> corsiUniversita;

    /**
     * Costruttore vuoto
     */
    public Relatore(){ super();}

    /**
     * Costruttore del Relatore usato per la registrazione
     * @param matricola
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param email
     * @param password
     * @param tipoUtente
     * @param corsiRelatore
     */
    public Relatore(String matricola, String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente, ArrayList<Integer> corsiRelatore) {
        super(nome, cognome, codiceFiscale, email, password, tipoUtente);
        this.matricola = matricola;
        this.corsiUniversita = corsiRelatore;
    }

    /**
     * Costruttore del Relatore con tutti i parametri
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param email
     * @param password
     * @param tipoUtente
     * @param idRelatore
     * @param matricola
     * @param corsiUniversita
     */
    public Relatore(String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente, int idRelatore, String matricola, ArrayList<Integer> corsiUniversita) {
        super(nome, cognome, codiceFiscale, email, password, tipoUtente);
        this.idRelatore = idRelatore;
        this.matricola = matricola;
        this.corsiUniversita = corsiUniversita;
    }

    public int getIdRelatore() {return idRelatore;}

    public void setIdRelatore(int idRelatore) {this.idRelatore = idRelatore;}

    public String getMatricola() {return matricola;}

    public void setMatricola(String matricola) {this.matricola = matricola;}

    public ArrayList<Integer> getCorsiRelatore() {return corsiUniversita;}

    public void setCorsiRelatore(ArrayList<Integer> corsiRelatore) {this.corsiUniversita = corsiRelatore;}

    /**
     * Metodo di modifica dell'account del Relatore; modifica i valori dell'oggetto istanziato e richiama il metodo modRelatore()
     * per aggiornare gli stessi dati sul database.
     * @param matricola
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param email
     * @param password
     * @param corsiRelatore
     * @param db
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean modRelatore(String matricola, String nome, String cognome, String codiceFiscale,
                               String email, String password, ArrayList<Integer> corsiRelatore, Database db) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.codiceFiscale=codiceFiscale;
        this.matricola=matricola;
        this.corsiUniversita = corsiRelatore;

        if(RelatoreDatabase.modRelatore(this, db)){
            return true;
        }
        return false;
    }
}
