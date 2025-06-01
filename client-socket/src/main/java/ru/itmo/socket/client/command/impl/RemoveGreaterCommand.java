package ru.itmo.socket.client.command.impl;

import ru.itmo.socket.client.command.ClientCommand;
import ru.itmo.socket.client.command.InputHelper;
import ru.itmo.socket.common.entity.Product;

import java.util.Optional;
import java.util.Scanner;

public class RemoveGreaterCommand implements ClientCommand {
    @Override
    public Optional<Object> preProcess(Scanner scanner) {
        Product product = InputHelper.readWithId(scanner);
        return Optional.of(product);
    }
}
