package it.uniba.dib.sms222329.classi;


import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesistaDatabase;

public class Tesista extends UtenteRegistrato {

    private int idTesista;
    private String matricola;
    private float media;
    private int numeroEsamiMancanti;
    private int idUniversitaCorso;

    /**
     * Costruttore vuoto
     */
    public Tesista() {
        super();
    }

    /**
     * Costruttore per la registrazione
     * @param matricola
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param email
     * @param password
     * @param tipoUtente
     * @param media
     * @param numeroEsamiMancanti
     * @param idUniversitaCorso
     */
    public Tesista(String matricola, String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente, float media, int numeroEsamiMancanti, int idUniversitaCorso) {
        super(nome, cognome, codiceFiscale, email, password, tipoUtente);
        this.matricola = matricola;
        this.media = media;
        this.numeroEsamiMancanti = numeroEsamiMancanti;
        this.idUniversitaCorso = idUniversitaCorso;
    }

    /**
     * Costruttore con tutti i parametri
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param email
     * @param password
     * @param tipoUtente
     * @param idTesista
     * @param matricola
     * @param media
     * @param numeroEsamiMancanti
     * @param idUniversitaCorso
     */
    public Tesista(String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente, int idTesista, String matricola, float media, int numeroEsamiMancanti, int idUniversitaCorso) {
        super(nome, cognome, codiceFiscale, email, password, tipoUtente);
        this.idTesista = idTesista;
        this.matricola = matricola;
        this.media = media;
        this.numeroEsamiMancanti = numeroEsamiMancanti;
        this.idUniversitaCorso = idUniversitaCorso;
    }

    public int getIdTesista() {return idTesista;}

    public void setIdTesista(int idTesista) {this.idTesista = idTesista;}

    public String getMatricola() {return matricola;}

    public void setMatricola(String matricola) {this.matricola = matricola;}

    public float getMedia() {return media;}

    public void setMedia(float media) {this.media = media;}

    public int getNumeroEsamiMancanti() {return numeroEsamiMancanti;}

    public void setNumeroEsamiMancanti(int numeroEsamiMancanti) {this.numeroEsamiMancanti = numeroEsamiMancanti;}

    public int getIdUniversitaCorso() {return idUniversitaCorso;}

    public void setIdUniversitaCorso(int idUniversitaCorso) {this.idUniversitaCorso = idUniversitaCorso;}

    /**
     * Metodo di modifica dell'account del Tesista; modifica i valori dell'oggetto istanziato e richiama il metodo modTesista()
     * per aggiornare gli stessi dati sul database.
     * @param matricola
     * @param nome
     * @param cognome
     * @param email
     * @param password
     * @param media
     * @param numEsamiMancanti
     * @param codFisc
     * @param idUniversitaCorso
     * @param db
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean modTesista(String matricola, String nome, String cognome, String email,
                           String password, float media, int numEsamiMancanti, String codFisc, int idUniversitaCorso, Database db) {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.codiceFiscale=codFisc;
        this.matricola=matricola;
        this.media=media;
        this.numeroEsamiMancanti=numEsamiMancanti;
        this.idUniversitaCorso=idUniversitaCorso;

        if (TesistaDatabase.modTesista(this, db)){
            return true;
        }
        return false;
    }
}