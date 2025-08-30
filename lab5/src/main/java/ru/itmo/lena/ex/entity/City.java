package ru.itmo.lena.ex.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;
//import utility.Validatable;
//import utility.Element;

public class City implements Comparable<City>{
    private static long nextId = 1;
    public static long getNextId() {
        return nextId;
    }
    public static void setNextId(long id) {
        nextId = id;
    }
    public static void increaseNextId() {
        nextId++;
    }
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float area; //Значение поля должно быть больше 0
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null
    private Integer metersAboveSeaLevel;
    private Boolean capital; //Поле не может быть null
    private Government government; //Поле не может быть null
    private StandardOfLiving standardOfLiving; //Поле может быть null
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

    public boolean isValid() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (creationDate == null) return false;
        if (coordinates == null) return false;
        if (area <= 0) return false;
        if (population <= 0 || population == null) return false;
        if (capital == null) return false;
        if (government == null) return false;
//        if (governor == null) return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Simple Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Simple Getter and Setter for coordinates
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    // Simple Getter and Setter for creationDate
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    // Simple Getter and Setter for area
    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    // Simple Getter and Setter for population
    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    // Simple Getter and Setter for metersAboveSeaLevel
    public Integer getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public void setMetersAboveSeaLevel(Integer metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    // Simple Getter and Setter for capital
    public Boolean getCapital() {
        return capital;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    // Simple Getter and Setter for government
    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    // Simple Getter and Setter for standardOfLiving
    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }

    // Simple Getter and Setter for governor
    public Human getGovernor() {
        return governor;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
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

