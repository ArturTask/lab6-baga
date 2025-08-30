package ru.itmo.lena.ex.exception;

/**
 * Исключение при попытке выполнить неизвестную команду
 */
public class UnknownCommandException extends CommandExecutionException {
    public UnknownCommandException(String message) {
        super(message);
    }

    public UnknownCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCommandException(String format, Object... args) {
        super(String.format(format, args));
    }
}