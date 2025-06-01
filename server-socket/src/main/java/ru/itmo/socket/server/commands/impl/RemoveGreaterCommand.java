package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.common.entity.Product;
import ru.itmo.socket.server.commands.CommandHelper;
import ru.itmo.socket.server.commands.ServerCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RemoveGreaterCommand implements ServerCommand {

    @Override
    public void execute(ObjectOutputStream oos, Object... args) throws IOException {
        Product product = (Product) args[0];
        CommandHelper.removeGreater(oos, product);
    }

    @Override
    public Class<?> getArgType() {
        return Product.class;
    }
}
