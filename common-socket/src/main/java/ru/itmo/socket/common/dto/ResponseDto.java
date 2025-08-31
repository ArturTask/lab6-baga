package ru.itmo.socket.common.dto;

import lombok.*;
import ru.itmo.socket.common.util.SocketContext;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class ResponseDto implements Serializable {
    private String message;
    private int code;

    public static ResponseDto ok(String message) {
        return new ResponseDto(message, SocketContext.ResponseCode.SUCCESS.getCode());
    }

    public static ResponseDto error(String message) {
        return new ResponseDto(message, SocketContext.ResponseCode.INTERNAL_SERVER_ERROR.getCode());
    }

    public static ResponseDto closeConnection(String message) {
        return new ResponseDto(message, SocketContext.ResponseCode.CONNECTION_CLOSED.getCode());
    }
}
