package ru.itmo.socket.server.commands.impl;

import ru.itmo.socket.server.commands.ServerCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class HelpCommand implements ServerCommand {

    @Override
    public void execute(ObjectOutputStream oos, Object... args) throws IOException {
        oos.writeUTF(
                "help : вывести справку по доступным командам\n" +
                        "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                        "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                        "add (element) : добавить новый элемент в коллекцию\n" +
                        "update {element_id) : обновить значение элемента коллекции, id которого равен заданному\n" +
                        "remove id: удалить элемент из коллекции по его id\n" +
                        "clear : очистить коллекцию\n" +
                        "save : сохранить коллекцию в файл\n" +
                        "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                        "exit : завершить программу (без сохранения в файл)\n" +
                        "head : вывести первый элемент коллекции\n" +
                        "add_if_max (element) : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                        "remove_greater (element) : удалить из коллекции все элементы, превышающие заданный\n" +
                        "remove_any_by_price price: удалить из коллекции один элемент, значение поля ргісе которого эквивалентно заданному\n" +
                        "print_field_descending_unit_of_measure вывести значения поля unitOfMeasure всех элементов в порядке убывания\n" +
                        "history : вывести последние 7 команд (без их аргументов)\n");
    }
}