package ru.itmo.socket.server.commands;

import ru.itmo.socket.common.dto.ResponseDto;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 */
public interface ServerCommand {

    /**
     * Executes server command, returns message if needed
     *
     * @param args arguments of this command
     * @return message for client
     */
    ResponseDto execute(Object... args) throws IOException;

    /**
     *
     * @return Тип аргумента (если аргумент используется в команде) это нужно для команды execute_script
     * чтобы команда прочитала из файла arg (аргумент) для команды В ФАЙЛЕ
     */
    default Class<?> getArgType() {
        return String.class; // по умолчанию String (вообще может и не быть параметров)
    }
}