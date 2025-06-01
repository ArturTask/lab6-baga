package ru.itmo.socket.common.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product implements Comparable<Product>, Serializable {
    private static int idsCounter = 0;

    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double price; //Поле может быть null, Значение поля должно быть больше в
    private UnitOfMeasure unitOfMeasure; //Поле может быть null
    private Organization manufacturer; //Поле может быть null

    public Product(String name, Coordinates coordinates, LocalDateTime creationDate, Double price, UnitOfMeasure unitOfMeasure, Organization manufacturer) {
        this.id = generateId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = manufacturer;
    }

    @Override
    public int compareTo(Product o) {
        return Integer.compare(this.id, o.getId());
    }

    public static int generateId() {
        return ++idsCounter;
    }

    public static Product generateDefault() {
        return new Product(generateId(), "default", new Coordinates(), LocalDateTime.now(), 100d, UnitOfMeasure.NONE,
                Organization.generateDefault());
    }

    public static Product generateDefaultWithoutId() {
        return new Product(-1, "default", new Coordinates(), LocalDateTime.now(), 100d, UnitOfMeasure.NONE,
                Organization.generateDefault());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Product)){
            return false;
        }
        return this.getId() == ((Product) obj).getId();
    }
}
