package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.builder.CityBuilder;
import ru.itmo.lena.ex.entity.City;
import ru.itmo.lena.ex.exception.BuildObjectException;
import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;
import ru.itmo.lena.ex.enumeration.InputFormat;

/**
 * Команда для удаления элементов, меньших чем заданный
 */
public class RemoveLowerCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public RemoveLowerCommand(CollectionManager collectionManager, Console console) {
        super("remove_lower", "удалить элементы, меньшие чем заданный");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        try {
            // Создаем объект для сравнения
            City cityToCompare = new CityBuilder(console, InputFormat.CONSOLE).build();

            // Удаляем элементы и получаем количество удаленных
            int removedCount = collectionManager.removeLower(cityToCompare);
            console.println("Удалено элементов: " + removedCount);

        } catch (BuildObjectException e) {
            console.printError("Ошибка при создании объекта: " + e.getMessage());
        }
    }
}