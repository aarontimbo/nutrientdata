package com.atimbo.fitness.nutrient.modules

/**
 * User: ast
 * Date: 5/13/15
 */
class NutrientProfileCalculatorSpec {

    void 'determine the amount per gram for food and nutrient type'() {
        given: 'salted butter'
        BigDecimal gramWeight = 227.0
        BigDecimal amountPer100Grams = 0.85

        when: 'we calculate the amount per gram'
        BigDecimal amountPerGram = NutrientProfileCalculator.amountPerGram(gramWeight, amountPer100Grams)

        then: 'the expected value is returned'
        amountPerGram == 1.9295001
    }
}
