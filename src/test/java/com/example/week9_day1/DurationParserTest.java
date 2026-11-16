package com.example.week9_day1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class DurationParserTest {
    @ParameterizedTest(name = "[{index}] Строка \"{0}\" -> ожидаем {2} минут")
    @CsvSource({
            "45m, 45",
            "2h, 120",
            "1d, 480",
            "2h20m, 140",
            "2h 20m, 140",
            "2d 4h 30m, 1230",
            "  15m  , 15"
    })
    @DisplayName("Успешный парсинг корректных Jira-подобных строк в минуты")
    void parseToMinutes_validInputs_returnsCalculatedMinutes(
            String input, int expectedMinutes
    ) {
        int actual = DurationParser.parseToMinute(input);

        assertThat(actual).isEqualTo(expectedMinutes);
    }

    @Test
    @DisplayName("Входные данные null, ожидается IlegalArgumentException")
    void parseToMinutes_nullInput_throwsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> DurationParser.parseToMinute(null)
        );

        assertThat(ex.getMessage()).contains("null");
    }

    @ParameterizedTest(name = "[{index}] Пустой ввод: \"{0}\"")
    @ValueSource(strings = {
            "",
            " ",
            "         ",
            "\t",
            "\n"
    })
    @DisplayName("Пустые строки и пробелы вызывают IllegalArgumentExcepton")
    void parseToMinutes_blankStrings_throwsExcception(String input) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> DurationParser.parseToMinute(input)
        );
        assertThat(ex.getMessage()).contains("пустой");
    }

    @ParameterizedTest(name = "[{index}] Невалидный формат: \"{0}\"")
    @ValueSource(strings = {
            "abc",
            "-5m",
            "1h -20m",
            "0m",
            "0h 0m",
            "10x",
            "1h 20min",
            "2 hours",
            "1h, 20m"
    })
    @DisplayName("Любое нарушение формата отсекается с IllegalArgumentException")
    void parseToMinutes_invalidFormats_throwsIllegalArgumentException(String invalidInput) {
        assertThrows(
                IllegalArgumentException.class,
                () -> DurationParser.parseToMinute(invalidInput)
        );
    }
}