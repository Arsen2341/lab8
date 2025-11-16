package Main;

import logging.AppLogger;
import transport.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu {

    private static final Logger logger = Logger.getLogger(Menu.class.getName());
    private Map<String, Command> commands = new HashMap<>();

    private int wrongAttempts = 0;

    public void addCommand(String name, Command command) {
        commands.put(name, command);
        logger.log(Level.FINE, "Додано команду: {0}", name);
    }

    public void showMenu() {
        System.out.println("\n--- МЕНЮ ---");
        System.out.println("Оберіть команду:");
        for (String commandName : commands.keySet()) {
            System.out.println(commandName);
        }
        logger.info("Показано меню");
    }

    public void executeCommand(String name) {
        Command command = commands.get(name);

        if (command != null) {

            wrongAttempts = 0;

            logger.log(Level.INFO, "Виконання команди: {0}", name);

            try {
                command.execute();
            } catch (Exception e) {
                System.err.println("Під час виконання команди сталася неочікувана помилка.");
                logger.log(Level.SEVERE, "Неочікувана помилка при виконанні команди: " + name, e);


                AppLogger.logCritical("Помилка під час виконання команди: " + name, e);
            }

        } else {

            System.out.println("Команда не знайдена");
            logger.log(Level.WARNING, "Спроба виконати неіснуючу команду: {0}", name);

            wrongAttempts++;


            AppLogger.logCritical("Введено неправильну команду: " + name,
                    new IllegalArgumentException("Невідома команда: " + name));

            if (wrongAttempts >= 3) {
                throw new IllegalStateException("3 рази підряд введено неправильну команду!");
            }
        }
    }
}
