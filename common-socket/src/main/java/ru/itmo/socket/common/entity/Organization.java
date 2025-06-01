package ru.itmo.socket.common.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Organization implements Serializable {
    private static int idsCounter = 0;

    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String fullName; //Длина строки не должна быть больше 1610, Поле может быть null
    private OrganizationType type; //Поле не может быть null

    public static int generateId() {
        return ++idsCounter;
    }

    public static Organization generateDefault() {
        return new Organization(Organization.generateId(), "defaultOrg", "OOO defaultOrg", OrganizationType.NONE);
    }
}

