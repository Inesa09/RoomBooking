package com.it.academy.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final String FILE_NAME = "DBconfig.properties";

    private Properties prop;

    public PropertiesReader(){
        try {
            InputStream input = this.getClass().getClassLoader()
                    .getResourceAsStream(FILE_NAME);
//            InputStream input = new FileInputStream(System.getProperty("user.dir") + "/web/WEB-INF/classes/" + FILE_NAME);
            prop = new Properties();
            prop.load(input);
        } catch (IOException e) {
            System.out.println("Sorry, unable to find " + FILE_NAME);
            e.printStackTrace();
        }
    }

    public String getUsername(){
        return prop.getProperty("username");
    }

    public String getPassword(){
        return prop.getProperty("password");
    }
}
