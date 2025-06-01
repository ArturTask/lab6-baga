package ru.itmo.socket.client.command.impl.base;

import ru.itmo.socket.client.command.ClientCommand;

import java.util.Optional;
import java.util.Scanner;

/**
 * Command WITHOUT input (returns Optional.empty())
 */
public class DefaultCommand implements ClientCommand {

    @Override
    public Optional<Object> preProcess(Scanner scanner) {
        return ClientCommand.super.preProcess(scanner);
    }
}
