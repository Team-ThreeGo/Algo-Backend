package com.threego.algo.career.command.domain.aggregate.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Year {
    _2022("2022"),
    _2023("2023"),
    _2024("2024");

    private final String value;

    Year(String value) { this.value = value; }

    @JsonValue
    public String getValue() { return value; }

    @JsonCreator
    public static Year from(String value) {
        for (Year y : values()) {
            if (y.value.equals(value)) return y;
        }
        throw new IllegalArgumentException("Invalid year: " + value);
    }
}
