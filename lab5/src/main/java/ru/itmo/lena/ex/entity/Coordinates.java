package ru.itmo.lena.ex.entity;

import java.util.Objects;

/**
 * Class representing geographic coordinates with validation
 * x - integer coordinate (max value: 850)
 * y - integer coordinate
 */
public class Coordinates implements Comparable<Coordinates> {
    private final Integer x; // Максимальное значение: 850
    private final int y;

    /**
     * Constructs Coordinates with validation
     * @param x X coordinate (null not allowed, max 850)
     * @param y Y coordinate
     * @throws IllegalArgumentException if validation fails
     */
    public Coordinates(Integer x, int y) {
        if (x == null) {
            throw new IllegalArgumentException("X координата не может быть null");
        }
        if (x > 850) {
            throw new IllegalArgumentException("X координата не должна превышать 850");
        }
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Compares coordinates first by x, then by y
     */
    @Override
    public int compareTo(Coordinates other) {
        if (other == null) return 1;
        int xCompare = Integer.compare(this.x, other.x); // выводит -1, 0, 1
        if (xCompare != 0) return xCompare; // Если поля x разные, сразу возвращаем результат
        return Integer.compare(this.y, other.y); // иначе сравниваем по y
    }

    @Override
    public boolean equals(Object o) { // o - Object, this - coord1, that - coord_o
        if (this == o) return true; // если сравниваем между собой сразу возвращаем true
        if (o == null || getClass() != o.getClass()) return false; // если сравниваем с null или другим классом, то они не равны
        Coordinates that = (Coordinates) o; // приводим Object к Coordinates, тк o точно является Coordinates
        return Objects.equals(y, that.y) && Objects.equals(x, that.x); //
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns coordinates in format "x;y"
     */
    @Override
    public String toString() {
        return x + ";" + y;
    }
}