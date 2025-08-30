package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.manager.CollectionManager;
import ru.itmo.lena.ex.util.Console;

/**
 * Выводит сумму metersAboveSeaLevel всех элементов
 */
public class SumOfMetersCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public SumOfMetersCommand(CollectionManager collectionManager, Console console) {
        super("sum_of_meters_above_sea_level", "вывести сумму metersAboveSeaLevel");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        long sum = collectionManager.sumOfMeters();
        console.println("Сумма metersAboveSeaLevel: " + sum);
    }
}