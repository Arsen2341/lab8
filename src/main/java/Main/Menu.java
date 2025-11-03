package main;
import transport.Command;
import java.util.HashMap;
import java.util.Map;

public class Menu {
    private Map<String, Command> commands = new HashMap<>();

    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    public void showMenu() {
        System.out.println("Оберіть команду:");
        for (String commandName : commands.keySet()) {
            System.out.println(commandName);
        }
    }

    public void executeCommand(String name) {
        Command command = commands.get(name);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Команда не знайдена");
        }
    }
}
