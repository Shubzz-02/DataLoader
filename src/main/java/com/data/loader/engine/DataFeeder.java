package com.data.loader.engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataFeeder extends Thread {
    Connection conn;
    final private List<String> data;
    String query;

    public DataFeeder(Connection conn, List<String> data, String query) {
        this.data = new ArrayList<>(data);
        this.conn = conn;
        this.query = query;
    }

    @Override
    public void run() {
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            for (String li : data) {
                String[] line = li.trim().split(",");
                for (int j = 1; j <= 34; j++) {
                    preparedStmt.setString(j, line[j - 1]);
                }
                preparedStmt.addBatch();
            }
            preparedStmt.executeBatch();
            conn.commit();
            data.clear();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
