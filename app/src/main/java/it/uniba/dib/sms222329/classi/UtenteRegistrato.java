package it.uniba.dib.sms222329.classi;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.uniba.dib.sms222329.database.Database;

public abstract class UtenteRegistrato extends Utente {

    String nome;
    String cognome;
    String email;
    String password;

    public UtenteRegistrato() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

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

    public void registrazione(Database db) throws SQLException {

    }

    //Registrazione CoRelatore
    /*public void registrazione(Database db) throws SQLException {
        String insert = "INSERT INTO corelatore VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = db.getConnection().prepareStatement(insert);

        preparedStatement.setString(2, this.nome);
        preparedStatement.setString(3, this.cognome);
        preparedStatement.setString(4, this.email);
        preparedStatement.setString(5, this.password);

        preparedStatement.executeUpdate();
    }*/
}
