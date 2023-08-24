package com.marko.kladionicajava.tools;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

public class DayService {
    public static String getNextDayInWeek(String dayOfWeek) {

        String englishDayOfWeek = convertToEnglishDay(dayOfWeek);

        DayOfWeek inputDay = DayOfWeek.valueOf(englishDayOfWeek.toUpperCase());
        LocalDate today = LocalDate.now();

        int daysUntilInputDay = (inputDay.getValue() - today.getDayOfWeek().getValue() + 7) % 7;
        LocalDate nextDay = today.plusDays(daysUntilInputDay);

        return String.valueOf(nextDay);
    }

    public static String convertToEnglishDay(String day) {
        switch (day.toLowerCase()) {
            case "pon.":
                return "MONDAY";
            case "uto.":
                return "TUESDAY";
            case "sre.":
                return "WEDNESDAY";
            case "čet.":
                return "THURSDAY";
            case "pet.":
                return "FRIDAY";
            case "sub.":
                return "SATURDAY";
            case "ned.":
                return "SUNDAY";
            default:
                throw new IllegalArgumentException("Nepoznat dan: " + day);
        }
    }
}
