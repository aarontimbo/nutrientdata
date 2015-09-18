package com.atimbo.fitness.nutrient.api

/**
 * Transfer object with aggregated data for a specific nutrient
 */
class NutrientProfile {
    Set<NutrientItem> items = []
}

class NutrientItem {
    String nutrient
    BigDecimal amountInGrams = 0.0
}
