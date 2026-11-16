package com.example.week9_day1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DurationParser {
    private static final Pattern VALID_INPUT = Pattern.compile("^(\\s*\\d+[dhm])+\\s*$");
    private static final Pattern TOKEN_PATTERN = Pattern.compile("(\\d+)([dmh])");

    private DurationParser() {

    }

    public static int parseToMinute(String durationStr) {
        if (durationStr == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }
        String trimmedStr = durationStr.trim();
        if (trimmedStr.isEmpty()) {
            throw new IllegalArgumentException("Строка не может быть пустой");
        }
        if (!VALID_INPUT.matcher(trimmedStr).matches()) {
            throw new IllegalArgumentException(
                    "Неверный: '" + durationStr +
                            "'. Допустимы только числа с единицами d, h, m (например: '2d 4h 30m')"
            );
        }
        Matcher matcher = TOKEN_PATTERN.matcher(trimmedStr);
        int totalMinutes = 0;
        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            char measure = matcher.group(2).charAt(0);

            totalMinutes += switch (measure) {
                case 'm' -> value;
                case 'h' -> value * 60;
                case 'd' -> value * 60 * 8;
                default -> throw new IllegalArgumentException("Неизвестная единица: " + measure);
            };
        }
        if (totalMinutes <= 0) {
            throw new IllegalArgumentException("Длительность должна быть больше 0");
        }

        return totalMinutes;
    }
}
