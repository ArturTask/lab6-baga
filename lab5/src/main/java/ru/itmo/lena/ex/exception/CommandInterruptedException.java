package ru.itmo.lena.ex.exception;

/**
 * Исключение при прерывании выполнения команды
 */
public class CommandInterruptedException extends RuntimeException {
    // Конструктор с сообщением
    public CommandInterruptedException(String message) {
        super(message);
    }

    // Конструктор с сообщением и причиной
    public CommandInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }

    // Конструктор только с причиной
    public CommandInterruptedException(Throwable cause) {
        super(cause);
    }

    // Конструктор с форматированием сообщения
    public CommandInterruptedException(String format, Object... args) {
        super(String.format(format, args));
    }
}
