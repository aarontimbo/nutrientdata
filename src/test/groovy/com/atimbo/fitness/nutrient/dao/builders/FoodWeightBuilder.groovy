package com.atimbo.fitness.nutrient.dao.builders

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodWeight

/**
 * Build {@link FoodWeight}
 */
class FoodWeightBuilder {
    FoodWeight foodWeight

    FoodWeightBuilder() {
        this.foodWeight = new FoodWeight(
                sequence: 1,
                amount: 1.0,
                gramWeight: 100,
                description: '8 oz'
        )
    }

    FoodWeight build() {
        return foodWeight
    }

    FoodWeightBuilder withFood(Food food) {
        foodWeight.food = food
        return this
    }

    FoodWeightBuilder withGramWeight(Float gramWeight) {
        foodWeight.gramWeight = gramWeight
        return this
    }
}

