package ru.itmo.socket.client.udp;

import lombok.extern.log4j.Log4j2;
import ru.itmo.socket.client.exception.ClientConnectionException;
import ru.itmo.socket.common.command.AppCommand;
import ru.itmo.socket.common.dto.CommandDto;
import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.common.util.SocketContext;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

@Log4j2
public class UdpClientConnector implements AutoCloseable {

    private final DatagramSocket socket;

    public UdpClientConnector() {
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(SocketContext.getTimeoutMs());
            pingServer();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private void pingServer() {
        log.info("Checking server liveness...");
        sendCommand(new CommandDto(AppCommand.PING.getValue(), null));
        try {
            receiveAnswer();
            log.info("✅ Server is online");
        } catch (ClientConnectionException e) {
            log.error("❌ Server unreachable");
            System.exit(1);
        }
    }

    public void sendCommand(CommandDto request) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            oos.flush();

            byte[] sendBuffer = baos.toByteArray();
            InetAddress serverAddress = InetAddress.getByName(SocketContext.getHost());

            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, SocketContext.getPort());
            socket.send(sendPacket);
        } catch (IOException e) {
            throw new ClientConnectionException(e);
        }

    }

    public ResponseDto receiveAnswer() {
        // ждем ответ
        byte[] receiveBuffer = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        try {
            socket.receive(receivePacket);

            // десериализуем ответ
            ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData(), 0, receivePacket.getLength());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (ResponseDto) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ClientConnectionException(e);
        }
    }

    public void finishSocketConnection() {
        socket.close();
    }

    @Override
    public void close() throws Exception {
        finishSocketConnection();
    }
}
