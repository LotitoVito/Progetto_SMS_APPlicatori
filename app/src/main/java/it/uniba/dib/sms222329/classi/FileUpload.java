package it.uniba.dib.sms222329.classi;


import java.time.LocalDate;
import java.util.Date;

public class FileUpload {

    int idUtente;
    String nome;
    String url;
    Date date;

    /**
     * Costruttore vuoto
     */
    public FileUpload(){}

    /**
     * Costruttore usato per salvare i file su firebase
     * @param idUtente
     * @param nome
     * @param url
     * @param date
     */
    public FileUpload(int idUtente, String nome, String url, Date date) {
        this.idUtente = idUtente;
        this.nome = nome;
        this.url = url;
        this.date = date;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "-Caricato da:"+nome+" il "+ date;
    }
}
