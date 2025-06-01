package ru.itmo.socket.server.context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import ru.itmo.socket.common.entity.Product;
import ru.itmo.socket.server.commands.ScriptExecutor;
import ru.itmo.socket.server.context.exception.ContextLoadException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class ContextLoader {
    @Getter
    private static final ObjectMapper mapper = getObjectMapper();

    // create json object mapper + configure to read DateTime
    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }


    /**
     * Saves all elements to the given file in current directory
     *
     * @param filename name of new file to save info to
     * @param elements all elements that will be saved to file
     * @throws ContextLoadException when IOException is thrown
     */
    public static void saveToFile(String filename, List<Product> elements) {
        if (elements.isEmpty()) {
            System.out.println("⚠️ Коллекция пуста");
            return;
        }

        URL resource = ScriptExecutor.class.getClassLoader().getResource(filename);
        try {
            if (resource == null || !Files.exists(Paths.get(resource.toURI()))) {
                System.out.println("Файл скрипта не найден: " + filename);
                return;
            }
        } catch (URISyntaxException e) {
            throw new ContextLoadException(e);
        }

        File file = new File(resource.getFile());

        try {
            mapper.writeValue(file, elements);
            System.out.println("✅ Сохранено в файл: " + filename);
        } catch (IOException e) {
            System.err.println("❌ Ошибка при сохранении: " + e.getMessage());
            throw new ContextLoadException(e);
        }
    }

    /**
     * LOADS all elements FROM the given file in current directory
     *
     * @param filename filename to load from
     * @return collection of new elems
     * @throws ContextLoadException when IOException is thrown
     */
    public static List<Product> loadFromFile(String filename) throws ContextLoadException {

        URL resource = ScriptExecutor.class.getClassLoader().getResource(filename);
        try {
            if (resource == null || !Files.exists(Paths.get(resource.toURI()))) {
                System.out.println("Файл скрипта не найден: " + filename);
                throw new ContextLoadException("");
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        File file = new File(resource.getFile());

        if (!file.exists()) {
            System.out.println("⚠️ Файл не найден: " + filename);
        }

        try {
            LinkedList<Product> products = mapper.readValue(file, new TypeReference<>() {
            });

            System.out.println("✅ Загружены данные из файла: " + filename);
            return products;

        } catch (IOException e) {
            System.err.println("❌ Ошибка при сохранении: " + e.getMessage());
            throw new ContextLoadException(e);
        }

    }

}
