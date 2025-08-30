package ru.itmo.lena.ex.builder;

import ru.itmo.lena.ex.entity.*;
import ru.itmo.lena.ex.exception.BuildObjectException;
import ru.itmo.lena.ex.util.Console;
import ru.itmo.lena.ex.enumeration.InputFormat;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Строитель объектов City с валидацией
 */
public class CityBuilder {
    private final Console console;
    private final InputFormat inputFormat;
    private LocalDateTime creationDate;
    private Long id;


    public CityBuilder(Console console, InputFormat inputFormat) {
        this.console = console;
        this.inputFormat = inputFormat;
    }

    /**
     * Основной метод построения города
     */
    public City build() throws BuildObjectException {
        return new City(
                City.getNextId(), // Автогенерация ID
                readName(),
                readCoordinates(),
                LocalDateTime.now(), // Автогенерация даты
                readArea(),
                readPopulation(),
                readMetersAboveSeaLevel(),
                readCapital(),
                readGovernment(),
                readStandardOfLiving(),
                readGovernor()
        );
    }

    private String readName() throws BuildObjectException {
        while (true) {
            console.print("Введите название города: ");
            String name = console.readln().trim();
            if (name.isEmpty()) {
                throw new BuildObjectException("Название города не может быть пустым");

            }
        return name;
    }}

    private Coordinates readCoordinates() throws BuildObjectException {
        console.println("Введите координаты:");
        return new Coordinates(
                readInteger("Координата X (целое число <= 850): ", false, 1, 850),
                readInteger("Координата Y (целое число): ", false, Integer.MIN_VALUE, Integer.MAX_VALUE)
        );
    }

    private float readArea() throws BuildObjectException {
        return readFloat("Площадь города (>0): ", false, 0.1f, Float.MAX_VALUE);
    }

    private Long readPopulation() throws BuildObjectException {
        return readLong("Население (>0): ", false, 1L, Long.MAX_VALUE);
    }

    private Integer readMetersAboveSeaLevel() throws BuildObjectException {
        return readInteger("Высота над уровнем моря (опционально): ", true, null, null);
    }


    private Boolean readCapital() throws BuildObjectException {
        while (true) {
            try {
                console.print("Является столицей (true/false): ");
                String input = console.readln().trim();

                if (input.equalsIgnoreCase("true")) return true;
                if (input.equalsIgnoreCase("false")) return false;

                console.println("Ошибка: Введите 'true' или 'false'.");
            } catch (Exception e) {
                console.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private Government readGovernment() throws BuildObjectException {
        while (true) {
            try {
                console.println("Тип правительства " + Arrays.toString(Government.values()) + ":");
                String input = console.readln().trim().toUpperCase();

                if (input.isEmpty()) {
                    console.println("Введите снова.");
                    continue; // Пропускаем пустые вводы
                }

                return Government.valueOf(input);
            } catch (IllegalArgumentException e) {
                console.println("Некорректный тип правительства. Попробуйте еще раз.");
            }
        }
    }


    private StandardOfLiving readStandardOfLiving() throws BuildObjectException {
        while (true) {
            try {
                console.print("Уровень жизни " + Arrays.toString(StandardOfLiving.values()) + " (опционально): ");
                String input = console.readln().trim();
                if (input.isEmpty()) return null;
                try {
                    return StandardOfLiving.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    console.println("Ошибка: Некорректный уровень жизни. Попробуйте еще раз.");
                }
            } catch (Exception e) {
                console.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private Human readGovernor() throws BuildObjectException {
        while (true) {
            try {
                console.print("Введите рост губернатора (>0) или Enter чтобы пропустить: ");
                String input = console.readln().trim();

                if (input.isEmpty()) return null;

                try {
                    int height = Integer.parseInt(input);
                    if (height <= 0) {
                        console.println("Ошибка: Рост должен быть >0. Попробуйте еще раз.");
                        continue;
                    }
                    return new Human(height, null);
                } catch (NumberFormatException e) {
                    console.println("Ошибка: Некорректный формат числа. Введите целое число >0.");
                }
            } catch (Exception e) {
                console.println("Ошибка: " + e.getMessage());
            }
        }
    }
//здесь while true
    // методы для чтения чисел
    private int readInteger(String prompt, boolean nullable, Integer min, Integer max) throws BuildObjectException {
        while (true) {
            console.print(prompt);
            String input = console.readln().trim();

            if (nullable && input.isEmpty()) {
                return 0;
            }

            try {
                int value = Integer.parseInt(input);
                if (min != null && value < min) {
                    console.printError("Значение должно быть >= " + min);
                    continue;
                }
                if (max != null && value > max) {
                    console.printError("Значение должно быть <= " + max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                console.printError("Некорректное целое число");
            }
        }
    }

    private long readLong(String prompt, boolean nullable, Long min, Long max) throws BuildObjectException {
        return 0;
    }

    private float readFloat(String prompt, boolean nullable, Float min, Float max) throws BuildObjectException {
        return 0;
    }


    public CityBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CityBuilder setCreationDate(LocalDateTime date) {
        this.creationDate = date;
        return this;
    }
}