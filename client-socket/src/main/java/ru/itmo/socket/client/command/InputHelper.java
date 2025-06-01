package ru.itmo.socket.client.command;


import ru.itmo.socket.common.entity.*;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Class responsible for inputting values through console
 */
public class InputHelper {

    // собирает валидный Product для добавления (автогенерирует id)
    public static Product read(Scanner scanner) {
        return readObject(scanner, false);
    }

    // собирает валидный Product для добавления (автогенерирует id)
    public static Product readWithId(Scanner scanner) {
        return readObject(scanner, true);
    }

    // собирает валидный Product
    private static Product readObject(Scanner scanner, boolean inputId) {
        // generate default product
        Product product = Product.generateDefaultWithoutId();


        // generate id if not passed
        if (inputId) {
            product.setId(inputId(scanner));
        } else {
            product.setId(Product.generateId());
        }

        // suggest using default product
        if (!isInputManually(scanner, product)) {
            return product;
        }

        product.setCreationDate(LocalDateTime.now());

        product.setName(inputName(scanner));
        product.setCoordinates(inputCoordinates(scanner));
        product.setPrice(inputPrice(scanner));
        product.setUnitOfMeasure(inputUnitOfMeasure(scanner));
        product.setManufacturer(inputOrganization(scanner));

        return product;
    }

    private static boolean isInputManually(Scanner scanner, Product product) {
        System.out.println("Желаете Создать объект с нуля? (иначе будет взят объект по умолчанию) (y/n)");
        System.out.println(product);
        String isManually = scanner.nextLine().trim();
        return "y".equalsIgnoreCase(isManually);
    }

    private static int inputId(Scanner scanner) {
        while (true) {
            System.out.print("Введите id продукта: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return Integer.parseInt(input);
            System.out.println("Ошибка: имя не может быть пустым");
        }
    }

    private static String inputName(Scanner scanner) {
        while (true) {
            System.out.print("Введите название продукта: ");
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
                double x = Double.parseDouble(scanner.nextLine());
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
                float y = Float.parseFloat(scanner.nextLine());
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

    private static UnitOfMeasure inputUnitOfMeasure(Scanner scanner) {
        System.out.println("Доступные единицы измерения:");
        for (UnitOfMeasure u : UnitOfMeasure.values()) {
            System.out.println("- " + u.name());
        }
        while (true) {
            System.out.print("Введите единицу измерения (или оставьте пустым): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;
            try {
                return UnitOfMeasure.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: неверное значение. Попробуйте снова.");
            }
        }
    }

    private static Organization inputOrganization(Scanner scanner) {
        System.out.print("Хотите ввести производителя? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (!choice.equals("y")) return null;

        Organization org = new Organization();
        org.setId(Product.generateId());

        // name
        while (true) {
            System.out.print("Введите имя организации: ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                org.setName(name);
                break;
            }
            System.out.println("Ошибка: имя не может быть пустым");
        }

        // full name
        while (true) {
            System.out.print("Введите полное имя (или оставьте пустым): ");
            String fullName = scanner.nextLine().trim();
            if (fullName.length() > 1610) {
                System.out.println("Ошибка: длина не должна превышать 1610 символов");
            } else {
                org.setFullName(fullName.isEmpty() ? null : fullName);
                break;
            }
        }

        // org type
        System.out.println("Доступные типы организаций:");
        for (OrganizationType type : OrganizationType.values()) {
            System.out.println("- " + type.name());
        }
        while (true) {
            System.out.print("Введите тип организации: ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                org.setType(OrganizationType.valueOf(input));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: неправильный тип организации");
            }
        }

        return org;
    }
}

