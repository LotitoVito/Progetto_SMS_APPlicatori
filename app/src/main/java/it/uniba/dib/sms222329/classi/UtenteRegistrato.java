package it.uniba.dib.sms222329.classi;


import java.io.Serializable;

public class UtenteRegistrato implements Serializable {

    int idUtente;
    String nome;
    String cognome;
    String codiceFiscale;
    String email;
    String password;
    int tipoUtente;

    public UtenteRegistrato(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UtenteRegistrato(String nome, String cognome, String codiceFiscale, String email, String password, int tipoUtente) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.password = password;
        this.tipoUtente = tipoUtente;
    }

    public UtenteRegistrato(String nome, String cognome, String codiceFiscale, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.password = password;
    }

    public UtenteRegistrato() {
    }

    public int getIdUtente() {return idUtente;}

    public void setIdUtente(int idUtente) {this.idUtente = idUtente;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {this.nome = nome;}

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {return codiceFiscale;}

    public void setCodiceFiscale(String codiceFiscale) {this.codiceFiscale = codiceFiscale;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipoUtente() {
        return tipoUtente;
    }

    public void setTipoUtente(int tipoUtente) {this.tipoUtente = tipoUtente;}

}
