package com.atimbo.fitness.nutrient.api

/**
 * Unit of Measure enum
 */
enum NutrientUnitOfMeasure {

    GRAM('g'),
    KCAL('kcal'),
    MILLIGRAM('mg'),
    INTERNATIONAL_UNIT('IU')

    final String id

    NutrientUnitOfMeasure(String id) {
        this.id = id
    }

    String toString() {
        id
    }
}
