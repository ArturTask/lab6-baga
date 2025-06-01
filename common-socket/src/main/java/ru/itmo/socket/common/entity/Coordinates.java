package ru.itmo.socket.common.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Coordinates implements Serializable {
    private double x; //Значение поля должно быть больше -599
    private float y; //Значение поля должно быть больше -162
}