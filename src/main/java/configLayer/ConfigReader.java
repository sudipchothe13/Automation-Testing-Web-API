package configLayer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {

        try {

            InputStream inputStream =
                    ConfigReader.class
                            .getClassLoader()
                            .getResourceAsStream("config.properties");

            if (inputStream == null) {

                throw new RuntimeException(
                        "config.properties file not found in src/test/resources"
                );
            }

            properties.load(inputStream);

            inputStream.close();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load config.properties",
                    e
            );
        }
    }

    public static String get(String key) {

        if (key == null || key.trim().isEmpty()) {
            return null;
        }

        /*
         * Jenkins / Maven -D property gets priority.
         *
         * Example:
         * mvn test -Dbrowser=edge
         *
         * This allows Jenkins parameters to override
         * config.properties without changing the file.
         */
        String systemProperty = System.getProperty(key);

        if (systemProperty != null
                && !systemProperty.trim().isEmpty()) {

            return systemProperty.trim();
        }

        String value = properties.getProperty(key);

        if (value == null) {
            return null;
        }

        return value.trim();
    }

    public static String get(String key, String defaultValue) {

        String value = get(key);

        if (value == null || value.isEmpty()) {
            return defaultValue;
        }

        return value;
    }

    public static boolean getBoolean(
            String key,
            boolean defaultValue) {

        String value = get(key);

        if (value == null || value.isEmpty()) {
            return defaultValue;
        }

        return Boolean.parseBoolean(value);
    }

    public static int getInt(
            String key,
            int defaultValue) {

        String value = get(key);

        if (value == null || value.isEmpty()) {
            return defaultValue;
        }

        try {

            return Integer.parseInt(value);

        } catch (NumberFormatException e) {

            return defaultValue;
        }
    }
}

