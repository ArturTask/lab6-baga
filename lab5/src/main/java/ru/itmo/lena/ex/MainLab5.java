package ru.itmo.lena.ex;

import ru.itmo.lena.ex.manager.*;
import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;

import java.util.Scanner;

/**
 * Главный класс приложения для управления коллекцией городов
 */
public class MainLab5 {
    /**
     * Стандартное имя файла данных, если не указано в аргументах
     */
    private static final String DEFAULT_FILENAME = "cities.csv";

    public static void main(String[] args) {
        try {
            String filename = (args.length > 0) ? args[0] : DEFAULT_FILENAME;

            Console console = new Console(new Scanner(System.in));
            CollectionManager collectionManager = new CollectionManager(filename, console);
            CommandManager commandManager = new CommandManager(collectionManager, console);

//            collectionManager.loadCollection();

            commandManager.startInteractiveMode();

        } catch (Exception e) {
            System.err.println("Критическая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}