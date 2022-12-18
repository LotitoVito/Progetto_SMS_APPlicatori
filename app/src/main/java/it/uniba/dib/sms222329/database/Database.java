package it.uniba.dib.sms222329.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private String url;
    private String username;
    private String password;
    private Connection connection;

    public Database() {
        this.url = "jdbc:mysql://localhost:3306/database";
        this.username = "root";
        this.password = "02102000";
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
