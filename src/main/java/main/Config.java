package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static String botToken;
    private static String botUsername;

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Файл config.properties не найден в resources!");
            }

            Properties prop = new Properties();
            prop.load(input);

            botToken = prop.getProperty("bot.token");
            botUsername = prop.getProperty("bot.username");

            if (botToken == null || botToken.isBlank()) {
                throw new RuntimeException("В config.properties отсутствует bot.token");
            }
            if (botUsername == null || botUsername.isBlank()) {
                throw new RuntimeException("В config.properties отсутствует bot.username");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении config.properties", e);
        }
    }

    public static String getBotToken() {
        return botToken;
    }

    public static String getBotUsername() {
        return botUsername;
    }
}
