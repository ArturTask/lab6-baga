package ru.itmo.lena.ex.builder;

import ru.itmo.lena.ex.entity.Coordinates;
import ru.itmo.lena.ex.util.Console;
import ru.itmo.lena.ex.enumeration.InputFormat;

public class CoordinatesBuilder extends Builder {
    public CoordinatesBuilder(Console console, InputFormat inputFormat) {
        super(console, inputFormat);
    }

    @Override
    public Coordinates build() {
        return new Coordinates(
                readX(),
                readY()
        );
    }

    private Integer readX() {
        while (true) {
            try {
                Integer x = Integer.parseInt(readLine("Введите координату X (≤850): "));
                if (x <= 850) return x;
                console.printError("X должен быть ≤850");
            } catch (NumberFormatException e) {
                console.printError("Некорректное число");
            }
//            if (inputFormat == InputFormat.FILE) System.exit(0);
        }
    }

    private int readY() {
        while (true) {
            try {
                return Integer.parseInt(readLine("Введите координату Y: "));
            } catch (NumberFormatException e) {
                console.printError("Некорректное число");
//                if (inputFormat == InputFormat.FILE) System.exit(0);
            }
        }
    }
}