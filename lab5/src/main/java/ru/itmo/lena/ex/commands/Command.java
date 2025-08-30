package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.exception.CommandExecutionException;

/**
 * Абстрактный класс для всех команд приложения
 */
public abstract class Command {
    private final String name;
    private final String description;

    /**
     * @param name Название команды
     * @param description Описание команды
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Выполняет логику команды
     * @param args Аргументы команды
     * @return true - если команда выполнена успешно, false - если произошла ошибка
     * @throws CommandExecutionException При критических ошибках выполнения
     */
    public abstract void execute(String[] args) throws CommandExecutionException;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }



    /**
     * Проверяет корректность аргументов
     */
    protected boolean validateArguments(String[] args, int expectedCount) {
        if (args.length != expectedCount) {
            System.err.printf("Неверное количество аргументов! Ожидалось: %d, получено: %d\n",
                    expectedCount, args.length);
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-20s %s",
                name,
                description);
    }
}