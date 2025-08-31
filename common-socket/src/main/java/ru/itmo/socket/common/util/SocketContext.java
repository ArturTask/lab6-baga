package ru.itmo.socket.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class SocketContext {

    private static Map<SocketContextParam, Object> ALL_PARAMS = Map.of(
            SocketContextParam.SERVER_HOST, "localhost",
            SocketContextParam.SERVER_PORT, 12345,
            SocketContextParam.TIMEOUT_MS, 2000
    );

    public static int getPort() {
        return (Integer) ALL_PARAMS.get(SocketContextParam.SERVER_PORT);
    }

    public static String getHost() {
        return String.valueOf(ALL_PARAMS.get(SocketContextParam.SERVER_HOST));
    }

    public static int getTimeoutMs() {
        return (Integer) (ALL_PARAMS.get(SocketContextParam.TIMEOUT_MS));
    }


    @Getter
    @AllArgsConstructor
    public enum SocketContextParam {
        SERVER_PORT,
        SERVER_HOST,
        TIMEOUT_MS
    }

    @Getter
    @AllArgsConstructor
    public enum ResponseCode {
        SUCCESS(200),
        INTERNAL_SERVER_ERROR(500),
        CONNECTION_CLOSED(777),
        ;

        private final int code;

    }

}
