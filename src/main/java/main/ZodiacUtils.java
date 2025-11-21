package main;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Optional;

public class ZodiacUtils {

    public static String getZodiacByDate(int day, int month) {
        switch (month) {
            case 1: return (day <= 20) ? "козерог" : "водолей";
            case 2: return (day <= 19) ? "водолей" : "рыбы";
            case 3: return (day <= 20) ? "рыбы" : "овен";
            case 4: return (day <= 20) ? "овен" : "телец";
            case 5: return (day <= 21) ? "телец" : "близнецы";
            case 6: return (day <= 21) ? "близнецы" : "рак";
            case 7: return (day <= 22) ? "рак" : "лев";
            case 8: return (day <= 21) ? "лев" : "дева";
            case 9: return (day <= 23) ? "дева" : "весы";
            case 10: return (day <= 23) ? "весы" : "скорпион";
            case 11: return (day <= 22) ? "скорпион" : "стрелец";
            case 12: return (day <= 22) ? "стрелец" : "козерог";
            default: return null;
        }
    }

    public static Optional<LocalDate> tryParseDate(String input) {
        if (input == null || !input.matches("\\d{2}\\.\\d{2}")) {
            return Optional.empty();
        }

        try {
            String[] parts = input.split("\\.");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            LocalDate date = LocalDate.of(2000, month, day);
            return Optional.of(date);
        } catch (DateTimeException | NumberFormatException e) {
            return Optional.empty();
        }
    }
}
