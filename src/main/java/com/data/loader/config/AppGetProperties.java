package com.data.loader.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppGetProperties {

    private String url;
    private String user;
    private String pass;
    private String file;
    private String table;
    private String query;

    public AppGetProperties() {
        try {
            String propFile = "config.properties";
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFile);
            if (inputStream != null)
                properties.load(inputStream);
            else
                throw new FileNotFoundException("property File " + propFile + " Not Found Please Check");
            url = properties.getProperty("datasource.url");
            user = properties.getProperty("datasource.username");
            pass = properties.getProperty("datasource.password");
            file = properties.getProperty("datasource.file");
            table = properties.getProperty("datasource.table");
            query = properties.getProperty("datasource.query");

            inputStream.close();
        } catch (IOException ex) {
            Logger lgr = Logger.getLogger(AppGetProperties.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public String getTable() {
        return table;
    }

    public String getQuery() {
        return query;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getFile() {
        return file;
    }
}
