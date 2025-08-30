package ru.itmo.lena.ex.manager;

import ru.itmo.lena.ex.commands.*;
import ru.itmo.lena.ex.exception.*;
import ru.itmo.lena.ex.util.Console;
import ru.itmo.lena.ex.util.RunMode;
import ru.itmo.lena.ex.enumeration.InputFormat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Управление командами приложения
 */
public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final Deque<String> commandHistory = new ArrayDeque<>(15);
    private final CollectionManager collectionManager;
    private final Set<String> executingScripts = new HashSet<>();
    private final Console console;
    private Scanner originalScanner; // Сохраняем оригинальный сканер
    private RunMode runMode = RunMode.DEFAULT;

    public CommandManager(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.originalScanner = console.getScanner();
        registerCommands();

    }

    private void registerCommands() {
        // Базовые команды
        commands.put("help", new HelpCommand(this, console));
        commands.put("info", new InfoCommand(collectionManager, console));
        commands.put("show", new ShowCommand(collectionManager, console));
        commands.put("clear", new ClearCommand(collectionManager, console));
        commands.put("save", new SaveCommand(collectionManager, console));
        commands.put("exit", new ExitCommand(this, console));
        // Команды модификации коллекции
        commands.put("add", new AddCommand(console, collectionManager));
        commands.put("update", new UpdateCommand(collectionManager, console));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager, console));
        // Специальные команды
        commands.put("execute_script", new ExecuteScript(this, console));
        commands.put("history", new HistoryCommand(commandHistory, console));
        commands.put("add_if_min", new AddIfMinCommand(collectionManager));
        commands.put("remove_lower", new RemoveLowerCommand(collectionManager, console));
        commands.put("sum_of_meters_above_sea_level", new SumOfMetersCommand(collectionManager, console));
    }

    /**
     * Запуск интерактивного режима
     */
    public void startInteractiveMode() {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;

                processCommand(input);
            } catch (CommandInterruptedException | UnknownCommandException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    /**
     * Обработка команды
     */
    public void processCommand(String input) throws CommandInterruptedException, UnknownCommandException {
        String[] parts = input.split(" ", 2);
        String commandName = parts[0];
        String[] args = parts.length > 1 ? parts[1].split(" ") : new String[0];

        try {
            executeCommand(commandName, args);
        } catch (UnknownCommandException e) {
            System.err.println(e.getMessage());
            System.out.println("Доступные команды: " + String.join(", ", commands.keySet()));
            throw e;
        }
    }

    /**
     * Выполнение команды
     */

    public void executeCommand(String commandName, String[] args)
            throws CommandInterruptedException, UnknownCommandException {
        Command command = commands.get(commandName.toLowerCase());
        if (command == null) {
            throw new UnknownCommandException(
                    "Команда '%s' не найдена. Введите 'help' для списка доступных команд",
                    commandName
            );
        }

        try {
            command.execute(args);
            addToHistory(commandName);
        } catch (CommandExecutionException e) {
            throw new CommandInterruptedException(e.getMessage(), e);
        }
    }
    private void addToHistory(String commandName) {
        // Удаляем старую запись, если история переполнена
        if (commandHistory.size() >= 15) {
            commandHistory.removeFirst();
        }
        commandHistory.addLast(commandName);
    }



    /**
     * Выполнение скрипта
     */
    public void executeScript(String filePath) throws CommandInterruptedException {
        File scriptFile = new File(filePath);

        // Проверка существования файла
        if (!scriptFile.exists()) {
            throw new CommandInterruptedException("Файл не существует: " + filePath);
        }

        // Проверка рекурсии
        if (executingScripts.contains(filePath)) {
            throw new CommandInterruptedException("рекурсия. файл выполняется: " + filePath);
        }

        executingScripts.add(filePath);
        console.setInputFormat(InputFormat.FILE);

        try (Scanner fileScanner = new Scanner(scriptFile)) {
            // Сохраняем оригинальный сканер и устанавливаем файловый
            originalScanner = console.getScanner();
            console.setScanner(fileScanner);

            int lineNumber = 0;
            while (fileScanner.hasNextLine()) {
                lineNumber++;
                String line = fileScanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#")) continue;

                try {
                    console.println("[Строка " + lineNumber + "] Выполнение: " + line);
                    processCommand(line);
                } catch (UnknownCommandException e) {
                    console.printError("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                    throw new CommandInterruptedException("Некорректная команда в скрипте");
                } catch (Exception e) {
                    console.printError("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                    throw new CommandInterruptedException("Выполнение скрипта прервано");
                }
            }
        } catch (FileNotFoundException e) {
            throw new CommandInterruptedException("Файл не найден: " + filePath);}
        finally {
            // Восстанавливаем оригинальные настройки
            console.setInputFormat(InputFormat.CONSOLE);
            console.setScanner(originalScanner);
            executingScripts.remove(filePath);
        }
    }


    public Console getConsole() {
        return console;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }



    public void setRunMode(RunMode runMode) {
        this.runMode = runMode;
    }

    public RunMode getRunMode() {
        return runMode;
    }




    public Map<String, Command> getCommands() {
        return Collections.unmodifiableMap(commands);
    }
}
