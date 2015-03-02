package com.atimbo.fitness.nutrient.dao.builders

import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import org.hibernate.SessionFactory

class FoodNutrientBuilder {

    FoodNutrient foodNutrient

    FoodNutrientBuilder() {
        foodNutrient = new FoodNutrient()
    }

    FoodNutrient build(Food food) {
        foodNutrient.food = food
        return foodNutrient
    }

    FoodNutrientBuilder withNutrientDefinition(NutrientDefinition nutrientDefinition) {
        foodNutrient.definition = nutrientDefinition
        return this
    }

    FoodNutrientBuilder withAmountPer100Grams(Float amountPer100Grams) {
        foodNutrient.amountPer100Grams = amountPer100Grams
        return this
    }

}
