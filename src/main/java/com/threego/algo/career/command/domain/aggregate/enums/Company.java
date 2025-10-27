package com.threego.algo.career.command.domain.aggregate.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Company {
    삼성전자, 카카오, 네이버, LG전자;

    @JsonCreator
    public static Company from(String value) {
        return Company.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
