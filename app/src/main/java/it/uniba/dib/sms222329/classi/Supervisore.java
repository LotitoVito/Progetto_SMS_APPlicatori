package it.uniba.dib.sms222329.classi;

public abstract class Supervisore extends UtenteRegistrato {
    public Supervisore(String nome, String cognome, String codiceFiscale, String email, String password) {
        super(nome, cognome, codiceFiscale, email, password);
    }

    public Supervisore() {

    }
}
