import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import transport.Command;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @Test
    public void testMenuExecuteCommand() {
        System.setOut(new PrintStream(outContent));

        main.Menu menu = new main.Menu();

        TestCommand testCommand = new TestCommand();

        menu.addCommand("test", testCommand);

        menu.executeCommand("test");

        String expectedOutput = "TestCommand виконалася" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
    private static class TestCommand implements Command {
        @Override
        public void execute() {
            System.out.println("TestCommand виконалася");
        }
    }
}
