package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.server.commands.util.CommandHelper;
import ru.itmo.socket.server.commands.ServerCommand;

import java.io.IOException;

public class SumOfMetersAboveSeaLevelCommand implements ServerCommand {

    @Override
    public ResponseDto execute(Object... args) throws IOException {
        return CommandHelper.sumOfMetersAboveSeaLevel();
    }
}
