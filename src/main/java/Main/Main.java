package Main;

import logging.AppLogger;
import transport.PassengerTrain;
import transport.Wagon.ReadFromFileCommand;
import transport.Wagon.CreateWagonCommand;
import transport.Wagon.DeleteWagonCommand;
import transport.Wagon.SortWagonsCommand;
import transport.Wagon.FindWagonsByRangeCommand;
import transport.Wagon.ExitCommand;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AppLogger.logInfo("=======================================");
        AppLogger.logInfo("Запуск програми 'PassengerTrain'");

        //тестове скидання на пошту))))))))))
        AppLogger.logCritical("ТЕСТ EMAIL", new RuntimeException("Тестова RuntimeException"));


        PassengerTrain train = new PassengerTrain("Галичина");

        Menu menu = new Menu();
        menu.addCommand("Зчитати", new ReadFromFileCommand(train));
        menu.addCommand("Створити", new CreateWagonCommand(train));
        menu.addCommand("Видалити", new DeleteWagonCommand(train));
        menu.addCommand("Відсортувати", new SortWagonsCommand(train));
        menu.addCommand("Знайти", new FindWagonsByRangeCommand(train));
        menu.addCommand("Вийти", new ExitCommand());

        menu.addCommand("Тест емаіл", () -> {
            throw new RuntimeException("Це ТЕСТОВА ПОМИЛКА для перевірки email!");
        });

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                menu.showMenu();
                System.out.print("Введіть команду: ");
                String choice = scanner.nextLine();

                AppLogger.logInfo("Користувач обрав команду: " + choice);

                menu.executeCommand(choice);

            } catch (Exception e) {

                System.err.println("❌ Критична помилка! Деталі відправлено на email.");


                AppLogger.logCritical("Помилка під час виконання команди", e);
            }
        }
    }
}
