package ru.itmo.socket.client.command;


import ru.itmo.socket.client.command.impl.*;
import ru.itmo.socket.client.command.impl.base.DefaultCommand;
import ru.itmo.socket.common.command.AppCommand;
import ru.itmo.socket.common.exception.AppCommandNotFoundException;

import java.util.HashMap;
import java.util.Map;

import static ru.itmo.socket.common.command.AppCommand.*;

/**
 * Some commands require input values,
 * others - not (they  have DefaultCommand without input values)
 */
public class ClientCommandContext {


    private static final Map<AppCommand, ClientCommand> commandMap = initializeMap();

    private static Map<AppCommand, ClientCommand> initializeMap() {
        HashMap<AppCommand, ClientCommand> result = new HashMap<>();

        result.put(HELP, new DefaultCommand());
        result.put(INFO, new DefaultCommand());
        result.put(SHOW, new DefaultCommand());
        result.put(ADD, new AddCommand());
        result.put(UPDATE, new UpdateCommand());
        result.put(REMOVE, new RemoveCommand());
        result.put(CLEAR, new DefaultCommand());
        result.put(SAVE, new SaveCommand());
        result.put(EXECUTE_SCRIPT, new ExecuteScriptCommand());
        result.put(EXIT, new DefaultCommand());
        result.put(HEAD, new DefaultCommand());
        result.put(ADD_IF_MAX, new AddIfMaxCommand());
        result.put(REMOVE_GREATER, new RemoveGreaterCommand());
        result.put(REMOVE_ANY_BY_PRICE, new RemoveAnyByPriceCommand());
        result.put(PRINT_FIELD_DESCENDING_UNIT_OF_MEASURE, new DefaultCommand());
        result.put(HISTORY, new DefaultCommand());

        return result;
    }

    public static ClientCommand getCommand(String commandName) throws AppCommandNotFoundException {
        return commandMap.get(AppCommand.getByStringValue(commandName));
    }
}
