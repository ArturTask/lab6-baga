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
        result.put(REMOVE, new RemoveCommand());
        result.put(CLEAR, new ClearCommand());
        result.put(SAVE, new SaveCommand());
        result.put(EXECUTE_SCRIPT, new ExecuteScriptCommand());
        result.put(EXIT, new ExitCommand());
        result.put(HEAD, new HeadCommand());
        result.put(ADD_IF_MAX, new AddIfMaxCommand());
        result.put(REMOVE_GREATER, new RemoveGreaterCommand());
        result.put(REMOVE_ANY_BY_PRICE, new RemoveAnyByPriceCommand());
        result.put(PRINT_FIELD_DESCENDING_UNIT_OF_MEASURE, new PrintFieldDescendingUnitOfMeasureCommand());
        result.put(HISTORY, new HistoryCommand());

        return result;
    }

    public static ServerCommand getCommand(String commandName) throws AppCommandNotFoundException {
        return commandMap.get(AppCommand.getByStringValue(commandName));
    }
}
