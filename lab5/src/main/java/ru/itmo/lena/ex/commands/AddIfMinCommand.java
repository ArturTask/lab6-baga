package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.builder.CityBuilder;
import ru.itmo.lena.ex.entity.City;
import ru.itmo.lena.ex.exception.BuildObjectException;
import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;
import ru.itmo.lena.ex.enumeration.InputFormat;

/**
 * Команда для добавления города, если он меньше минимального в коллекции
 */
public class AddIfMinCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        super("add_if_min", "добавить новый город, если он меньше минимального в коллекции");
        this.collectionManager = collectionManager;
        this.console = collectionManager.getConsole();
    }

    @Override
    public void execute(String[] args) {
        try {
            console.println("* Создание нового города для сравнения:");
            City newCity = new CityBuilder(console, InputFormat.CONSOLE).build();

            if (!newCity.isValid()) {
                console.printError("Поля города не валидны!");
            }

            City minCity = collectionManager.findMin();

            if (minCity != null && newCity.compareTo(minCity) >= 0) {
                console.println("Город не добавлен: не является минимальным в коллекции");
            }

            collectionManager.add(newCity);
            console.printSuccess("Город успешно добавлен! ID: " + newCity.getId());

        } catch (BuildObjectException e) {
            console.printError("Ошибка при создании города: " + e.getMessage());
        }
    }
}