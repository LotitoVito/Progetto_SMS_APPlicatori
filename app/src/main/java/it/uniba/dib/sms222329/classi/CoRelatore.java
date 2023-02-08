package it.uniba.dib.sms222329.classi;

import it.uniba.dib.sms222329.database.CoRelatoreDatabase;
import it.uniba.dib.sms222329.database.Database;

public class CoRelatore extends UtenteRegistrato {

    private String organizzazione;
    private int idCorelatore;


    /**
     * Costruttore vuoto
     */
    public CoRelatore() {
        super();
    }

    /**
     * Costruttore con tutti i parametri del CoRelatore
     * @param idUtente
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param email
     * @param password
     * @param tipoUtente
     * @param organizzazione
     * @param idCorelatore
     */
    public CoRelatore(int idUtente, String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente, String organizzazione, int idCorelatore) {
        super(idUtente, nome, cognome, codiceFiscale, email, password, tipoUtente);
        this.organizzazione = organizzazione;
        this.idCorelatore = idCorelatore;
    }

    /**
     * Costruttore usato per la registrazione del CoRelatore
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param email
     * @param password
     * @param tipoUtente
     * @param organizzazione
     */
    public CoRelatore(String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente,  String organizzazione) {
        super(nome, cognome, codiceFiscale, email, password, tipoUtente);
        this.organizzazione = organizzazione;
    }

    public int getIdCorelatore() {return idCorelatore;}

    public void setIdCorelatore(int idCorelatore) {this.idCorelatore = idCorelatore;}

    public String getOrganizzazione() {return organizzazione;}

    public void setOrganizzazione(String organizzazione) {this.organizzazione = organizzazione;}

    /**
     * Metodo di modifica dell'account del CoRelatore; modifica i valori dell'oggetto istanziato e richiama il metodo modCorelatore()
     * per aggiornare gli stessi dati sul database.
     * @param nome
     * @param cognome
     * @param email
     * @param password
     * @param codFisc
     * @param org
     * @param db
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
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
