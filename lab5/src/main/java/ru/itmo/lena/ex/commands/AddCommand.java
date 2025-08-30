package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.builder.CityBuilder;
import ru.itmo.lena.ex.entity.City;
import ru.itmo.lena.ex.exception.BuildObjectException;
import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;
import ru.itmo.lena.ex.enumeration.InputFormat;

public class AddCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public AddCommand(Console console, CollectionManager collectionManager) {
        super("add", "добавить новый город");
        this.console = console;
        this.collectionManager = collectionManager;
    }


    @Override
    public void execute(String[] args) {
        try {
            City city = new CityBuilder(console, InputFormat.CONSOLE).build();
            City existed_city = collectionManager.findByNameAndCords(city.getName(), city.getCoordinates());
            if (existed_city != null) {
                console.println("Элемент с таким именем и координатами уже существует");
                collectionManager.update(existed_city, city);
                console.println(String.format("Элемент %d обновлён", existed_city.getId()));
            } else {
                collectionManager.add(city);
                console.println("Элемент добавлен");
            }
        } catch (BuildObjectException e) {
            console.printError("Ошибка при создании объекта: " + e.getMessage());
        }
    }

}