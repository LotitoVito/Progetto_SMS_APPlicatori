package it.uniba.dib.sms222329.classi;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class Relatore extends Supervisore {

    private String matricola;

    public Relatore(String matricola, String nome, String cognome, String email, String password) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    //Registrazione account su database
    @Override
    public void registrazione(Database db) throws SQLException {
        //Inserimento dati in tabella Relatore
        String insertRelatore = "INSERT INTO Relatore VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatementRelatore = db.getConnection().prepareStatement(insertRelatore);

        preparedStatementRelatore.setString(1, this.matricola);
        preparedStatementRelatore.setString(2, this.nome);
        preparedStatementRelatore.setString(3, this.cognome);
        preparedStatementRelatore.setString(4, this.email);
        preparedStatementRelatore.setString(5, this.password);

        preparedStatementRelatore.executeUpdate();

        //Inserimento dati in tabella Utente
        String insertUtente = "INSERT INTO Utenti VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatementUtente = db.getConnection().prepareStatement(insertUtente);

        preparedStatementUtente.setString(1, this.email);
        preparedStatementUtente.setString(2, this.nome);
        preparedStatementUtente.setString(3, this.cognome);
        preparedStatementUtente.setString(4, this.password);
        preparedStatementUtente.setInt(5, 1);

        preparedStatementUtente.executeUpdate();
    }
}
