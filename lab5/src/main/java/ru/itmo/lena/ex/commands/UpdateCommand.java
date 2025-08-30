package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.builder.CityBuilder;
import ru.itmo.lena.ex.entity.City;
import ru.itmo.lena.ex.exception.BuildObjectException;
import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;
import ru.itmo.lena.ex.enumeration.InputFormat;

/**
 * Команда для обновления элемента коллекции по ID
 */
public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public UpdateCommand(CollectionManager collectionManager, Console console) {
        super("update", "обновить элемент коллекции по ID");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            console.printError("Требуется аргумент - ID города");
            return;
        }

        try {
            long id = Long.parseLong(args[0]);
            City existingCity = collectionManager.findById(id);

            if (existingCity == null) {
                console.printError("Города с ID=" + id + " не существует");
                return;
            }

            // Сохраняем автоматические поля
            CityBuilder builder = new CityBuilder(console, InputFormat.CONSOLE)
                    .setId(existingCity.getId())
                    .setCreationDate(existingCity.getCreationDate());

            // Создаем обновленный объект
            City updatedCity = builder.build();

            // Заменяем элемент в коллекции
            collectionManager.update(existingCity, updatedCity);
            console.println("Город с ID=" + id + " успешно обновлен");

        } catch (NumberFormatException e) {
            console.printError("ID должен быть целым числом");
        } catch (BuildObjectException e) {
            console.printError("Ошибка в данных: " + e.getMessage());
        }
    }
}