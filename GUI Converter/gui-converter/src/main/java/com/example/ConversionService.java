package com.example;

public class ConversionService {
    public static double convert(ConversionType type, boolean isUnit1ToUnit2, double value) {
        switch (type) {
            case MILES_KM:
                return isUnit1ToUnit2 ? milesToKm(value) : kmToMiles(value);
            case CELSIUS_FAHRENHEIT:
                return isUnit1ToUnit2 ? celsiusToFahrenheit(value) : fahrenheitToCelsius(value);
            case POUNDS_KG:
                return isUnit1ToUnit2 ? poundsToKg(value) : kgToPounds(value);
            default:
                throw new IllegalArgumentException("Unknown conversion type");
        }
    }

    /* "isUnit1ToUnit2" if true will convert from the first unit to the second and if false can do the opposite so it works backwards 
     * 
     * Uses the conversion units we made in ConversionType.java enum in the switches above
     * 
     * Below are just simply the conversion math to whichever unit you want
    */

    private static double milesToKm(double miles) {
        return roundToTwoDecimals(miles * 1.609344);
    }

    private static double kmToMiles(double km) {
        return roundToTwoDecimals(km / 1.609344);
    }

    private static double celsiusToFahrenheit(double celsius) {
        return roundToTwoDecimals((celsius * 9/5) + 32);
    }

    private static double fahrenheitToCelsius(double fahrenheit) {
        return roundToTwoDecimals((fahrenheit - 32) * 5/9);
    }

    private static double poundsToKg(double pounds) {
        return roundToTwoDecimals(pounds * 0.45359237);
    }

    private static double kgToPounds(double kg) {
        return roundToTwoDecimals(kg / 0.45359237);
    }

    private static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    
    