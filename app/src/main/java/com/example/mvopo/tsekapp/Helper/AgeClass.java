package com.example.mvopo.tsekapp.Helper;

public enum AgeClass {
    NEWBORN("Newborn"),
    INFANT("Infant"),
    PSAC("PSAC"),
    SCHOOLAGE("School Age"),
    ADOLESCENT("Adolescent"),
    ADULT("Adult"),
    SENIOR("Senior Citizen");

    private final String stringValue;

    AgeClass(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
