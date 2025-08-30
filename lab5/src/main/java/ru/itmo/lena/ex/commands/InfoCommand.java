package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.exception.CommandExecutionException;
import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;

import java.time.format.DateTimeFormatter;

/**
 * Команда для вывода информации о коллекции
 */
public class InfoCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public InfoCommand(CollectionManager collectionManager, Console console) {
        super("info", "вывести информацию о коллекции (тип, дата инициализации, размер)");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) throws CommandExecutionException {
        try {
            if (args.length > 0) {
                throw new CommandExecutionException("Команда не принимает аргументов");
            }

            console.println("Информация о коллекции");
            console.println(String.format("Тип: %s",
                    collectionManager.getCollection().getClass().getSimpleName()));
            console.println(String.format("Дата создания: %s",
                    collectionManager.getCreationTime().format(DateTimeFormatter.ISO_DATE_TIME)));
            console.println(String.format("Количество элементов: %d",
                    collectionManager.getCollectionSize()));

        } catch (Exception e) {
            throw new CommandExecutionException("Ошибка при получении информации: " + e.getMessage(), e);
        }
    }
}