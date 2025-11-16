package transport;
import logging.AppLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Wagon {
    private static final Logger logger = Logger.getLogger(Wagon.class.getName());

    private String name;
    private int passengerCapacity;
    private int luggageCapacity;
    private int comfortLevel;

    public Wagon(String name, int passengerCapacity, int luggageCapacity, int comfortLevel) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.luggageCapacity = luggageCapacity;
        this.comfortLevel = comfortLevel;
    }

    public String getName() { return name; }
    public int getPassengerCapacity() { return passengerCapacity; }
    public int getLuggageCapacity() { return luggageCapacity; }
    public int getComfortLevel() { return comfortLevel; }

    @Override
    public String toString() {
        return "Вагон " + name + ": пасажирів - " + passengerCapacity + ", багаж - " + luggageCapacity + ", комфорт - " + comfortLevel;
    }

    public static class ReadFromFileCommand implements Command {
        private static final Logger cmdLogger = Logger.getLogger(ReadFromFileCommand.class.getName());
        private PassengerTrain train;

        public ReadFromFileCommand(PassengerTrain train) {
            this.train = train;
        }

        @Override
        public void execute() {
            String filePath = "D:\\лабки ПП\\lab4-6\\lab4-6.txt";
            cmdLogger.log(Level.INFO, "Запуск зчитування вагонів з файлу: {0}", filePath);

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

                String line;
                int lineNumber = 0;

                while ((line = reader.readLine()) != null) {
                    lineNumber++;

                    if (line.trim().isEmpty()) continue;

                    String[] wagonData = line.split(",");

                    if (wagonData.length != 4) {
                        RuntimeException ex = new RuntimeException(
                                "Неправильний формат рядка №" + lineNumber + ": " + line
                        );

                        cmdLogger.log(Level.SEVERE, ex.getMessage(), ex);
                        AppLogger.logCritical(ex.getMessage(), ex);
                        continue;
                    }

                    try {
                        String name = wagonData[0].trim();
                        int passengers = Integer.parseInt(wagonData[1].trim());
                        int luggage = Integer.parseInt(wagonData[2].trim());
                        int comfort = Integer.parseInt(wagonData[3].trim());

                        train.addWagon(new Wagon(name, passengers, luggage, comfort));

                    } catch (NumberFormatException e) {

                        String msg = "Помилка числа у рядку №" + lineNumber + ": " + line;

                        cmdLogger.log(Level.SEVERE, msg, e);
                        AppLogger.logCritical(msg, e);
                    }
                }

                System.out.println("Дані про вагони успішно зчитано.");
                cmdLogger.info("Зчитування завершено вдало.");

            } catch (IOException e) {

                String msg = "Критична помилка читання файлу: " + filePath;

                cmdLogger.log(Level.SEVERE, msg, e);
                AppLogger.logCritical(msg, e);
            }
        }
    }

    public static class CreateWagonCommand implements Command {
        private static final Logger cmdLogger = Logger.getLogger(CreateWagonCommand.class.getName());
        private PassengerTrain train;

        public CreateWagonCommand(PassengerTrain train) {
            this.train = train;
        }

        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            String filePath = "D:\\лабки ПП\\lab4-6\\lab4-6.txt";
            try {
                System.out.print("Введіть тип вагону: ");
                String wagonType = scanner.nextLine();
                System.out.print("Введіть місткість пасажирів: ");
                int passengerCapacity = scanner.nextInt();
                System.out.print("Введіть місткість багажу: ");
                int luggageCapacity = scanner.nextInt();
                System.out.print("Введіть рівень комфорту: ");
                int comfortLevel = scanner.nextInt();
                scanner.nextLine();

                Wagon newWagon = new Wagon(wagonType, passengerCapacity, luggageCapacity, comfortLevel);
                train.addWagon(newWagon);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                    String wagonData = wagonType + "," + passengerCapacity + "," + luggageCapacity + "," + comfortLevel;
                    writer.write(wagonData);
                    writer.newLine();
                    cmdLogger.log(Level.INFO, "Новий вагон ''{0}'' збережено у файл.", wagonType);
                } catch (IOException e) {
                    System.err.println("Помилка при записі вагона в файл: " + e.getMessage());
                    cmdLogger.log(Level.SEVERE, "Критична помилка при записі у файл: " + filePath, e);
                }

                System.out.println("Вагон був створений та доданий до поїзда.");
                cmdLogger.log(Level.INFO, "Вагон ''{0}'' створений та доданий до поїзда.", wagonType);

            } catch (InputMismatchException e) {
                System.err.println("Помилка вводу: Очікувалося число.");
                cmdLogger.log(Level.WARNING, "Неправильний ввід користувача. Очікувалося число.", e);
                scanner.nextLine();
            }
        }
    }

    public static class DeleteWagonCommand implements Command {
        private static final Logger cmdLogger = Logger.getLogger(DeleteWagonCommand.class.getName());
        private PassengerTrain train;
        private final String filePath = "D:\\лабки ПП\\lab4-6\\lab4-6.txt";

        public DeleteWagonCommand(PassengerTrain train) {
            this.train = train;
        }

        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);

            try {
                System.out.print("Введіть індекс вагону для видалення: ");
                int index = scanner.nextInt();
                scanner.nextLine();

                if (index < 0 || index >= train.getWagons().size()) {
                    System.out.println("❌ Невірний індекс!");
                    return;
                }

                // Видаляємо з памʼяті
                Wagon removed = train.getWagons().remove(index);

                System.out.println("Вагон \"" + removed.getName() + "\" видалено.");

                // Перезаписуємо файл
                rewriteFile();

                cmdLogger.info("Видалено вагон: " + removed.getName());

            } catch (InputMismatchException e) {
                String msg = "Помилка вводу індекса";
                System.err.println(msg);
                cmdLogger.log(Level.WARNING, msg, e);
                AppLogger.logCritical(msg, e);
            }
        }

        private void rewriteFile() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {

                for (Wagon w : train.getWagons()) {
                    writer.write(w.getName() + "," + w.getPassengerCapacity() + "," + w.getLuggageCapacity() + "," + w.getComfortLevel());
                    writer.newLine();
                }

                System.out.println("Файл оновлено після видалення.");
            } catch (IOException e) {
                String msg = "Помилка перезапису файлу";
                System.err.println(msg);
                cmdLogger.log(Level.SEVERE, msg, e);
                AppLogger.logCritical(msg, e);
            }
        }
    }

    public static class SortWagonsCommand implements Command {
        private static final Logger cmdLogger = Logger.getLogger(SortWagonsCommand.class.getName());
        private PassengerTrain train;

        public SortWagonsCommand(PassengerTrain train) {
            this.train = train;
        }

        @Override
        public void execute() {
            cmdLogger.info("Запит на сортування вагонів та розрахунок.");
            System.out.println("Загальна кількість пасажирів: " + train.getTotalPassengers());
            System.out.println("Загальна місткість багажу: " + train.getTotalLuggage());
            train.sortWagonsByComfortLevel();
            System.out.println("Вагони, відсортовані за рівнем комфорту (від вищого до нижчого):");

            if (train.getWagons().isEmpty()) {
                System.out.println("У поїзді немає вагонів.");
                cmdLogger.info("Спроба сортування, але у поїзді немає вагонів.");
            } else {
                for (Wagon wagon : train.getWagons()) {
                    System.out.println(wagon.getName() + " - Рівень комфорту: " + wagon.getComfortLevel());
                }
            }
        }
    }

    public static class FindWagonsByRangeCommand implements Command {
        private static final Logger cmdLogger = Logger.getLogger(FindWagonsByRangeCommand.class.getName());
        private PassengerTrain train;

        public FindWagonsByRangeCommand(PassengerTrain train) {
            this.train = train;
        }

        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.print("Введіть мінімальну кількість пасажирів: ");
                int minPassengerCount = scanner.nextInt();
                System.out.print("Введіть максимальну кількість пасажирів: ");
                int maxPassengerCount = scanner.nextInt();
                scanner.nextLine();

                cmdLogger.log(Level.INFO, "Запит на пошук вагонів за діапазоном пасажирів: {0}-{1}",
                        new Object[]{minPassengerCount, maxPassengerCount});
                List<Wagon> matchingWagons = train.findWagonsByPassengerCapacityRange(minPassengerCount, maxPassengerCount);

                if (matchingWagons.isEmpty()) {
                    System.out.println("Вагонів, що відповідають заданому діапазону, не знайдено.");
                    cmdLogger.info("Результат пошуку: вагонів не знайдено.");
                } else {
                    System.out.println("Вагони з пасажирами в даному діапазоні (" + minPassengerCount + " - " + maxPassengerCount + "):");
                    for (Wagon wagon : matchingWagons) {
                        System.out.println(wagon);
                    }
                }
            } catch (InputMismatchException e) {
                System.err.println("Помилка вводу: Очікувалося число.");
                cmdLogger.log(Level.WARNING, "Неправильний ввід користувача при пошуку. Очікувалося число.", e);
                scanner.nextLine();
            }
        }
    }

    public static class ExitCommand implements Command {
        private static final Logger cmdLogger = Logger.getLogger(ExitCommand.class.getName());
        @Override
        public void execute() {
            cmdLogger.info("Команда 'Вийти'. Завершення роботи програми.");
            System.out.println("Завершення роботи...");
            System.exit(0);
        }
    }
}