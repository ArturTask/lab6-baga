package ru.itmo.lena.ex.exception;

/**
 * Исключение, возникающее при ошибках создания объектов коллекции
 */
public class BuildObjectException extends CommandExecutionException {
    /**
     * Создает исключение с сообщением об ошибке
     * @param message описание ошибки
     */
    public BuildObjectException(String message) {
        super(message);
    }

    /**
     * Создает исключение с сообщением и причиной
     * @param message описание ошибки
     * @param cause исходное исключение
     */
    public BuildObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Создает исключение с форматированным сообщением
     * @param format формат строки сообщения
     * @param args аргументы для форматирования
     */
    public BuildObjectException(String format, Object... args) {
        super(String.format(format, args));
    }
}