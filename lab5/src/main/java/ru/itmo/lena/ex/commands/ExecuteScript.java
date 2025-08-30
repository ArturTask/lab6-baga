package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.commands.Command;
import ru.itmo.lena.ex.exception.CommandInterruptedException;
import ru.itmo.lena.ex.exception.UnknownCommandException;
import ru.itmo.lena.ex.manager.CommandManager;
import ru.itmo.lena.ex.util.Console;
import ru.itmo.lena.ex.enumeration.InputFormat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Команда для выполнения скрипта из файла
 */
public class ExecuteScript extends Command {
    private final CommandManager commandManager;
    private final Console console;
    private final Set<String> executingScripts = new HashSet<>();

    public ExecuteScript(CommandManager commandManager, Console console) {
        super("execute_script", "исполнить скрипт из указанного файла");
        this.commandManager = commandManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            console.printError("Требуется путь к файлу");
            return;
        }

        String scriptPath = args[0];
        if (executingScripts.contains(scriptPath)) {
            console.printError("рекурсия, скрипт '" + scriptPath + "' уже выполняется.");
            return;
        }

        try {
            executingScripts.add(scriptPath);
            Scanner originalScanner = console.getScanner();
            File scriptFile = new File(scriptPath);

            if (!scriptFile.exists()) {
                throw new FileNotFoundException("Файл '" + scriptPath + "' не найден");
            }

            Scanner scriptScanner = new Scanner(scriptFile);
            console.setScanner(scriptScanner);
            console.setInputFormat(InputFormat.FILE);

            while (scriptScanner.hasNextLine()) {
                String input = scriptScanner.nextLine().trim();
                if (input.isEmpty()) continue;

                String[] parts = input.split(" ", 2);
                String commandName = parts[0];
                String[] commandArgs = (parts.length > 1) ? parts[1].split(" ") : new String[0];

                // Запрет рекурсивного вызова execute_script
                if (commandName.equalsIgnoreCase("execute_script")) {
                    console.printError("Рекурсия запрещена!");
                    continue;
                }

                try {
                    commandManager.executeCommand(commandName, commandArgs);
                } catch (UnknownCommandException e) {
                    console.printError(e.getMessage());
                } catch (CommandInterruptedException e) {
                    console.printError("Ошибка выполнения команды: " + e.getMessage());
                }
            }

            console.setScanner(originalScanner);
            console.setInputFormat(InputFormat.CONSOLE);
            executingScripts.remove(scriptPath);

        } catch (FileNotFoundException e) {
            console.printError("Файл скрипта не найден: " + e.getMessage());
        } catch (CommandInterruptedException e) {
            console.printError("Ошибка выполнения: " + e.getMessage());
        } finally {
            executingScripts.remove(scriptPath); // На случай непредвиденных ошибок
        }
    }
}