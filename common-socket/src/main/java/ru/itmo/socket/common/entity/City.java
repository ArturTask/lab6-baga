package ru.itmo.socket.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;
//import utility.Validatable;
//import utility.Element;

@Setter
@Getter
@NoArgsConstructor
public class City implements Serializable, Comparable<City>{
    @Setter
    @Getter
    private static long nextId = 1;

    public static void increaseNextId() {
        nextId++;
    }
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    // Simple Getter and Setter for name
    private String name; //Поле не может быть null, Строка не может быть пустой
    // Simple Getter and Setter for coordinates
    private Coordinates coordinates; //Поле не может быть null
    // Simple Getter and Setter for creationDate
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    // Simple Getter and Setter for area
    private float area; //Значение поля должно быть больше 0
    // Simple Getter and Setter for population
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null
    // Simple Getter and Setter for metersAboveSeaLevel
    private Integer metersAboveSeaLevel;
    // Simple Getter and Setter for capital
    private Boolean capital; //Поле не может быть null
    // Simple Getter and Setter for government
    private Government government; //Поле не может быть null
    // Simple Getter and Setter for standardOfLiving
    private StandardOfLiving standardOfLiving; //Поле может быть null
    // Simple Getter and Setter for governor
    private Human governor; //Поле может быть null

    public City(Long id, String name, Coordinates coordinates,
                LocalDateTime creationDate, float area, Long population,
                Integer metersAboveSeaLevel, Boolean capital, Government government,
                StandardOfLiving standardOfLiving, Human governor){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.capital = capital;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }

    public static City generateDefaultWithoutId() {
        long nextId = City.getNextId();
        return new City(
                nextId, // Автогенерация ID
                "Detroit-" + nextId,
                new Coordinates(1, 1),
                LocalDateTime.now(), // Автогенерация даты
                1f,
                1L,
                10,
                true,
                Government.DEMARCHY,
                StandardOfLiving.MEDIUM,
                null
        );
    }

    public boolean isValid() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (creationDate == null) return false;
        if (coordinates == null) return false;
        if (area <= 0) return false;
        if (population == null || population <= 0) return false;
        if (capital == null) return false;
        if (government == null) return false;
//        if (governor == null) return false;
        return true;
    }


    @Override
    public int compareTo(City other) {
        // 1. Сравнение по population (основной критерий)
//        int populationCompare = this.population.compareTo(other.population);
//        if (populationCompare != 0) return populationCompare;
        int populationCompare = Objects.compare(this.population, other.population,
                Comparator.nullsFirst(Comparator.naturalOrder())
        );
        // 2. Если population одинаковое, сравниваем по area
        int areaCompare = Float.compare(this.area, other.area);
        if (areaCompare != 0) return areaCompare;
        // 3. Если area одинаковое, сравниваем по metersAboveSeaLevel
        int maslCompare = Integer.compare(
                this.metersAboveSeaLevel != null ? this.metersAboveSeaLevel : Integer.MIN_VALUE,
                other.metersAboveSeaLevel != null ? other.metersAboveSeaLevel : Integer.MIN_VALUE
        );
        if (maslCompare != 0) return maslCompare;
        return this.name.compareTo(other.name);
    }



//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, coordinates, creationDate, area, population,
//                metersAboveSeaLevel, capital, government, standardOfLiving, governor);
//    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate); // неизменяеме поля
    }

    @Override
    public String toString() {
        return "City{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
                "\"creationDate\": \"" + creationDate.format(DateTimeFormatter.ISO_DATE_TIME) + "\", " +
                "\"area\": " + area + ", " +
                "\"population\": " + population + ", " +
                "\"metersAboveSeaLevel\": " + metersAboveSeaLevel + ", " +
                "\"capital\": " + capital + ", " +
                "\"government\": \"" + government + "\", " +
                "\"standardOfLiving\": " + (standardOfLiving == null ? "null" : standardOfLiving.toString()) + ", " +
                "\"governor\": " + (governor == null ? "null" : governor.toString()) + "}";
    }

}

