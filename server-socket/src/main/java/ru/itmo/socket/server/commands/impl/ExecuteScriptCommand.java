package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.server.commands.ScriptExecutor;
import ru.itmo.socket.server.commands.ServerCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Команда execute_script: запрашивает файл и передаёт его исполнение ScriptExecutor.
 */
public class ExecuteScriptCommand implements ServerCommand {

    @Override
    public void execute(ObjectOutputStream oos, Object... args) throws IOException {
        String filename = (String) args[0];
        ScriptExecutor.execute(oos, filename);
    }

    @Override
    public int getNumberOfOutputLines(Object... args) {
        String fileName = String.valueOf(args[0]);
        return ScriptExecutor.countNumberOfCommands(fileName);
    }
}




