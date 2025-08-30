package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;

/**
 * Команда для очистки коллекции
 */
public class ClearCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public ClearCommand(CollectionManager collectionManager, Console console) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            console.printError("Команда не принимает аргументов");
            return;
        }

        collectionManager.clear();
        console.println("Коллекция успешно очищена");
    }
}