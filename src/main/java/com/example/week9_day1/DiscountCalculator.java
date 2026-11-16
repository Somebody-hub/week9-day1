package com.example.week9_day1;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountCalculator {
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    public BigDecimal calculateDiscountAmount (BigDecimal price, BigDecimal percent) {

        if (price == null) {
            throw new IllegalArgumentException("Price can not be null");
        }
        if (percent == null) {
            throw new IllegalArgumentException("Discount percent can not be null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can not be less zero");
        }
        if (percent.compareTo(BigDecimal.ZERO) < 0 || percent.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Discount percent can not be less zero and biggger 100");
        }

        return price.multiply(percent).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
    }
}
