package transport;

import java.util.ArrayList;
import java.util.List;

public class PassengerTrain {
    private String name;
    private List<Wagon> wagons;
    public PassengerTrain(String name) {
        this.name = name;
        wagons = new ArrayList<>();
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
    // public String getType() {
    //    return type;
    // }

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
    }

    public List<Wagon> findWagonsByPassengerCapacityRange(int minPassengerCount, int maxPassengerCount) {
        List<Wagon> matchingWagons = new ArrayList<>();
        for (Wagon wagon : wagons) {
            int passengerCapacity = wagon.getPassengerCapacity();
            if (passengerCapacity >= minPassengerCount && passengerCapacity <= maxPassengerCount) {
                matchingWagons.add(wagon);
            }
        }
        return matchingWagons;
    }
    public List<Wagon> getWagons() {
        return wagons;
    }

    public void removeWagon(int index) {
        if (index >= 0 && index < wagons.size()) {
            wagons.remove(index);
        } else {
            System.out.println("Недійсний індекс вагону.");
        }
    }

    public void addWagon(Wagon wagon) {
        wagons.add(wagon);
    }
}
