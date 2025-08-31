package ru.itmo.socket.server.commands.util;

import ru.itmo.socket.common.command.AppCommand;
import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.common.entity.City;
import ru.itmo.socket.common.util.SocketContext;
import ru.itmo.socket.server.context.AppContext;
import ru.itmo.socket.server.context.CommandHistory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Class responsible for working with collection (AppContext)
 */
public class CommandHelper {

    public static ResponseDto info() throws IOException {
        return ResponseDto.ok(AppContext.info());
    }

    public static ResponseDto addCity(City city) {
        boolean added = AppContext.add(city);
        if (added) {
            return ResponseDto.ok("Элемент успешно добавлен.");
        } else {
            return ResponseDto.error("Элемент уже есть в коллекции.");
        }
    }

    public static ResponseDto updateCity(City updated) {
        long id = updated.getId();
        boolean updatedEntity = AppContext.updateEntity(updated);
        if (updatedEntity) {
            return ResponseDto.ok("Элемент с id " + id + " успешно обновлён.");
        } else {
            return ResponseDto.error("Элемент с id " + id + " не найден.");
        }
    }

    public static ResponseDto removeById(int id) {
        boolean removed = AppContext.removeById(id);
        if (removed) {
            return ResponseDto.ok("Элемент с id " + id + " удалён.");
        } else {
            return ResponseDto.error("Элемент с id " + id + " не найден.");
        }
    }

    public static ResponseDto clear() {
        AppContext.removeAll();
        return ResponseDto.ok("Коллекция очищена.");
    }

    public static ResponseDto show() {
        List<String> elements = AppContext.getAllElements();
        if (elements.isEmpty()) {
            return ResponseDto.ok("Коллекция пуста.");
        } else {
            StringBuilder sb = new StringBuilder();
            elements.forEach(cityStr -> sb.append(cityStr).append(System.lineSeparator()));
            return ResponseDto.ok(sb.toString());
        }
    }

    public static ResponseDto addIfMin(City city) {
        boolean added = AppContext.addIfMin(city);
        if (added) {
            return ResponseDto.ok("Элемент успешно добавлен.");
        } else {
            return ResponseDto.error("Элемент не добавлен, так как не < min.");
        }
    }

    public static ResponseDto removeLower(City city) {
        int removedQuantity = AppContext.removeAllLowerThan(city);
        return ResponseDto.ok("Удалено " + removedQuantity + " элемент(ов).");
    }

    public static ResponseDto exit() {
        AppContext.saveContext("new-save-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm")) + ".csv");
        return ResponseDto.closeConnection("Завершение работы. bye \uD83D\uDE18");
    }

    public static ResponseDto history() {
        String stringOfCommands = CommandHistory.getHistory()
                .stream()
                .map(AppCommand::getValue)
                .reduce((s, s2) -> s + ", " +s2)
                .orElse("История пуста");
        return ResponseDto.ok(stringOfCommands);
    }

    public static ResponseDto sumOfMetersAboveSeaLevel() {
        long sum = AppContext.sumOfMeters();
        return ResponseDto.ok("Сумма metersAboveSeaLevel: " + sum);
    }
}
