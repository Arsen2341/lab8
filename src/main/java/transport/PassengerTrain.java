package transport;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PassengerTrain {
    private static final Logger logger = Logger.getLogger(PassengerTrain.class.getName());
    private String name;
    private List<Wagon> wagons;

    public PassengerTrain(String name) {
        this.name = name;
        wagons = new ArrayList<>();
        logger.log(Level.INFO, "Створено поїзд: {0}", name);
    }

    public int getTotalPassengers() {
        int totalPassengers = 0;
        for (Wagon wagon : wagons) {
            totalPassengers += wagon.getPassengerCapacity();
        }
        return totalPassengers;
    }

    public String getName() {
        return name;
    }

    public int getTotalLuggage() {
        int totalLuggage = 0;
        for (Wagon wagon : wagons) {
            totalLuggage += wagon.getLuggageCapacity();
        }
        return totalLuggage;
    }

    public void sortWagonsByComfortLevel() {
        int n = wagons.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (wagons.get(j).getComfortLevel() < wagons.get(j + 1).getComfortLevel()) {
                    Wagon temp = wagons.get(j);
                    wagons.set(j, wagons.get(j + 1));
                    wagons.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        logger.log(Level.INFO, "Вагони поїзда ''{0}'' відсортовано за комфортом", name);
    }

    public List<Wagon> findWagonsByPassengerCapacityRange(int minPassengerCount, int maxPassengerCount) {
        List<Wagon> matchingWagons = new ArrayList<>();
        for (Wagon wagon : wagons) {
            int passengerCapacity = wagon.getPassengerCapacity();
            if (passengerCapacity >= minPassengerCount && passengerCapacity <= maxPassengerCount) {
                matchingWagons.add(wagon);
            }
        }
        logger.log(Level.INFO, "Пошук: знайдено {0} вагонів в діапазоні [{1} - {2}] пасажирів",
                new Object[]{matchingWagons.size(), minPassengerCount, maxPassengerCount});
        return matchingWagons;
    }

    public List<Wagon> getWagons() {
        return wagons;
    }

    public void removeWagon(int index) {
        if (index >= 0 && index < wagons.size()) {
            Wagon removedWagon = wagons.remove(index);
            logger.log(Level.INFO, "З поїзда ''{0}'' видалено вагон (індекс {1}): {2}",
                    new Object[]{name, index, removedWagon.getName()});
            System.out.println("Вагон був видалений з поїзда.");
        } else {
            System.out.println("Недійсний індекс вагону.");
            logger.log(Level.WARNING, "Спроба видалення вагону за недійсним індексом: {0}", index);
        }
    }

    public void addWagon(Wagon wagon) {
        wagons.add(wagon);
        logger.log(Level.INFO, "До поїзда ''{0}'' додано вагон: {1}", new Object[]{name, wagon.getName()});
    }
}