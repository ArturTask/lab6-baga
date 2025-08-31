package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.common.entity.City;
import ru.itmo.socket.server.commands.util.CommandHelper;
import ru.itmo.socket.server.commands.ServerCommand;

import java.io.IOException;

public class AddIfMinCommand implements ServerCommand {

    @Override
    public ResponseDto execute(Object... args) throws IOException {
        City city = (City) args[0];
        // Если новый элемент больше максимального в коллекции, он будет добавлен.
        return CommandHelper.addIfMin(city);
    }

    @Override
    public Class<?> getArgType() {
        return City.class;
    }
}

