import org.junit.jupiter.api.Test;
import transport.PassengerTrain;
import transport.Wagon;

import java.util.List;
import static org.junit.Assert.*;

public class PassengerTrainTest {
    private PassengerTrain train;

    @Test
    public void testGetTotalPassengers() {
        train = new PassengerTrain("TestTrain");

        Wagon wagon1 = new Wagon("Wagon1", 30, 50, 3);
        Wagon wagon2 = new Wagon("Wagon2",20, 40, 2);

        train.addWagon(wagon1);
        train.addWagon(wagon2);

        assertEquals(30 + 20, train.getTotalPassengers());
    }

    @Test
    public void testGetTotalLuggage() {
        PassengerTrain train = new PassengerTrain("TestTrain");
        Wagon wagon1 = new Wagon("Wagon1", 50, 100, 3);
        Wagon wagon2 = new Wagon("Wagon2", 30, 50, 2);

        train.addWagon(wagon1);
        train.addWagon(wagon2);

        assertEquals(150, train.getTotalLuggage());
    }

    @Test
    public void testSortWagonsByComfortLevel() {
        PassengerTrain train = new PassengerTrain("TestTrain");
        Wagon wagon1 = new Wagon("Wagon1", 50, 100, 3);
        Wagon wagon2 = new Wagon("Wagon2", 30, 50, 2);

        train.addWagon(wagon1);
        train.addWagon(wagon2);

        train.sortWagonsByComfortLevel();

        assertEquals("Wagon1", train.getWagons().get(0).getName());
        assertEquals("Wagon2", train.getWagons().get(1).getName());
    }

    @Test
    public void testFindWagonsByPassengerCapacityRange() {
        PassengerTrain train = new PassengerTrain("TestTrain");

        Wagon wagon1 = new Wagon("Wagon1", 30, 50, 3);
        Wagon wagon2 = new Wagon("Wagon2", 20, 40, 2);
        Wagon wagon3 = new Wagon("Wagon3", 25, 45, 5);

        train.addWagon(wagon1);
        train.addWagon(wagon2);
        train.addWagon(wagon3);

        List<Wagon> result = train.findWagonsByPassengerCapacityRange(25, 50);

        assertEquals(2, result.size());
        assertTrue(result.contains(wagon1));
        assertTrue(result.contains(wagon3));
    }
    @Test
    public void testRemoveWagon() {
        PassengerTrain train = new PassengerTrain("TestTrain");
        Wagon wagon1 = new Wagon("Wagon1",30, 50, 3);
        Wagon wagon2 = new Wagon("Wagon2", 20, 40, 2);

        train.addWagon(wagon1);
        train.addWagon(wagon2);
        train.removeWagon(1);

        assertEquals(1, train.getWagons().size());
        assertEquals(wagon1, train.getWagons().get(0));
    }

}

