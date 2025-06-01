package ru.itmo.socket.client.command.impl;

import ru.itmo.socket.client.command.ClientCommand;

import java.util.Optional;
import java.util.Scanner;

public class RemoveCommand implements ClientCommand {
    @Override
    public Optional<Object> preProcess(Scanner scanner) {
        System.out.print("Введите id элемента для удаления: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        return Optional.of(id);
    }
}
