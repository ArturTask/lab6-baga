package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.server.commands.ServerCommand;
import ru.itmo.socket.server.commands.CommandHelper;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ClearCommand implements ServerCommand {

    @Override
    public void execute(ObjectOutputStream oos, Object... args) throws IOException {
        CommandHelper.clear(oos);
    }
}
