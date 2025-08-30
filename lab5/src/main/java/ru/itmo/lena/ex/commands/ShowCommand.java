package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;

/**
 * Команда для вывода всех элементов коллекции
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public ShowCommand(CollectionManager collectionManager, Console console) {
        super("show", "вывести все элементы коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            console.printError("Команда не принимает аргументов");
            return;
        }

        if (collectionManager.getCollection().isEmpty()) {
            console.println("Коллекция пуста");
            return;
        }

        console.println("Элементы коллекции");
        collectionManager.getCollection().forEach(city ->
                console.println(city.toString())
        );
    }
}