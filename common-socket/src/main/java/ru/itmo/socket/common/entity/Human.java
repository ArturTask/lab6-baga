package ru.itmo.socket.common.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Class representing a human (governor)
 */
public class Human implements Serializable, Comparable<Human> {
    private Integer height; // Значение поля должно быть больше 0
    private Date birthday;  // Поле может быть null

    public Human(Integer height, Date birthday) {
        if (height != null && height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        this.height = height;
        this.birthday = birthday;
    }

    public Human(Integer height) {
        this(height, null);
    }

    /**
     * Creates Human from string representation (format: "height;birthday")
     * @param s string in format "height;birthday" (e.g. "180;2020-01-01")
     */
    public Human(String s) {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        String[] parts = s.split(";");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid input format");
        }

        try {
            this.height = Integer.parseInt(parts[0]);
            if (this.height <= 0) {
                throw new IllegalArgumentException("Height must be greater than 0");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid height format");
        }

        if (!parts[1].equals("null")) {
            try {
                this.birthday = new SimpleDateFormat("yyyy-MM-dd").parse(parts[1]);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format", e);
            }
        }
    }

//    public Integer getHeight() {
//        return height;
//    }
//
//    public Date getBirthday() {
//        return birthday;
//    }

    @Override
    public int compareTo(Human o) {
        if (o == null) return 1;
        if (this.height == null && o.height == null) return 0;
        if (this.height == null) return -1;
        if (o.height == null) return 1;
        return Integer.compare(this.height, o.height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(height, human.height) &&
                Objects.equals(birthday, human.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, birthday);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = birthday != null ? dateFormat.format(birthday) : "null";
        return height + ";" + dateStr;
    }
}