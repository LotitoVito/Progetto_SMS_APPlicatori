package it.uniba.dib.sms222329.classi;


import java.time.LocalDate;

public class FileUpload {

    private int idUtente;
    private String nome;
    private String url;
    private LocalDate date;

    public FileUpload(int idUtente, String nome, String url, LocalDate timestamp) {
        this.idUtente = idUtente;
        this.nome = nome;
        this.url = url;
        this.date = timestamp;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "-Caricato da:"+nome+" il "+ date;
    }
}
