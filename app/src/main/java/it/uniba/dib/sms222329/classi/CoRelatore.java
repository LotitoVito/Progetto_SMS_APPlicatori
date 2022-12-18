package it.uniba.dib.sms222329.classi;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public class CoRelatore extends Supervisore {

    private String id;

    public CoRelatore(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Registrazione account su database
    @Override
    public void registrazione(Database db) throws SQLException {
        //Inserimento dati in tabella Relatore
        String insertCorelatore = "INSERT INTO CoRelatore VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatementCorelatore = db.getConnection().prepareStatement(insertCorelatore);

        preparedStatementCorelatore.setString(2, this.nome);
        preparedStatementCorelatore.setString(3, this.cognome);
        preparedStatementCorelatore.setString(4, this.email);
        preparedStatementCorelatore.setString(5, this.password);

        preparedStatementCorelatore.executeUpdate();

        //Inserimento dati in tabella Utente
        String insertUtente = "INSERT INTO Utenti VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatementUtente = db.getConnection().prepareStatement(insertUtente);

        preparedStatementUtente.setString(1, this.email);
        preparedStatementUtente.setString(2, this.nome);
        preparedStatementUtente.setString(3, this.cognome);
        preparedStatementUtente.setString(4, this.password);
        preparedStatementUtente.setInt(5, 2);

        preparedStatementUtente.executeUpdate();
    }
}
