package ru.itmo.lena.ex.builder;

import ru.itmo.lena.ex.entity.Government;
import ru.itmo.lena.ex.util.Console;
import ru.itmo.lena.ex.enumeration.InputFormat;
import java.util.Arrays;

public class GovernmentBuilder extends Builder {
    public GovernmentBuilder(Console console, InputFormat inputFormat) {
        super(console, inputFormat);
    }

    @Override
    public Government build() {
        while (true) {
            String input = readLine("Введите тип правительства " +
                    Arrays.toString(Government.values()) + ": ");
            try {
                return Government.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                console.printError("Некорректный тип правительства");
                if (inputFormat == InputFormat.FILE) System.exit(0);
            }
        }
    }
}