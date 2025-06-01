package ru.itmo.socket.client.command.impl;

import ru.itmo.socket.client.command.ClientCommand;
import ru.itmo.socket.client.command.InputHelper;
import ru.itmo.socket.common.entity.Product;

import java.util.Optional;
import java.util.Scanner;

public class AddIfMaxCommand implements ClientCommand {
    @Override
    public Optional<Object> preProcess(Scanner scanner) {
        System.out.println("Введите данные нового элемента для проверки на максимальность:");
        // Используем вспомогательный класс для ввода всех данных нового объекта
        Product newProduct = InputHelper.read(scanner);
        return Optional.of(newProduct);
    }
}

