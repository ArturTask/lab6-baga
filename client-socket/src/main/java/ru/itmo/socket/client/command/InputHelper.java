package ru.itmo.socket.client.command;


import ru.itmo.socket.common.entity.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class responsible for inputting values through console
 */
public class InputHelper {

    // собирает валидный obj для добавления (автогенерирует id)
    public static City read(Scanner scanner) {
        return readObject(scanner, false);
    }

    // собирает валидный City для добавления (автогенерирует id)
    public static City readWithId(Scanner scanner) {
        return readObject(scanner, true);
    }

    // собирает валидный City
    private static City readObject(Scanner scanner, boolean inputId) {
        // generate default city
        City city = City.generateDefaultWithoutId();


        // generate id if not passed
        if (inputId) {
            city.setId(inputId(scanner));
        } else {
            city.setId(City.getNextId());
        }

        // suggest using default city
        if (!isInputManually(scanner, city)) {
            return city;
        }

        city.setCreationDate(LocalDateTime.now());

        city.setName(inputName(scanner));
        city.setCoordinates(inputCoordinates(scanner));
        city.setCreationDate(LocalDateTime.now());
        city.setArea(inputArea(scanner));
        city.setPopulation(inputPopulation(scanner));
        city.setMetersAboveSeaLevel(inputMetersAboveSeaLevel(scanner));
        city.setCapital(inputCapital(scanner));
        city.setGovernment(inputGovernment(scanner));
        city.setStandardOfLiving(inputStandardOfLiving(scanner));
        city.setGovernor(inputGovernor(scanner));

        return city;
    }

    private static Human inputGovernor(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Введите рост губернатора (>0) или Enter чтобы пропустить: ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) return null;

                try {
                    int height = Integer.parseInt(input);
                    if (height <= 0) {
                        System.out.println("Ошибка: Рост должен быть >0. Попробуйте еще раз.");
                        continue;
                    }
                    return new Human(height, null);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: Некорректный формат числа. Введите целое число >0.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static StandardOfLiving inputStandardOfLiving(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Уровень жизни " + Arrays.toString(StandardOfLiving.values()) + " (опционально): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) return null;
                try {
                    return StandardOfLiving.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: Некорректный уровень жизни. Попробуйте еще раз.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static Government inputGovernment(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Тип правительства " + Arrays.toString(Government.values()) + ":");
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.isEmpty()) {
                    System.out.println("Введите снова.");
                    continue; // Пропускаем пустые вводы
                }

                return Government.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректный тип правительства. Попробуйте еще раз.");
            }
        }
    }

    private static Boolean inputCapital(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Является столицей (true/false): ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("true")) return true;
                if (input.equalsIgnoreCase("false")) return false;

                System.out.println("Ошибка: Введите 'true' или 'false'.");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static Integer inputMetersAboveSeaLevel(Scanner scanner) {
        System.out.println("Высота над уровнем моря (опционально): ");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return 1;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Некорректное целое число");
            }
        }
    }

    private static Long inputPopulation(Scanner scanner) {
        while (true) {
            System.out.print("Введите population объекта: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return Long.parseLong(input);
            System.out.println("Ошибка: population не может быть пустым");
        }
    }

    private static float inputArea(Scanner scanner) {
        while (true) {
            System.out.print("Введите area объекта: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return Float.parseFloat(input);
            System.out.println("Ошибка: area не может быть пустым");
        }
    }


    private static boolean isInputManually(Scanner scanner, City city) {
        System.out.println("Желаете Создать объект с нуля? (иначе будет взят объект по умолчанию) (y/n)");
        System.out.println(city);
        String isManually = scanner.nextLine().trim();
        return "y".equalsIgnoreCase(isManually);
    }

    private static long inputId(Scanner scanner) {
        while (true) {
            System.out.print("Введите id объекта: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return Long.parseLong(input);
            System.out.println("Ошибка: имя не может быть пустым");
        }
    }

    private static String inputName(Scanner scanner) {
        while (true) {
            System.out.print("Введите название объекта: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Ошибка: имя не может быть пустым");
        }
    }

    private static Coordinates inputCoordinates(Scanner scanner) {
        Coordinates coordinates = new Coordinates();
        while (true) {
            try {
                System.out.print("Введите координату X (x > -599): ");
                int x = Integer.parseInt(scanner.nextLine());
                if (x <= -599) throw new IllegalArgumentException();
                coordinates.setX(x);
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: X должен быть числом > -599");
            }
        }
        while (true) {
            try {
                System.out.print("Введите координату Y (y > -162): ");
                int y = Integer.parseInt(scanner.nextLine());
                if (y <= -162) throw new IllegalArgumentException();
                coordinates.setY(y);
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: Y должен быть числом > -162");
            }
        }
        return coordinates;
    }

    private static Double inputPrice(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите цену (или оставьте пустым): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) return null;
                double value = Double.parseDouble(input);
                if (value <= 0) throw new IllegalArgumentException();
                return value;
            } catch (Exception e) {
                System.out.println("Ошибка: цена должна быть числом > 0 или пустой");
            }
        }
    }

}

