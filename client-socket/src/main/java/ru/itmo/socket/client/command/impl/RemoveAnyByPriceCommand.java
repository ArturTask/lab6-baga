package ru.itmo.socket.client.command.impl;

import ru.itmo.socket.client.command.ClientCommand;

import java.util.Optional;
import java.util.Scanner;

public class RemoveAnyByPriceCommand implements ClientCommand {
    @Override
    public Optional<Object> preProcess(Scanner scanner) {
        System.out.print("Введите price элемента для удаления: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        return Optional.of(price);
    }
}
