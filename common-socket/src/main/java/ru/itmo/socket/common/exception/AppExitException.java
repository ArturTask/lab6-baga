package ru.itmo.socket.common.exception;

public class AppExitException extends RuntimeException {
    public AppExitException(String message) {
        super(message);
    }
}
