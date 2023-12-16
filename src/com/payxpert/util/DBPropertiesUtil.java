package com.payxpert.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
public class DBPropertiesUtil {
    public static String getConnectionString(String fileName) {
        Properties p = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(fileName);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            p.load(fis);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return p.getProperty("database.prefix")+p.getProperty("database.server")+ ":" + p.getProperty("database.port") + "/" +  p.getProperty("database.name") + "?user=" + p.getProperty("database.user") + "&password=" + p.getProperty("database.password");
    }
}

