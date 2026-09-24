package configLayer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {

        try {

            /*
             * First try config.properties from classpath
             */
            InputStream inputStream =
                    ConfigReader.class
                            .getClassLoader()
                            .getResourceAsStream("config.properties");

            /*
             * If not found in classpath,
             * try src/test/resources/config.properties
             */
            if (inputStream == null) {

                inputStream = new FileInputStream(
                        "src/test/resources/config.properties"
                );
            }

            properties.load(inputStream);

            inputStream.close();

        } catch (Exception e) {

            /*
             * Do not fail the complete framework during
             * class initialization if properties file
             * is unavailable.
             *
             * System properties passed from Jenkins/Maven
             * can still be used.
             */

            System.out.println(
                    "WARNING: config.properties could not be loaded."
            );

            System.out.println(
                    "ConfigReader will use system properties/default values."
            );
        }
    }


    // =========================================================
    // GET PROPERTY
    // =========================================================

    public static String get(String key) {

        if (key == null || key.trim().isEmpty()) {

            return null;
        }


        /*
         * Jenkins / Maven system property gets priority.
         *
         * Example:
         *
         * mvn test -Dbrowser=edge
         *
         * System property:
         * edge
         *
         * config.properties:
         * chrome
         *
         * Result:
         * edge
         */

        String systemProperty =
                System.getProperty(key);

        if (systemProperty != null
                && !systemProperty.trim().isEmpty()) {

            return systemProperty.trim();
        }


        /*
         * Otherwise read from properties file.
         */

        String value =
                properties.getProperty(key);

        if (value == null) {

            return null;
        }

        return value.trim();
    }


    // =========================================================
    // GET PROPERTY WITH DEFAULT VALUE
    // =========================================================

    public static String get(
            String key,
            String defaultValue) {

        String value = get(key);

        if (value == null
                || value.trim().isEmpty()) {

            return defaultValue;
        }

        return value;
    }


    // =========================================================
    // GET BOOLEAN
    // =========================================================

    public static boolean getBoolean(
            String key,
            boolean defaultValue) {

        String value = get(key);

        if (value == null
                || value.trim().isEmpty()) {

            return defaultValue;
        }

        return Boolean.parseBoolean(
                value.trim()
        );
    }


    // =========================================================
    // GET INTEGER
    // =========================================================

    public static int getInt(
            String key,
            int defaultValue) {

        String value = get(key);

        if (value == null
                || value.trim().isEmpty()) {

            return defaultValue;
        }

        try {

            return Integer.parseInt(
                    value.trim()
            );

        } catch (NumberFormatException e) {

            return defaultValue;
        }
    }
}

