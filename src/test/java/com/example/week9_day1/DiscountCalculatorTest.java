package com.example.week9_day1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
class DiscountCalculatorTest {
    private DiscountCalculator discountCalculator;

    @BeforeEach
    void setUp() {
        discountCalculator = new DiscountCalculator();
    }

    @ParameterizedTest(name = "[{index}] цена {0} скидка {1}% -> ожидание {2}")
    @CsvSource({
            "1000.00, 10,     100.00", // 1. Обычный целый процент
            "200.00,  7.5,    15.00",  // 2. Дробный процент (7.5%)
            "999.90,  12.5,   124.99", // 3. Дробная цена и дробный процент (124.9875 -> округление 124.99)
            "50.00,   33.33,  16.67",  // 4. Периодическая дробь (16.665 -> 16.67)
            "4500.00, 50,     2250.00" // 5. Крупная покупка с половинной скидкой
    })
    @DisplayName("5 стандартных сценариев: расчет скидки с целыми и дробными процентами")
    void calculateDiscountAmount_standardValues_returnsCorrectDiscount(
            String priceStr, String rateStr, String expectedDiscountStr
    ) {
        BigDecimal price = new BigDecimal(priceStr);
        BigDecimal discountRate = new BigDecimal(rateStr);
        BigDecimal expected = new BigDecimal(expectedDiscountStr);

        BigDecimal actual = discountCalculator.calculateDiscountAmount(price, discountRate);

        assertThat(actual).isEqualByComparingTo(expected);
    }

    @ParameterizedTest(name = "[{index}] цена {0} скидка {1} -> ожидание 0.00")
    @CsvSource({
            "1000.0, 0",
            "0.00, 50.5",
            "0.00, 0"
    })
    @DisplayName("Граничное значение ноль для цены или скидки")
    void calculateDiscountAmount_zeroValues_returnsZeroDiscount(
            String priceStr, String rateStr
    ) {
        BigDecimal price = new BigDecimal(priceStr);
        BigDecimal discountRate = new BigDecimal(rateStr);

        BigDecimal actual = discountCalculator.calculateDiscountAmount(price, discountRate);

        assertThat(actual).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @ParameterizedTest(name = "Отрицательное значение: цена {0}, скидка {1} -> ожидание IllegalArgumentException")
    @CsvSource({
            "-0.01, 10.0",
            "100.0, -10.0",
            "-150.00, -20.0"
    })
    @DisplayName("Граничное отрицательное значение для цены или скидки")
    void calculateDiscountAmount_negativeValues_throwsException(
            String priceStr, String rateStr
    ) {
        BigDecimal price = new BigDecimal(priceStr);
        BigDecimal discountRate = new BigDecimal(rateStr);

        assertThatThrownBy(() -> discountCalculator.calculateDiscountAmount(price, discountRate)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Граничное максимальное значение. Скидка равна полной стоимости товара")
    void calculateDiscountAmount_maximumHundredPercent_returnFullPrice() {
        BigDecimal price = new BigDecimal("42.0");
        BigDecimal discountRate = new BigDecimal("100.00");

        BigDecimal actual = discountCalculator.calculateDiscountAmount(price, discountRate);

        assertThat(actual).isEqualByComparingTo(price);

    }

    @Test
    @DisplayName("Значение скидки больше 100%. Ожидание IllegalArgumentException")
    void calculateDiscountAmount_rateAboveHundred_throwsException() {
        BigDecimal price = new BigDecimal("100.0");
        BigDecimal discountRate = new BigDecimal("-10.00");

        assertThatThrownBy(() -> discountCalculator.calculateDiscountAmount(price, discountRate)).isInstanceOf(IllegalArgumentException.class);
    }
}
