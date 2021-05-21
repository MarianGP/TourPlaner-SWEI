package org.garcia.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigurationManager {

    public static String getConfigProperty(String propertyName) {
        Path configDirectory = Paths.get("src", "main", "java", "org", "garcia", "config");
        String absolutePath = configDirectory.toFile().getAbsolutePath();
        String filePath = absolutePath + "\\application.properties";
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(filePath));
            return prop.getProperty(propertyName);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }
}
