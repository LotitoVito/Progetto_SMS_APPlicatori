package it.uniba.dib.sms222329.classi;

import it.uniba.dib.sms222329.database.CoRelatoreDatabase;
import it.uniba.dib.sms222329.database.Database;

public class CoRelatore extends UtenteRegistrato {

    private String organizzazione;
    private int idCorelatore;

    public CoRelatore(int idCorelatore, String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente, String organizzazione) {
        super(nome, cognome, codiceFiscale, email, password, tipoUtente);
        this.idCorelatore = idCorelatore;
        this.organizzazione = organizzazione;
    }

    //Costruttore per registrazione
    public CoRelatore(String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente,  String organizzazione) {
        super(nome, cognome, codiceFiscale, email, password, tipoUtente);
        this.organizzazione = organizzazione;
    }

    public CoRelatore() {
        super();
    }

    public int getIdCorelatore() {return idCorelatore;}

    public void setIdCorelatore(int idCorelatore) {this.idCorelatore = idCorelatore;}

    public String getOrganizzazione() {return organizzazione;}

    public void setOrganizzazione(String organizzazione) {this.organizzazione = organizzazione;}


    public boolean modCoRelatore(String nome, String cognome, String email,
                                 String password, String codFisc, String org, Database db) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.organizzazione=org;
        this.codiceFiscale=codFisc;

        if(CoRelatoreDatabase.modCoRelatore(this, db)){
            return true;
        }
        return false;
    }
}
