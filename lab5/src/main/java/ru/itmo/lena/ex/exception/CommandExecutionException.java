package ru.itmo.lena.ex.exception;

/**
 * Базовое исключение для всех ошибок выполнения команд.
 * Наследуйте от этого класса специализированные исключения.
 */
public class CommandExecutionException extends Exception {

    /**
     * Создает исключение с сообщением об ошибке.
     *
     * @param message Описание ошибки
     */
    public CommandExecutionException(String message) {
        super(message);
    }

    /**
     * Создает исключение с сообщением и причиной.
     *
     * @param message Описание ошибки
     * @param cause   Исходное исключение
     */
    public CommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}