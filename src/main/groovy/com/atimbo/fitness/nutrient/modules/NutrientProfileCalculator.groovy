package com.atimbo.fitness.nutrient.modules

import groovy.transform.CompileStatic

/**
 * Nutrient Profile calculator
 */
@CompileStatic
class NutrientProfileCalculator {

    static BigDecimal calculate(Float amount, Float gramWeight, Float amountPer100Grams) {
        return amount * amountPerGram(gramWeight, amountPer100Grams)
    }

    private static BigDecimal amountPerGram(Float gramWeight, Float amountPer100Grams) {
        return gramWeight * amountPer100Grams / 100
    }
}
