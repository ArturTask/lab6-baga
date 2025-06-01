package ru.itmo.socket.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@ToString
public enum OrganizationType implements Serializable {
    COMMERCIAL,
    PUBLIC,
    TRUST,
    PRIVATE_LIMITED_COMPANY,
    OPEN_J0INT_STOCK_COMPANY,
    NONE
    ;
}
