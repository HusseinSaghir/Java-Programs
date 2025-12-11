package com.example;

public enum ConversionType {
    MILES_KM("Miles", "Kilometers"),
    CELSIUS_FAHRENHEIT("Celsius", "Fahrenheit"),
    POUNDS_KG("Pounds", "Kilograms");

    /*
     * An enum (enumeration) is a special Java type that represents a fixed set of constants. 
     * In this case, it represents different types of unit conversions.
     * 
     * 
     * Our Three Enum Constants: below
     * 
     * MILES_KM("Miles", "Kilometers") - Converts between miles and kilometers
       CELSIUS_FAHRENHEIT("Celsius", "Fahrenheit") - Converts between Celsius and Fahrenheit
       POUNDS_KG("Pounds", "Kilograms") - Converts between pounds and kilograms

       Each enum constant has two associated String values: Unit 1 & 2

       This pattern is useful for any application that needs to manage different types of conversions or mappings
     */

    private final String unit1;
    private final String unit2;

    ConversionType(String unit1, String unit2) {
        this.unit1 = unit1;
        this.unit2 = unit2;
    }

    public String getUnit1() {
        return unit1;
    }

    public String getUnit2() {
        return unit2;
    }
}