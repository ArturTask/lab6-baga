package ru.itmo.socket.server.udp;

import ru.itmo.socket.common.dto.CommandDto;
import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.common.util.SocketContext;

import java.io.*;
import java.net.*;

public class UdpServerConnector implements AutoCloseable {

    private final DatagramSocket socket;

    public UdpServerConnector() {
        try {
            socket = new DatagramSocket(SocketContext.getPort());
            System.out.println("✅ Сервер активен, слушает порт " + SocketContext.getPort());
        } catch (SocketException e) {
            throw new RuntimeException("Не удалось открыть UDP-сокет на порту " + SocketContext.getPort(), e);
        }
    }

    /**
     * Получает команду от клиента
     */
    public ReceivedCommand receiveData() {
        try {
            byte[] receiveBuffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket); // блокируется пока не придет пакет

            ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData(), 0, receivePacket.getLength());
            ObjectInputStream ois = new ObjectInputStream(bais);
            CommandDto command = (CommandDto) ois.readObject();

            return new ReceivedCommand(command, receivePacket.getAddress(), receivePacket.getPort());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Ошибка при получении данных", e);
        }
    }

    /**
     * Отправляет ответ клиенту
     */
    public void sendData(ResponseDto response, InetAddress clientAddress, int clientPort) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            oos.flush();

            byte[] sendBuffer = baos.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);

            socket.send(sendPacket);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при отправке данных", e);
        }
    }

    @Override
    public void close() {
        socket.close();
    }

    /**
     * Вспомогательная обертка: команда + адрес клиента
     */
    public record ReceivedCommand(CommandDto command, InetAddress clientAddress, int clientPort) {}
}
