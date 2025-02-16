package db.coursework.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@UtilityClass
public class PropUtils {
    private static final String PATH = "application.properties";

    public static String get(String key) {
        try (InputStream inputStream = PropUtils.class.getClassLoader().getResourceAsStream(PATH)) {
            if (inputStream == null) {
                throw new IOException("Property file not found: " + PATH);
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            String value = properties.getProperty(key);
            if (value == null) {
                throw new IllegalArgumentException("Property " + key + " not found");
            }

            return value;
        } catch (IOException | IllegalArgumentException e) {
            log.error("Error reading properties file or invalid property: {}", key, e);
            throw new RuntimeException("Failed to load property", e);  // Рекомендуется выбрасывать RuntimeException
        }
    }
}
