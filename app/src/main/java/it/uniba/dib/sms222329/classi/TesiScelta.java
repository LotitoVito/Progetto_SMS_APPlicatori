package it.uniba.dib.sms222329.classi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDate;

import it.uniba.dib.sms222329.database.Database;
import it.uniba.dib.sms222329.database.TesiSceltaDatabase;

public class TesiScelta extends Tesi{

    /**
     * Costanti per statoCorelatore
     */
    public static int RIFIUTATO = 0;
    public static int IN_ATTESA = 1;
    public static int ACCETTATO = 2;

    private int idTesiScelta;
    private int idTesista;
    private String capacitàStudente;
    private int idCorelatore;
    /**
     * Variabile usata per verificare se il corelatore ha accettato la proposta
     */
    private int statoCorelatore;
    private byte[] file;
    private LocalDate dataPubblicazione;
    private String riassunto;

    /**
     * Costruttore vuoto
     */
    public TesiScelta() {}

    /**
     * Costruttore con tutti i parametri
     * @param idTesi
     * @param idTesiScelta
     * @param idTesista
     * @param capacitàStudente
     * @param idCorelatore
     * @param statoCorelatore
     * @param dataPubblicazione
     * @param riassunto
     */
    public TesiScelta(int idTesi, int idTesiScelta, int idTesista, String capacitàStudente, int idCorelatore, int statoCorelatore, LocalDate dataPubblicazione, String riassunto) {
        super(idTesi);
        this.idTesiScelta = idTesiScelta;
        this.idTesista = idTesista;
        this.capacitàStudente = capacitàStudente;
        this.idCorelatore = idCorelatore;
        this.statoCorelatore = statoCorelatore;
        this.dataPubblicazione = dataPubblicazione;
        this.riassunto = riassunto;
    }

    /**
     * Costruttore per la registrazione
     * @param idTesi
     * @param idTesista
     * @param capacitàStudente
     */
    public TesiScelta(int idTesi, int idTesista, String capacitàStudente) {
        super(idTesi);
        this.idTesista = idTesista;
        this.capacitàStudente = capacitàStudente;
    }

    public int getIdTesiScelta() {return idTesiScelta;}

    public void setIdTesiScelta(int idTesiScelta) {this.idTesiScelta = idTesiScelta;}

    public int getIdTesista() {return idTesista;}

    public void setIdTesista(int idTesista) {this.idTesista = idTesista;}

    public String getCapacitàStudente() {return capacitàStudente;}

    public void setCapacitàStudente(String capacitàStudente) {this.capacitàStudente = capacitàStudente;}

    public int getIdCorelatore() {return idCorelatore;}

    public void setIdCorelatore(int idCorelatore) {this.idCorelatore = idCorelatore;}

    public int getStatoCorelatore() {return statoCorelatore;}

    public void setStatoCorelatore(int statoCorelatore) {this.statoCorelatore = statoCorelatore;}

    public byte[] getFile() {return file;}

    public void setFile(byte[] file) {this.file = file;}

    public LocalDate getDataPubblicazione() {return dataPubblicazione;}

    public void setDataPubblicazione(LocalDate dataPubblicazione) {this.dataPubblicazione = dataPubblicazione;}

    public String getRiassunto() {return riassunto;}

    public void setRiassunto(String riassunto) {this.riassunto = riassunto;}

    /**
     * Metodo di proposta al CoRelatore; ricerca idCorelatore in base alla email passata, modifica i valori dell'oggetto istanziato e richiama il metodo AggiungiCorelatore()
     * per aggiornare gli stessi dati sul database.
     * @param dbClass
     * @param emailCorelatore
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean AggiungiCorelatore(Database dbClass, String emailCorelatore){
        Cursor cursore = dbClass.RicercaDato("SELECT c." + Database.CORELATORE_ID + " FROM " + Database.CORELATORE + " c, " + Database.UTENTI + " u " +
                "WHERE c." + Database.CORELATORE_UTENTEID + "=u." + Database.UTENTI_ID + " AND " + Database.UTENTI_EMAIL + "='" + emailCorelatore + "';");
        cursore.moveToFirst();

        try{
            this.idCorelatore = cursore.getInt(cursore.getColumnIndexOrThrow(Database.CORELATORE_ID));
            this.statoCorelatore = IN_ATTESA;

            if(TesiSceltaDatabase.AggiungiCorelatore(dbClass, this)){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Metodo di rimozione del CoRelatore; modifica i valori dell'oggetto istanziato e richiama il metodo RimuoviCorelatore()
     * per aggiornare gli stessi dati sul database.
     * @param dbClass
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean RimuoviCorelatore(Database dbClass){
        this.idCorelatore = 0;
        this.statoCorelatore = RIFIUTATO;

        if(TesiSceltaDatabase.RimuoviCorelatore(dbClass, this)){
            return true;
        }
        return false;
    }

    /**
     * Metodo di accettazione della proposta da parte del Corelatore; modifica i valori dell'oggetto istanziato e richiama il metodo AccettaRichiesta()
     * per aggiornare gli stessi dati sul database.
     * @param dbClass
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean AccettaRichiesta(Database dbClass){
        this.statoCorelatore = ACCETTATO;

        if(TesiSceltaDatabase.AccettaRichiesta(dbClass, this)){
            return true;
        }
        return false;
    }

    /**
     * Metodo di rifiuto della proposta da parte del Corelatore; modifica i valori dell'oggetto istanziato e richiama il metodo RifiutaRichiesta()
     * per aggiornare gli stessi dati sul database.
     * @param dbClass
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean RifiutaRichiesta(Database dbClass){
        this.statoCorelatore = RIFIUTATO;

        if(TesiSceltaDatabase.RifiutaRichiesta(dbClass, this)){
            return true;
        }
        return false;
    }

    /**
     * Metodo di consegna della tesi scelta da parte del Tesista; modifica i valori dell'oggetto istanziato e richiama il metodo ConsegnaTesiScelta()
     * per aggiornare gli stessi dati sul database.
     * @param dbClass
     * @param riassunto
     * @return  Restituisce true se l'aggiornamento sul database va a buon fine, nel caso contrario restituisce false.
     */
    public boolean ConsegnaTesiScelta(Database dbClass, String riassunto){
        this.riassunto = riassunto;
        this.dataPubblicazione = LocalDate.now();

        if(TesiSceltaDatabase.ConsegnaTesiScelta(dbClass, this)){
            return true;
        }
        return false;
    }
}
