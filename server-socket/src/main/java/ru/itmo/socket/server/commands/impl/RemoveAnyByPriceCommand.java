package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.server.commands.CommandHelper;
import ru.itmo.socket.server.commands.ServerCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RemoveAnyByPriceCommand implements ServerCommand {

    @Override
    public void execute(ObjectOutputStream oos, Object... args) throws IOException {
        double price = Double.parseDouble(String.valueOf(args[0]));
        CommandHelper.removeAnyByPrice(oos, price);
    }
}
