package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.manager.FileManager;
import ru.itmo.lena.ex.util.Console;

import java.io.IOException;

/**
 * Команда для сохранения коллекции в файл
 */
public class SaveCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public SaveCommand(CollectionManager collectionManager, Console console) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        try {
            FileManager.writeCitiesToCSV(collectionManager.getFileName(), collectionManager.getCollection());
            console.println("Коллекция успешно сохранена в файл: " + collectionManager.getFileName());
        } catch (IOException e) {
            console.printError("Ошибка сохранения: " + e.getMessage());
        }
    }
}