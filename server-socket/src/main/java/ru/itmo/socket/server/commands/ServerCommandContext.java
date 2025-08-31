package ru.itmo.socket.server.commands;


import ru.itmo.socket.common.command.AppCommand;
import ru.itmo.socket.common.exception.AppCommandNotFoundException;
import ru.itmo.socket.server.commands.impl.*;

import java.util.HashMap;
import java.util.Map;

import static ru.itmo.socket.common.command.AppCommand.*;


/**
 * Context of commands, logic of handling different commands
 */
public class ServerCommandContext {

    private static final Map<AppCommand, ServerCommand> commandMap = initializeMap();

    private static Map<AppCommand, ServerCommand> initializeMap() {
        HashMap<AppCommand, ServerCommand> result = new HashMap<>();

        result.put(HELP, new HelpCommand());
        result.put(INFO, new InfoCommand());
        result.put(SHOW, new ShowCommand());
        result.put(ADD, new AddCommand());
        result.put(UPDATE, new UpdateCommand());
        result.put(REMOVE_BY_ID, new RemoveCommand());
        result.put(CLEAR, new ClearCommand());
        result.put(EXECUTE_SCRIPT, new ExecuteScriptCommand());
        result.put(EXIT, new ExitCommand());
        result.put(HISTORY, new HistoryCommand());
        result.put(ADD_IF_MIN, new AddIfMinCommand());
        result.put(REMOVE_LOWER, new RemoveLowerCommand());
        result.put(SUM_OF_METERS_ABOVE_SEA_LEVEL, new SumOfMetersAboveSeaLevelCommand());
        result.put(PING, new PingCommand());
        return result;
    }

    public static ServerCommand getCommand(String commandName) throws AppCommandNotFoundException {
        return commandMap.get(AppCommand.getByStringValue(commandName));
    }
}
