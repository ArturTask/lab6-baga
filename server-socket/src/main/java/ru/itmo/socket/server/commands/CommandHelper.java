package ru.itmo.socket.server.commands;

import ru.itmo.socket.common.command.AppCommand;
import ru.itmo.socket.common.entity.Product;
import ru.itmo.socket.common.exception.AppExitException;
import ru.itmo.socket.server.context.AppContext;
import ru.itmo.socket.server.context.CommandHistory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for working with collection (AppContext)
 */
public class CommandHelper {

    public static void info(ObjectOutputStream oos) throws IOException {
        AppContext.info(oos);
    }

    public static void addProduct(ObjectOutputStream oos, Product product) throws IOException {
        boolean added = AppContext.add(product);
        if (added) {
            oos.writeUTF("Элемент успешно добавлен.");
        } else {
            oos.writeUTF("Элемент уже есть в коллекции.");
        }
    }

    public static void updateProduct(ObjectOutputStream oos, Product updated) throws IOException {
        int id = updated.getId();
        boolean updatedEntity = AppContext.updateEntity(updated);
        if (updatedEntity) {
            oos.writeUTF("Элемент с id " + id + " успешно обновлён.");
        } else {
            oos.writeUTF("Элемент с id " + id + " не найден.");
        }
    }

    public static void removeById(ObjectOutputStream oos, int id) throws IOException {
        boolean removed = AppContext.removeById(id);
        if (removed) {
            oos.writeUTF("Элемент с id " + id + " удалён.");
        } else {
            oos.writeUTF("Элемент с id " + id + " не найден.");
        }
    }

    public static void clear(ObjectOutputStream oos) throws IOException {
        AppContext.removeAll();
        oos.writeUTF("Коллекция очищена.");
    }

    public static void show(ObjectOutputStream oos) throws IOException {
        List<String> elements = AppContext.getAllElements();
        if (elements.isEmpty()) {
            oos.writeUTF("Коллекция пуста.");
        } else {
            StringBuilder sb = new StringBuilder();
            elements.forEach(productStr -> sb.append(productStr).append(System.lineSeparator()));
            oos.writeUTF(sb.toString());
        }
    }


    public static void head(ObjectOutputStream oos) throws IOException {
        String first = AppContext.getFirst();
        if (first == null) {
            oos.writeUTF("Коллекция пуста.");
        } else {
            oos.writeUTF("Первый элемент: " + first);
        }
    }

    public static void addIfMax(ObjectOutputStream oos, Product product) throws IOException {
        boolean added = AppContext.addIfMax(product);
        if (added) {
            oos.writeUTF("Элемент успешно добавлен.");
        } else {
            oos.writeUTF("Элемент не добавлен, так как не превышает максимальный.");
        }
    }

    public static void removeGreater(ObjectOutputStream oos, Product product) throws IOException {
        int removedQuantity = AppContext.removeAllGreaterThan(product);
        oos.writeUTF("Удалено " + removedQuantity + " элемент(ов).");
    }

    public static void removeAnyByPrice(ObjectOutputStream oos, Double price) throws IOException {
        Integer removedElemId = AppContext.removeOneByPrice(price);
        if (removedElemId != null) {
            oos.writeUTF("Элемент с ценой " + price + " удалён. id = " + removedElemId);
        } else {
            oos.writeUTF("Элемент с ценой " + price + " не найден.");
        }
    }

    public static void printFieldDescendingUnitOfMeasure(ObjectOutputStream oos) throws IOException {
        List<String> units = AppContext.getAllUniqueDescendingUnitOfMeasure();
        if (units.isEmpty()) {
            oos.writeUTF("Нет значений UnitOfMeasure.");
        } else {
            StringBuilder sb = new StringBuilder();
            units.forEach(unitStr -> sb.append(unitStr).append(System.lineSeparator()));
            oos.writeUTF(sb.toString());
        }
    }

    public static void exit(ObjectOutputStream oos) throws IOException {
        oos.writeUTF("AppExit - Завершение работы. bye \uD83D\uDE18");
        throw new AppExitException("Завершение работы. bye \uD83D\uDE18");
    }

    public static void history(ObjectOutputStream oos) throws IOException {
        String stringOfCommands = CommandHistory.getHistory()
                .stream()
                .map(AppCommand::getValue)
                .reduce((s, s2) -> s + s2 + System.lineSeparator())
                .orElse("История пуста");
        oos.writeUTF(stringOfCommands);
    }
}
