package com.example.week9_day1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskStatusTest {

    @ParameterizedTest(name = "[{index}] Переход {0} -> {1} разрешён: {2}")
    @CsvSource({
            "NEW, NEW, false",
            "NEW, IN_PROGRESS, true",
            "NEW, DONE, false",
            "NEW, CANCELLED, true",

            "IN_PROGRESS, NEW, false",
            "IN_PROGRESS, IN_PROGRESS, false",
            "IN_PROGRESS, DONE, true",
            "IN_PROGRESS, CANCELLED, true",

            "DONE, NEW, false",
            "DONE, IN_PROGRESS, true",
            "DONE, DONE, false",
            "DONE, CANCELLED, true",

            "CANCELLED, NEW, false",
            "CANCELLED, IN_PROGRESS, false",
            "CANCELLED, DONE, false",
            "CANCELLED, CANCELLED, false"
    })
    @DisplayName("Полная матрица переходов между всеми возможными парами статусов задачи")
    void canTransitionTo_allPossibleTransitions_returnsExpectedResult(
            TaskStatus from, TaskStatus to, boolean expected
    ) {
       boolean actual = from.canTransitionTo(to);

       assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Граничный случай null")
    void canTransitionTo_nullTarget_returnFalse() {
        for(TaskStatus status: TaskStatus.values()) {
            assertThat(status.canTransitionTo(null)).isFalse();
        }
    }
}