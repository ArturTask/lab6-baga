package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.server.commands.ServerCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class PingCommand implements ServerCommand {

    @Override
    public ResponseDto execute(Object... args) throws IOException {
        return ResponseDto.ok("ping ok");
    }
}
