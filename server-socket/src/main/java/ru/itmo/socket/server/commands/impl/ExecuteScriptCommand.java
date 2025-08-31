package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.server.commands.util.ScriptExecutor;
import ru.itmo.socket.server.commands.ServerCommand;

import java.io.IOException;

/**
 * Команда execute_script: запрашивает файл и передаёт его исполнение ScriptExecutor.
 */
public class ExecuteScriptCommand implements ServerCommand {

    @Override
    public ResponseDto execute(Object... args) throws IOException {
        String filename = (String) args[0];
        return ScriptExecutor.execute(filename);
    }
}




