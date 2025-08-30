package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.entity.City;
import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;

/**
 * Команда для удаления элемента коллекции по ID
 */
public class RemoveByIdCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public RemoveByIdCommand(CollectionManager collectionManager, Console console) {
        super("remove_by_id", "удалить элемент по ID");
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
            City city = collectionManager.findById(id);

            if (city == null) {
                console.printError("Города с ID=" + id + " не существует");
                return;
            }

            collectionManager.remove(city);
            console.println("Город с ID=" + id + " успешно удален");

        } catch (NumberFormatException e) {
            console.printError("ID должен быть целым числом");
        }
    }
}