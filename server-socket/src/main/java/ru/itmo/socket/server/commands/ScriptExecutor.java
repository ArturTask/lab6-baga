package ru.itmo.socket.server.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itmo.socket.server.context.ContextLoader;
import ru.itmo.socket.server.context.exception.ContextLoadException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Class responsible for executing script from file (multiple commands)
 */
public class ScriptExecutor {
    private static final Set<String> runningScripts = new HashSet<>();

    /**
     * Исполняет файл-скрипт построчно.
     * Запрещает повторный запуск уже запущенного файла (рекурсию).
     */
    public static void execute(ObjectOutputStream oos, String fileName) throws IOException {
        if (runningScripts.contains(fileName)) {
            oos.writeUTF("Ошибка: рекурсивный вызов скрипта '" + fileName + "' обнаружен. Выполнение прекращено.");
            return;
        }

        URL resource = ScriptExecutor.class.getClassLoader().getResource(fileName);
        try {
            if (resource == null || !Files.exists(Paths.get(resource.toURI()))) {
                System.out.println("Файл скрипта не найден: " + fileName);
                return;
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        File file = new File(resource.getFile());

        runningScripts.add(fileName);
        try (Scanner fileScanner = new Scanner(file)) {

            while (fileScanner.hasNextLine()) {
                String lineFromFile = fileScanner.nextLine().trim();
                if (lineFromFile.isEmpty()) continue;

                String[] parts = lineFromFile.split("\\s+", 2);
                String cmdName = parts[0];
                String rawArg = parts.length > 1 ? parts[1] : null;

                ServerCommand cmd = ServerCommandContext.getCommand(cmdName);
                Object objArg = rawArg;

                // try parse object if needed from json to POJO
                if (rawArg != null) {
                    ObjectMapper mapper = ContextLoader.getMapper();

                    // map from json if not string argument!
                    Class<?> argType = cmd.getArgType();
                    if (argType != String.class) {
                        objArg = mapper.readValue(rawArg, argType);
                    }
                }

                if ("execute_script".equals(cmdName)) {
                    if (rawArg == null) {
                        oos.writeUTF("Ошибка: имя файла не указано в команде execute_script.");
                    } else {
                        // recursion if we have execute_script inside execute_script
                        ScriptExecutor.execute(oos, rawArg);
                    }
                } else {
                    if (cmd == null) {
                        oos.writeUTF("Команда не найдена в скрипте: " + cmdName);
                    } else {
                        cmd.execute(oos, objArg);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            oos.writeUTF("Не удалось читать файл скрипта: " + e.getMessage());
        } finally {
            runningScripts.remove(fileName);
        }
    }

    /**
     * number of not empty commands in script
     */
    public static int countNumberOfCommands(String fileName) {

        try {
            URL resource = ScriptExecutor.class.getClassLoader().getResource(fileName);
            if (resource == null || !Files.exists(Paths.get(resource.toURI()))) {
                System.out.println("Файл скрипта не найден: " + fileName);
                return 0;
            }

            Scanner scanner = new Scanner(new File(resource.toURI()));

            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); // trim чтобы убрать пробелы и табы
                if (!line.isEmpty()) {
                    count++;
                }
            }

            scanner.close();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
}
