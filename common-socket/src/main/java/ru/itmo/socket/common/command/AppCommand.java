package ru.itmo.socket.common.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.itmo.socket.common.exception.AppCommandNotFoundException;

import java.util.Arrays;

/**
 * Все доступные команды для приложения
 */
@AllArgsConstructor
@Getter
public enum AppCommand {
    HELP("help"),
    INFO("info"),
    SHOW("show"),
    CLEAR("clear"),
    EXIT("exit"),
    ADD("add"),
    UPDATE("update"),
    REMOVE_BY_ID("remove_by_id"),
    EXECUTE_SCRIPT("execute_script"),
    HISTORY("history"),
    ADD_IF_MIN("add_if_min"),
    REMOVE_LOWER("remove_lower"),
    SUM_OF_METERS_ABOVE_SEA_LEVEL("sum_of_meters_above_sea_level"),
    PING("ping")
    ;

    private final String value;

    /**
     *
     * @param commandName string name of command from console
     * @return enum value of command
     */
    public static AppCommand getByStringValue(String commandName) throws AppCommandNotFoundException {
        return Arrays.stream(AppCommand.values())
                .filter(c -> c.getValue().equalsIgnoreCase(commandName))
                .findFirst()
                .orElseThrow(() -> new AppCommandNotFoundException("command " + commandName + " not found"))
                ;
    }

}

