package com.data.loader;

import com.data.loader.config.AppGetProperties;
import com.data.loader.config.DBConnection;
import com.data.loader.engine.DataFeeder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {


    private Connection conn;
    private String query;


    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        AppGetProperties properties = new AppGetProperties();
        //  benchmark(properties.getFile());
        conn = new DBConnection().getDBConnection(properties);
        if (conn == null)
            System.exit(0);
        dataFeed(properties.getFile(), properties.getQuery());
    }

    private void dataFeed(String file, String query) {
        try {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
            List<String> data = new ArrayList<>();
            Files.lines(Paths.get(file))
                    .skip(3)
                    .forEach(line -> {
                                while (executor.getActiveCount() > 85) {
                                    continue;
                                }
                                data.add(line);
                                if (data.size() % 1000 == 0) {
                                    //  System.out.println("Sending data -> " + data.size());
                                    executor.execute(new DataFeeder(conn, data, query));
                                    data.clear();
                                }
                            }
                    );
            executor.shutdown();
            conn.close();
        } catch (IOException | SQLException ex) {
            Logger lgr = Logger.getLogger(this.getClass().getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public void benchmark(String file) {
        long t1 = System.nanoTime();
        List<String> data = new ArrayList<>();
        AtomicLong i = new AtomicLong();
        try {
//            Scanner scanner = new Scanner(new File(file));
//            while (scanner.hasNext()) {
//                i.getAndIncrement();
//                data.add(scanner.nextLine());
//                if (data.size() % 100 == 0) {
//                    //  System.out.println("Sending data -> " + data.size());
//                    //executor.execute(new DataFeeder(conn, data, query));
//                    data.clear();
//                }
//            }

            Files.lines(Paths.get(file))
                    .skip(3)
                    .forEach(line -> {
                                i.getAndIncrement();
                                data.add(line);
                                if (data.size() % 100 == 0) {
                                    //  System.out.println("Sending data -> " + data.size());
                                    //executor.execute(new DataFeeder(conn, data, query));
                                    data.clear();
                                }
                            }
                    );

//            BufferedReader in = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = in.readLine()) != null) {
//                i.getAndIncrement();
//                data.add(line);
//                if (data.size() % 100 == 0) {
//                    //  System.out.println("Sending data -> " + data.size());
//                    //executor.execute(new DataFeeder(conn, data, query));
//                    data.clear();
//                }
//            }
            long t2 = System.nanoTime();

            System.out.println("total records = " + i.get() + " time " + ((t2 - t1) / 1000000000));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
