package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.server.commands.util.CommandHelper;
import ru.itmo.socket.server.commands.ServerCommand;

import java.io.IOException;

public class RemoveCommand implements ServerCommand {

    @Override
    public ResponseDto execute(Object... args) throws IOException {
        int id = Integer.parseInt(String.valueOf(args[0]));
        return CommandHelper.removeById(id);
    }
}
