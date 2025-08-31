package ru.itmo.socket.server.commands.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itmo.socket.common.dto.ResponseDto;
import ru.itmo.socket.common.util.SocketContext;
import ru.itmo.socket.server.commands.ServerCommand;
import ru.itmo.socket.server.commands.ServerCommandContext;
import ru.itmo.socket.server.context.ContextLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Class responsible for executing script from file (multiple commands).
 */
public class ScriptExecutor {
    private static final Set<String> runningScripts = new HashSet<>();

    /**
     * Execute file-script line by line.
     * Prevents recursive execution of the same script.
     */
    public static ResponseDto execute(String fileName) {
        if (runningScripts.contains(fileName)) {
            return ResponseDto.error("Ошибка: рекурсивный вызов скрипта '" + fileName + "' обнаружен. Выполнение прекращено.");
        }

        URL resource = ScriptExecutor.class.getClassLoader().getResource(fileName);
        try {
            if (resource == null || !Files.exists(Paths.get(resource.toURI()))) {
                return ResponseDto.error("Файл скрипта не найден: " + fileName);
            }
        } catch (URISyntaxException e) {
            return ResponseDto.error("Ошибка доступа к файлу: " + e.getMessage());
        }

        File file = new File(resource.getFile());
        StringBuilder resultBuilder = new StringBuilder();

        runningScripts.add(fileName);
        try (Scanner fileScanner = new Scanner(file)) {
            int step = 1;
            while (fileScanner.hasNextLine()) {
                String lineFromFile = fileScanner.nextLine().trim();
                if (lineFromFile.isEmpty()) continue;

                String[] parts = lineFromFile.split("\\s+", 2);
                String cmdName = parts[0];
                String rawArg = parts.length > 1 ? parts[1] : null;

                ServerCommand cmd = ServerCommandContext.getCommand(cmdName);
                Object objArg = rawArg;

                try {
                    // map JSON -> POJO if needed
                    if (rawArg != null && cmd != null) {
                        ObjectMapper mapper = ContextLoader.getMapper();
                        Class<?> argType = cmd.getArgType();
                        if (argType != String.class) {
                            objArg = mapper.readValue(rawArg, argType);
                        }
                    }

                    ResponseDto response;
                    if ("execute_script".equals(cmdName)) {
                        if (rawArg == null) {
                            response = ResponseDto.error("Ошибка: имя файла не указано в команде execute_script.");
                        } else {
                            response = ScriptExecutor.execute(rawArg); // recursion
                        }
                    } else {
                        if (cmd == null) {
                            response = ResponseDto.error("Команда не найдена в скрипте: " + cmdName);
                        } else {
                            response = cmd.execute(objArg); // теперь команды тоже возвращают ResponseDto
                        }
                    }
                    // ERROR case
                    if (response.getCode() == SocketContext.ResponseCode.INTERNAL_SERVER_ERROR.getCode()) {
                        // при ошибке возвращаем то, что успели собрать + саму ошибку
                        if (!resultBuilder.isEmpty()) {
                            resultBuilder.append("\n");
                        }
                        resultBuilder.append(step).append(") ").append(response.getMessage());
                        return ResponseDto.error(resultBuilder.toString());
                    }
                    // CLOSE CONNECTION case
                    if(response.getCode() == SocketContext.ResponseCode.CONNECTION_CLOSED.getCode()) {
                        // при ошибке возвращаем то, что успели собрать + саму ошибку
                        if (!resultBuilder.isEmpty()) {
                            resultBuilder.append("\n");
                        }
                        resultBuilder.append(step).append(") ").append(response.getMessage());
                        return ResponseDto.closeConnection(resultBuilder.toString());
                    }
                    // SUCCESS case
                    else {
                        if (!resultBuilder.isEmpty()) {
                            resultBuilder.append("\n");
                        }
                        resultBuilder.append(step).append(") ").append(response.getMessage());
                    }
                } catch (Exception e) {
                    return ResponseDto.error("Ошибка при выполнении команды '" + cmdName + "': " + e.getMessage());
                }

                step++;
            }
        } catch (FileNotFoundException e) {
            return ResponseDto.error("Не удалось читать файл скрипта: " + e.getMessage());
        } finally {
            runningScripts.remove(fileName);
        }

        return ResponseDto.ok(resultBuilder.toString());
    }

}
