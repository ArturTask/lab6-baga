package ru.itmo.lena.ex.manager;

import ru.itmo.lena.ex.MainLab5;
import ru.itmo.lena.ex.entity.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер для работы с CSV файлами
 */
public class FileManager {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Чтение городов из CSV файла
     */
    public static ArrayList<City> readCitiesFromCSV(String filePath) throws IOException {
        ArrayList<City> cities = new ArrayList<>();


        CSVFormat format = CSVFormat.RFC4180
                .builder()
                .setHeader("id", "name", "x", "y", "creationDate", "area",
                        "population", "metersAboveSeaLevel", "capital",
                        "government", "standardOfLiving", "governor")
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .setIgnoreEmptyLines(true)
                .build();

        URL resource = FileManager.class.getClassLoader().getResource(filePath);
        Path path;
        try {
            path = Paths.get(resource.toURI());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (Reader reader = new FileReader(path.toFile());
             CSVParser parser = new CSVParser(reader, format)) {

            for (CSVRecord record : parser) {
                try {
                    City city = parseCityFromRecord(record);
                    if (city.isValid()) {
                        cities.add(city);
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка парсинга строки " + record.getRecordNumber() +
                            ": " + e.getMessage());
                }
            }
        }
        return cities;
    }


    private static City parseCityFromRecord(CSVRecord record) {
        // Ручная обработка значений с проверкой на пустые строки
        String metersAboveSeaLevel = record.get("metersAboveSeaLevel").trim();
        String standardOfLiving = record.get("standardOfLiving").trim();
        String governor = record.get("governor").trim();

        return new City(
                Long.parseLong(record.get("id").trim()),
                record.get("name").trim(),
                new Coordinates(
                        Integer.parseInt(record.get("x").trim()),
                        Integer.parseInt(record.get("y").trim())
                ),
                LocalDateTime.parse(record.get("creationDate").trim(), DATE_FORMATTER),
                Float.parseFloat(record.get("area").trim()),
                Long.parseLong(record.get("population").trim()),
                metersAboveSeaLevel.isEmpty() ? null : Integer.parseInt(metersAboveSeaLevel),
                Boolean.parseBoolean(record.get("capital").trim()),
                Government.valueOf(record.get("government").trim().toUpperCase()),
                standardOfLiving.isEmpty() ? null : StandardOfLiving.valueOf(standardOfLiving.toUpperCase()),
                governor.isEmpty() ? null : new Human(governor)
        );
    }

    /**
     * Запись городов в CSV файл
     */
    public static void writeCitiesToCSV(String filePath, List<City> cities) throws IOException {
        CSVFormat format = CSVFormat.RFC4180.builder()
                .setHeader("id", "name", "x", "y", "creationDate", "area",
                        "population", "metersAboveSeaLevel", "capital",
                        "government", "standardOfLiving", "governor")
                .build();

        try (Writer writer = new FileWriter(filePath);
             CSVPrinter printer = new CSVPrinter(writer, format)) {

            for (City city : cities) {
                printer.printRecord(
                        city.getId(),
                        city.getName(),
                        city.getCoordinates().getX(),
                        city.getCoordinates().getY(),
                        city.getCreationDate().format(DATE_FORMATTER),
                        city.getArea(),
                        city.getPopulation(),
                        city.getMetersAboveSeaLevel() != null ? city.getMetersAboveSeaLevel() : "",
                        city.getCapital(),
                        city.getGovernment().name(),
                        city.getStandardOfLiving() != null ? city.getStandardOfLiving().name() : "",
                        city.getGovernor() != null ? city.getGovernor().toString() : ""
                );
            }
        }
    }
}
