package ru.itmo.socket.client.util;

import lombok.extern.log4j.Log4j2;
import ru.itmo.socket.client.command.ClientCommand;
import ru.itmo.socket.client.command.ClientCommandContext;
import ru.itmo.socket.common.dto.CommandDto;
import ru.itmo.socket.common.exception.AppCommandNotFoundException;

import java.util.Optional;
import java.util.Scanner;

@Log4j2
public class Console {
    private static final Scanner scanner = new Scanner(System.in);

    public static CommandDto readCommandFromConsole() {
        String stringCommand = scanner.nextLine().trim();

        // парсим команду
        ClientCommand clientCommand;
        try {
            clientCommand = ClientCommandContext.getCommand(stringCommand);
        } catch (AppCommandNotFoundException e) {
            log.error("Command " + stringCommand + " not found");
            throw e;
        }
        // это мы получаем доп параметры (если надо в конкретной команде)
        // например при добавлении пользователя
        Optional<Object> clientCommandParam = clientCommand.preProcess(scanner);

        // добавляем доп аргументы если нужно к команде и отправляем на сервер
        Object arg = clientCommandParam.orElse(null);
        return new CommandDto(stringCommand, arg);
    }
}
