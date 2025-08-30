package ru.itmo.lena.ex.builder;

import ru.itmo.lena.ex.util.Console;
import ru.itmo.lena.ex.enumeration.InputFormat;

/**
 * Абстрактный базовый класс для построения объектов
 */
public abstract class Builder {
    protected final Console console;
    protected final InputFormat inputFormat;

    public Builder(Console console, InputFormat inputFormat) { // конструктор
        this.console = console;
        this.inputFormat = inputFormat;
    }

    abstract public Object build();

    protected void printIfFileMode(String msg) {
        if (inputFormat == InputFormat.FILE) {
            console.println(msg);
        }
    }

    protected String readLine(String prompt) {
        console.print(prompt);
        String line = console.readln().trim();
        printIfFileMode(line);
        return line;
    }
}