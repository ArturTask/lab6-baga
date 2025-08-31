package ru.itmo.socket.client;

import lombok.extern.log4j.Log4j2;
import ru.itmo.socket.client.udp.UdpClientConnector;
import ru.itmo.socket.client.util.Console;
import ru.itmo.socket.common.dto.CommandDto;
import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.common.exception.AppCommandNotFoundException;
import ru.itmo.socket.common.util.SocketContext;

import java.text.MessageFormat;

@Log4j2
public class ClientUDP {

    public static void main(String[] args) throws Exception {
        try (UdpClientConnector udpClientConnector = new UdpClientConnector()) {

            while (true) {
                CommandDto commandDto;
                try {
                    commandDto = Console.readCommandFromConsole();
                } catch (AppCommandNotFoundException e) {
                    continue;
                }
                udpClientConnector.sendCommand(commandDto);
                ResponseDto responseDto = udpClientConnector.receiveAnswer();
                if (responseDto.getCode() == SocketContext.ResponseCode.INTERNAL_SERVER_ERROR.getCode()) {
                    log.error(MessageFormat.format("Error! Response code: {0} message: {1}", responseDto.getCode(), responseDto.getMessage()));
                    continue;
                }
                log.info("Received from server:\n" + responseDto.getMessage());

                // exit
                if (responseDto.getCode() == SocketContext.ResponseCode.CONNECTION_CLOSED.getCode()) {
                    break;
                }
            }
        }
    }

}
