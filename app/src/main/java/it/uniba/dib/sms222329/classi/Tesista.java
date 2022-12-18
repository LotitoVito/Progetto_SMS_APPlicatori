package it.uniba.dib.sms222329.classi;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class Tesista extends UtenteRegistrato {

    private String matricola;
    //private Tesi tesiScelta;
    private float media;
    private int numeroEsamiSvolti;

    public Tesista(String matricola, String nome, String cognome, String email, String password, float media, int numeroEsamiSvolti) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.media = media;
        this.numeroEsamiSvolti = numeroEsamiSvolti;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public int getEsamiSvolti() {
        return numeroEsamiSvolti;
    }

    public void setEsamiSvolti(int esamiSvolti) {
        this.numeroEsamiSvolti = esamiSvolti;
    }

    //Registrazione account su database
    @Override
    public void registrazione(Database db) throws SQLException {
        //Inserimento dati in tabella Tesista
        String insertTesista = "INSERT INTO Tesista VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatementTesista = db.getConnection().prepareStatement(insertTesista);

        preparedStatementTesista.setString(1, this.matricola);
        preparedStatementTesista.setString(2, this.nome);
        preparedStatementTesista.setString(3, this.cognome);
        preparedStatementTesista.setString(4, this.email);
        preparedStatementTesista.setString(5, this.password);
        preparedStatementTesista.setFloat(6, this.media);
        preparedStatementTesista.setInt(7, this.numeroEsamiSvolti);

        preparedStatementTesista.executeUpdate();

        //Inserimento dati in tabella Utente
        String insertUtente = "INSERT INTO Utenti VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatementUtente = db.getConnection().prepareStatement(insertUtente);

        preparedStatementUtente.setString(1, this.email);
        preparedStatementUtente.setString(2, this.nome);
        preparedStatementUtente.setString(3, this.cognome);
        preparedStatementUtente.setString(4, this.password);
        preparedStatementUtente.setInt(5, 0);

        preparedStatementUtente.executeUpdate();
    }
}