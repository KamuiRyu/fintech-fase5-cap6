package utils;

import java.io.*;
import java.util.*;

public class EnvLoader {
    private static final Properties env = new Properties();

    public static void loadEnv(String filePath) {
        try (InputStream input = new FileInputStream(filePath)) {
            env.load(new java.io.InputStreamReader(input, java.nio.charset.StandardCharsets.UTF_8));
            for (String key : env.stringPropertyNames()) {
                System.setProperty(key, env.getProperty(key));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler .env: " + e.getMessage());
        }
    }

    public static void loadEnvFromResources(String resourceName) {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName)) {
            if (input == null) {
                System.err.println("Arquivo .env não encontrado em resources!");
                return;
            }
            Properties prop = new Properties();
            prop.load(input);
            prop.forEach((k, v) -> System.setProperty(k.toString(), v.toString()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String get(String key) {
        return System.getProperty(key, env.getProperty(key));
    }
}