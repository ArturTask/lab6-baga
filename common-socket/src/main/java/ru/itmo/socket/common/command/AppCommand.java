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
    HELP("help"), // : вывести справку по доступным командам
    INFO("info"), // : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
    SHOW("show"), // : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
    ADD("add"), // (element) : добавить новый элемент в коллекцию
    UPDATE("update"), // {element_id) : обновить значение элемента коллекции, id которого равен заданному
    REMOVE("remove"), // id: удалить элемент из коллекции по его id
    CLEAR("clear"), // : очистить коллекцию
    SAVE("save"), // : сохранить коллекцию в файл
    EXECUTE_SCRIPT("execute_script"), // file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
    EXIT("exit"), // : завершить программу (без сохранения в файл)
    HEAD("head"), // : вывести первый элемент коллекции
    ADD_IF_MAX("add_if_max"), // (element) : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
    REMOVE_GREATER("remove_greater"), // (element) : удалить из коллекции все элементы, превышающие заданный
    REMOVE_ANY_BY_PRICE("remove_any_by_price"), // price: удалить из коллекции один элемент, значение поля ргісе которого эквивалентно заданному
    PRINT_FIELD_DESCENDING_UNIT_OF_MEASURE("print_field_descending_unit_of_measure"), //: вывести значения поля unitOfMeasure всех элементов в порядке убывания
    HISTORY("history") // вывести историю запросов
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

