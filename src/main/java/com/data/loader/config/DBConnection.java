package com.data.loader.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    Connection conn;

    public DBConnection() {
        conn = null;
    }

    public Connection getDBConnection(AppGetProperties properties) {
        try {
            conn = DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPass());
            createTable(properties);
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(this.getClass().getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return conn;
    }

    private void createTable(AppGetProperties properties) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute(properties.getTable());
    }


}
