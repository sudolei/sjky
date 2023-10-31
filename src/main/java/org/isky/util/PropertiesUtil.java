package org.isky.util;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {

    public static String getVal(String path) {
        Properties prop = new Properties();
        InputStream input = null;
        String val = "";
        try {
            input = new FileInputStream(path);
            prop.load(input);
            val = prop.getProperty("file.folder");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return val;
    }


    public static void setVal(String path, String val) {
        Properties prop = new Properties();
        prop.setProperty("file.folder", val);
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            prop.store(os, "This is a comment");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
