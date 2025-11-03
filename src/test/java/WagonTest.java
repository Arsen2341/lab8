import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transport.PassengerTrain;
import transport.Wagon;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;

public class WagonTest {

    private Wagon wagon;
    private PassengerTrain train;
    private Wagon.ReadFromFileCommand readFromFileCommand;
    private Wagon.CreateWagonCommand createWagonCommand;
    private Wagon.DeleteWagonCommand deleteWagonCommand;
    private Wagon.SortWagonsCommand sortWagonsCommand;

    @BeforeEach
    public void setUp() {
        wagon = new Wagon("TestWagon", 50, 100, 4);
        train = new PassengerTrain("TestTrain");
        readFromFileCommand = new Wagon.ReadFromFileCommand(train);
        createWagonCommand = new Wagon.CreateWagonCommand(train);
        deleteWagonCommand = new Wagon.DeleteWagonCommand(train);
        sortWagonsCommand = new Wagon.SortWagonsCommand(train);

    }

    @Test
    public void testGetPassengerCapacity() {
        assertEquals(50, wagon.getPassengerCapacity());
    }

    @Test
    public void testGetLuggageCapacity() {
        assertEquals(100, wagon.getLuggageCapacity());
    }

    @Test
    public void testGetComfortLevel() {
        assertEquals(4, wagon.getComfortLevel());
    }

    @Test
    public void testToString() {
        String expected = "Вагон TestWagon: пасажирів - 50, багаж - 100";
        assertEquals(expected, wagon.toString());
    }

    @Test
    public void testAddWagon() {
        Wagon Wagon1 = new Wagon("Купе", 30, 80, 3);

        train.addWagon(Wagon1);

        assertEquals(1, train.getWagons().size());
        assertEquals(Wagon1, train.getWagons().get(0));
    }

    @Test
    public void testReadFromFileCommand() {
        String testData = "Wagon1,50,100,4\nWagon2,40,80,3\nWagon3,50,50,2\nWagon4,100,90,6\nWagon5,20,10,1\nWagon6,20,10,1\n";
        InputStream InputStream = new ByteArrayInputStream(testData.getBytes());
        System.setIn(InputStream);
        readFromFileCommand.execute();
        assertEquals(7, train.getWagons().size());
    }

    @Test
    public void testCreateWagonCommand() {
        // Mock user input
        String userInput = "NewWagon\n60\n120\n5\n";
        InputStream InputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(InputStream);

        createWagonCommand.execute();

        assertEquals(1, train.getWagons().size());
        Wagon newWagon = train.getWagons().get(0);
        assertEquals("NewWagon", newWagon.getName());
        assertEquals(60, newWagon.getPassengerCapacity());
        assertEquals(120, newWagon.getLuggageCapacity());
        assertEquals(5, newWagon.getComfortLevel());
    }

    @Test
    public void testDeleteWagonCommand() {
        Wagon wagon1 = new Wagon("Wagon1", 50, 100, 4);
        Wagon wagon2 = new Wagon("Wagon2", 40, 80, 3);
        train.addWagon(wagon1);
        train.addWagon(wagon2);

        String userInput = "1\n";
        InputStream InputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(InputStream);

        deleteWagonCommand.execute();

        assertEquals(1, train.getWagons().size());
        assertEquals(wagon1, train.getWagons().get(0));
    }
    @Test
    public void testSortWagonsCommand() {
        Wagon wagon1 = new Wagon("Wagon1", 50, 100, 4);
        Wagon wagon2 = new Wagon("Wagon2", 40, 80, 3);
        train.addWagon(wagon1);
        train.addWagon(wagon2);

        sortWagonsCommand.execute();

        assertEquals(wagon1, train.getWagons().get(0));
        assertEquals(wagon2, train.getWagons().get(1));
    }
}
