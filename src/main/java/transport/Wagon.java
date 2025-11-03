package transport;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Wagon {
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

    public String getName() {
        return name;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public int getLuggageCapacity() {
        return luggageCapacity;
    }

    public int getComfortLevel() {
        return comfortLevel;
    }

    public String toString() {
        return "Вагон " + name + ": пасажирів - " + passengerCapacity + ", багаж - " + luggageCapacity;
    }

    public static class ReadFromFileCommand implements Command {
        private PassengerTrain train;

        public ReadFromFileCommand(PassengerTrain train) {
            this.train = train;
        }

        @Override
        public void execute() {
            try (BufferedReader reader = new BufferedReader(new FileReader("D:\\лабки ПП\\lab4-6\\lab4-6.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] wagonData = line.split(",");
                    if (wagonData.length == 4) {
                        String wagonName = wagonData[0];
                        int passengerCapacity = Integer.parseInt(wagonData[1]);
                        int luggageCapacity = Integer.parseInt(wagonData[2]);
                        int comfortLevel = Integer.parseInt(wagonData[3]);

                        Wagon wagon = new Wagon(wagonName, passengerCapacity, luggageCapacity, comfortLevel);
                        train.addWagon(wagon);

                        System.out.println("Вагон: " + wagon);
                    } else {
                        System.err.println("Помилка: Неправильний формат рядка у файлі.");
                    }
                }
                System.out.println("Дані про вагони були успішно зчитані з файлу.");
            } catch (IOException e) {
                System.err.println("Помилка при зчитуванні файлу: " + e.getMessage());
            }
        }
    }

    public static class CreateWagonCommand implements Command {
        private PassengerTrain train;

        public CreateWagonCommand(PassengerTrain train) {
            this.train = train;
        }

        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введіть тип вагону: ");
            String wagonType = scanner.nextLine();
            System.out.print("Введіть місткість пасажирів: ");
            int passengerCapacity = scanner.nextInt();
            System.out.print("Введіть місткість багажу: ");
            int luggageCapacity = scanner.nextInt();
            System.out.print("Введіть рівень комфорту: ");
            int comfortLevel = scanner.nextInt();

            Wagon newWagon = new Wagon(wagonType, passengerCapacity, luggageCapacity, comfortLevel);
            train.addWagon(newWagon);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("lab4-6.txt", true))) {
                String wagonData = wagonType + "," + passengerCapacity + "," + luggageCapacity + "," + comfortLevel;
                writer.write(wagonData);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Помилка при записі вагона в файл: " + e.getMessage());
            }

            System.out.println("Вагон був створений та доданий до поїзда.");
        }
    }

    public static class DeleteWagonCommand implements Command {
        private PassengerTrain train;

        public DeleteWagonCommand(PassengerTrain train) {
            this.train = train;
        }

        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введіть індекс вагону для видалення: ");
            int wagonIndex = scanner.nextInt();

            if (wagonIndex >= 0 && wagonIndex < train.getWagons().size()) {
                train.removeWagon(wagonIndex);
                System.out.println("Вагон був видалений з поїзда.");
            } else {
                System.out.println("Недійсний індекс вагону.");
            }
        }
    }

    public static class SortWagonsCommand implements Command {
        private PassengerTrain train;

        public SortWagonsCommand(PassengerTrain train) {
            this.train = train;
        }

        @Override
        public void execute() {
            System.out.println("Кількість пасажирів: " + train.getTotalPassengers());
            System.out.println("Загальна місткість багажу: " + train.getTotalLuggage());
            train.sortWagonsByComfortLevel();
            System.out.println("Вагони, відсортовані за рівнем комфорту:");
            for (Wagon wagon : train.getWagons()) {
                System.out.println(wagon.getName() + " - Рівень комфорту: " + wagon.getComfortLevel());
            }
        }
    }

    public static class FindWagonsByRangeCommand implements Command {
        private PassengerTrain train;

        public FindWagonsByRangeCommand(PassengerTrain train) {
            this.train = train;
        }

        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введіть мінімальну кількість пасажирів: ");
            int minPassengerCount = scanner.nextInt();
            System.out.print("Введіть максимальну кількість пасажирів: ");
            int maxPassengerCount = scanner.nextInt();

            List<Wagon> matchingWagons = train.findWagonsByPassengerCapacityRange(minPassengerCount, maxPassengerCount);

            if (matchingWagons.isEmpty()) {
                System.out.println("Вагонів, що відповідають заданому діапазону параметрів кількості пасажирів, не знайдено.");
            } else {
                System.out.println("Вагони з пасажирами в даному діапазоні: " + minPassengerCount + " і " + maxPassengerCount + ":");
                for (Wagon wagon : matchingWagons) {
                    System.out.println(wagon);
                }
            }
        }
    }

    public static class ExitCommand implements Command {
        @Override
        public void execute() {
            System.exit(0);
        }
    }
}
