package com.sparta.kch.restassured;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties properties = new Properties();
    static {
        try (InputStream inputStream = AppConfig.class
                .getClassLoader()
                .getResourceAsStream("git.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Unable to find config.properties");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getBaseUri() {
        return properties.getProperty("BASE_URL");
    }
    public static String getRepoPath() {
        return properties.getProperty("PATH");
    }
    public static String getToken() {
        return properties.getProperty("BEARER_TOKEN");
    }
    public  static String getOwner(){
        return properties.getProperty("OWNER");
    }
    public  static String getRepoName(){
        return properties.getProperty("REPO_NAME");
    }
    public static String getUniquePath() {
        return properties.getProperty("UNIQUE_PATH");
    }
}
