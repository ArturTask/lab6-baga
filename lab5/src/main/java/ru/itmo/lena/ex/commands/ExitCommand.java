package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.manager.CommandManager;
import ru.itmo.lena.ex.util.Console;

/**
 * Команда для завершения программы без сохранения
 */
public class ExitCommand extends Command {
    private final CommandManager commandManager;
    private final Console console;

    public ExitCommand(CommandManager commandManager, Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.commandManager = commandManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        console.println("Программа успешно завершена");
        System.exit(0);
    }
}