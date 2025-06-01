package ru.itmo.socket.server.context.exception;

public class ContextLoadException extends RuntimeException{

    public ContextLoadException(String message) {
        super(message);
    }

    public ContextLoadException(Throwable cause) {
        super(cause);
    }
}
