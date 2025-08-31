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
        result.put(REMOVE_BY_ID, new RemoveCommand());
        result.put(CLEAR, new DefaultCommand());
        result.put(EXECUTE_SCRIPT, new ExecuteScriptCommand());
        result.put(EXIT, new DefaultCommand());
        result.put(HISTORY, new DefaultCommand());
        result.put(ADD_IF_MIN, new AddIfMinCommand());
        result.put(REMOVE_LOWER, new RemoveLowerCommand());
        result.put(SUM_OF_METERS_ABOVE_SEA_LEVEL, new DefaultCommand());
        result.put(PING, new DefaultCommand());
        return result;
    }

    public static ClientCommand getCommand(String commandName) throws AppCommandNotFoundException {
        return commandMap.get(AppCommand.getByStringValue(commandName));
    }
}
