package Main;
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
        PassengerTrain train = new PassengerTrain("Галичина");
        main.Menu menu = new main.Menu();
        menu.addCommand("Зчитати", new ReadFromFileCommand(train));
        menu.addCommand("Створити", new CreateWagonCommand(train));
        menu.addCommand("Видалити", new DeleteWagonCommand(train));
        menu.addCommand("Відсортувати", new SortWagonsCommand(train));
        menu.addCommand("Знайти", new FindWagonsByRangeCommand(train));
        menu.addCommand("Вийти", new ExitCommand());

        Scanner scanner = new Scanner(System.in);
        while (true) {
            menu.showMenu();
            System.out.print("Введіть команду: ");
            String choice = scanner.nextLine();
            menu.executeCommand(choice);
        }
    }
}
