package ru.itmo.socket.client.command.impl;

import ru.itmo.socket.client.command.ClientCommand;
import ru.itmo.socket.client.command.InputHelper;
import ru.itmo.socket.common.entity.City;

import java.util.Optional;
import java.util.Scanner;

public class AddCommand implements ClientCommand {
    @Override
    public Optional<Object> preProcess(Scanner scanner) {
        System.out.println("Введите данные нового элемента:");
        City obj = InputHelper.read(scanner);
        return Optional.of(obj);
    }
}
