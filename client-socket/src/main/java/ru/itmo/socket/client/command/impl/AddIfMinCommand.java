package ru.itmo.socket.client.command.impl;

import ru.itmo.socket.client.command.ClientCommand;
import ru.itmo.socket.client.command.InputHelper;

import java.util.Optional;
import java.util.Scanner;

public class AddIfMinCommand implements ClientCommand {
    @Override
    public Optional<Object> preProcess(Scanner scanner) {
        System.out.println("Введите данные нового элемента для проверки на min:");
        // Используем вспомогательный класс для ввода всех данных нового объекта
        Object object = InputHelper.read(scanner);
        return Optional.of(object);
    }
}

