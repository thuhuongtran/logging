package com.misa.core.pools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongoConfig {
    private static final String MONGO_CONFIG_FILE = "config/mongodb.properties";
    public static String MONGODB_HOST = "171.244.27.229";
    public static String MONGODB_DATABASE = "misa";
    public static String MONGODB_USERNAME = "misa";
    public static String MONGODB_PASSWORD = "Vp@abc123";
    public static int MONGODB_PORT = 0;

    public static void init() throws IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream(MONGO_CONFIG_FILE);
        prop.load(input);
        MONGODB_HOST = prop.getProperty("host");
        MONGODB_DATABASE = prop.getProperty("database");
        MONGODB_PORT = Integer.parseInt(prop.getProperty("port"));
        MONGODB_USERNAME = prop.getProperty("username");
        MONGODB_PASSWORD = prop.getProperty("password");
    }
}
