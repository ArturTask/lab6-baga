package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.common.entity.City;
import ru.itmo.socket.server.commands.util.CommandHelper;
import ru.itmo.socket.server.commands.ServerCommand;

import java.io.IOException;

public class AddCommand implements ServerCommand {

    @Override
    public ResponseDto execute(Object... args) throws IOException {
        City city = (City) args[0];
        return CommandHelper.addCity(city);
    }

    @Override
    public Class<?> getArgType() {
        return City.class;
    }
}
