package com.atimbo.fitness.nutrient.api

/**
 * Enum of nutrient definition types.
 */
enum NutrientType {

    CALCIUM('Calcium, Ca', NutrientUnitOfMeasure.MILLIGRAM),
    CARBOHYDRATE('Carbohydrate, by difference', NutrientUnitOfMeasure.GRAM),
    CHOLESTEROL('Cholesterol', NutrientUnitOfMeasure.MILLIGRAM),
    ENERGY('Energy', NutrientUnitOfMeasure.KCAL),
    FOLATE_TOTAL('Folate, total', NutrientUnitOfMeasure.GRAM),
    FOLIC_ACID('Folic acid', NutrientUnitOfMeasure.GRAM),
    FRUCTOSE('Fructose', NutrientUnitOfMeasure.GRAM),
    GLUCOSE('Glucose (dextrose)', NutrientUnitOfMeasure.GRAM),
    PROTEIN('Protein', NutrientUnitOfMeasure.GRAM),
    SODIUM('Sodium, Na', NutrientUnitOfMeasure.MILLIGRAM),
    TOTAL_FAT('Total lipid (fat)', NutrientUnitOfMeasure.GRAM)

    final String id
    final NutrientUnitOfMeasure unitOfMeasure

    NutrientType(String id, NutrientUnitOfMeasure unitOfMeasure) {
        this.id = id
        this.unitOfMeasure = unitOfMeasure
    }

    String toString() {
        id
    }
}
