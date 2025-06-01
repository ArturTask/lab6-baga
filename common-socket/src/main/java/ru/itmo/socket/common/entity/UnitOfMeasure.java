package ru.itmo.socket.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@ToString
public enum UnitOfMeasure implements Serializable {
    KILOGRAMS,
    METERS,
    MILLILITERS,
    NONE
    ;
}
