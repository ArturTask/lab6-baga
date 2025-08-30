package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;

/**
 * Удаляет все элементы, у которых metersAboveSeaLevel равен заданному
 */
public class RemoveAllByMetersCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public RemoveAllByMetersCommand(CollectionManager collectionManager, Console console) {
        super("remove_all_by_meters_above_sea_level", "удалить элементы с заданным metersAboveSeaLevel");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            console.printError("Требуется аргумент: metersAboveSeaLevel");
            return;
        }

        try {
            Integer meters = Integer.parseInt(args[0]);
            int removed = collectionManager.removeAllByMeters(meters);
            console.println("Удалено элементов: " + removed);
        } catch (NumberFormatException e) {
            console.printError("metersAboveSeaLevel должен быть целым числом");
        }
    }
}