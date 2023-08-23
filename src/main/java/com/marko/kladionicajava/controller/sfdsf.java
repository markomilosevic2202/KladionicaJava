package com.marko.kladionicajava.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class sfdsf {
    public static LocalDate getNextDayInWeek(String dayOfWeek) {
        // Konvertujemo uneti dan u nedelji na engleski jezik
        String englishDayOfWeek = convertToEnglishDay(dayOfWeek);

        DayOfWeek inputDay = DayOfWeek.valueOf(englishDayOfWeek.toUpperCase());
        LocalDate today = LocalDate.now();

        int daysUntilInputDay = (inputDay.getValue() - today.getDayOfWeek().getValue() + 7) % 7;
        LocalDate nextDay = today.plusDays(daysUntilInputDay);

        return nextDay;
    }

    public static String convertToEnglishDay(String day) {
        switch (day.toLowerCase()) {
            case "ponedeljak":
                return "MONDAY";
            case "utorak":
                return "TUESDAY";
            case "sreda":
                return "WEDNESDAY";
            case "cetvrtak":
                return "THURSDAY";
            case "petak":
                return "FRIDAY";
            case "subota":
                return "SATURDAY";
            case "nedelja":
                return "SUNDAY";
            default:
                throw new IllegalArgumentException("Nepoznat dan: " + day);
        }
    }

    public static void main(String[] args) {
        String inputDayOfWeek = "sreda"; // Možete promeniti na željeni dan
        LocalDate nextDay = getNextDayInWeek(inputDayOfWeek);

        System.out.println("Prvi sledeći " + inputDayOfWeek + " je: " + nextDay);
    }
}



