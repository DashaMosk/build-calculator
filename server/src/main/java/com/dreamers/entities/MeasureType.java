package com.dreamers.entities;

public enum MeasureType {
    METER("m."),
    METER2("m2"),
    KILO("kg");

    private String shortName;

    MeasureType(String name) {
        shortName = name;
    }

    public String getShortName() {
        return shortName;
    }
}
