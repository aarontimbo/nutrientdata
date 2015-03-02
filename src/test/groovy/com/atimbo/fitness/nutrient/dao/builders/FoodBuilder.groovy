package com.atimbo.fitness.nutrient.dao.builders

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodGroup

class FoodBuilder {

    Food food

    FoodBuilder() {
        food = new Food(
                shortDescription: 'Steak',
                longDescription: 'Grass fed Steak'
        )
    }

    Food build(FoodGroup foodGroup) {
        food.foodGroup = foodGroup
        return food
    }

    FoodBuilder withShortDescription(String description) {
        food.shortDescription = description
        return this
    }

    FoodBuilder withLongDescription(String description) {
        food.longDescription = description
        return this
    }

}
