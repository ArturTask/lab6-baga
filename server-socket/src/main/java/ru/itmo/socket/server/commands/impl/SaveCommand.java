package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.server.commands.ServerCommand;
import ru.itmo.socket.server.context.AppContext;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Команда для сохранения коллекции LabWork в XML-файл.
 */
public class SaveCommand implements ServerCommand {

    @Override
    public void execute(ObjectOutputStream oos, Object... args) throws IOException {
        String fileName = String.valueOf(args[0]);
        AppContext.saveContext(fileName);
    }
}


