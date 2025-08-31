package ru.itmo.socket.client.exception;

public class ClientConnectionException extends RuntimeException {
    public ClientConnectionException(Throwable cause) {
        super(cause);
    }
}
