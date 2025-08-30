package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.util.Console;

import java.util.Deque;

/**
 * Команда для вывода истории выполненных команд
 */
public class HistoryCommand extends Command {
    private final Deque<String> commandHistory;
    private final Console console;

    public HistoryCommand(Deque<String> commandHistory, Console console) {
        super("history", "вывести последние 15 команд");
        this.commandHistory = commandHistory;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            console.printError("Команда не принимает аргументов");
            return;
        }

        if (commandHistory.isEmpty()) {
            console.println("История команд пуста");
            return;
        }

        console.println("Последние " + commandHistory.size() + " команд");
        commandHistory.forEach(cmd -> console.println(" - " + cmd));
    }
}