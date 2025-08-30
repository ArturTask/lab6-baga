package ru.itmo.lena.ex.util;


import ru.itmo.lena.ex.enumeration.InputFormat;

import java.util.Scanner;

/**
 * Класс для работы с консольным вводом/выводом
 */
public class Console {
    private Scanner scanner;
//    private final boolean coloredOutput;
    private InputFormat inputFormat = InputFormat.CONSOLE;

    public Console(Scanner scanner) {
        this.scanner = scanner;
        this.inputFormat = InputFormat.CONSOLE;
    }

    public void setInputFormat(InputFormat format) {
        this.inputFormat = format;
    }

//    public InputFormat getInputFormat() {
//        return inputFormat;
//    }


    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }


    public String readln() {
        return scanner.nextLine();
    }

    public void print(Object obj) {
        System.out.print(obj);
    }

    public void println(Object obj) {
        System.out.println(obj);
    }

    public void printError(Object obj) {
        System.err.println(obj);
    }

    public void printSuccess(Object obj) {
        System.out.println(obj);
    }
}