package ru.itmo.socket.server;

import lombok.extern.log4j.Log4j2;
import ru.itmo.socket.common.dto.CommandDto;
import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.server.commands.ServerCommand;
import ru.itmo.socket.server.commands.ServerCommandContext;
import ru.itmo.socket.server.context.AppContext;
import ru.itmo.socket.server.context.CommandHistory;
import ru.itmo.socket.server.udp.UdpServerConnector;

import java.io.IOException;

@Log4j2
public class ServerUDP {
    public static void main(String[] args) {
        AppContext.loadContext("cities.csv");

        try (UdpServerConnector serverConnector = new UdpServerConnector()) {
            while (true) {
                UdpServerConnector.ReceivedCommand received = serverConnector.receiveData();
                CommandDto command = received.command();
                log.info("Server received: " + command);

                ServerCommand serverCommand = ServerCommandContext.getCommand(command.getCommandName());
                // здесь логика обработки команды
                ResponseDto response = serverCommand.execute(command.getArg());

                // отправляем обратно клиенту
                serverConnector.sendData(response, received.clientAddress(), received.clientPort());
                CommandHistory.addCommand(command.getCommandName());

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

