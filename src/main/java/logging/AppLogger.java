package logging;

import java.io.IOException;
import java.util.logging.*;

public class AppLogger {

    private static final Logger LOGGER = Logger.getLogger(AppLogger.class.getName());
    private static final String LOG_FILE = "app_log.txt";

    static {
        try {
            System.setProperty("java.util.logging.SimpleFormatter.format",
                    "%1$tF %1$tT [%4$-7s] - %5$s%n");

            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setUseParentHandlers(false);
            LOGGER.setLevel(Level.INFO);

        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Не вдалося налаштувати логування у файл!\n" + e.getMessage());
        }
    }

    public static void logInfo(String message) {
        LOGGER.log(Level.INFO, message);
    }

    public static void logCritical(String message, Throwable throwable) {

        LOGGER.log(Level.SEVERE, "КРИТИЧНА ПОМИЛКА: " + message, throwable);

        String fullText = message + "\n\n" +
                "Тип помилки: " + throwable.getClass().getName() + "\n" +
                "Опис: " + throwable.getMessage() + "\n" +
                "StackTrace: " + throwable;

        EmailSender.sendAlert("CRITICAL ERROR: " + message, fullText);
    }
}
