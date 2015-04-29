package com.atimbo.fitness.nutrient.dao.builders

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodGroup

class FoodBuilder {

    Food food

    FoodBuilder() {
        food = new Food(
                shortDescription: 'Steak',
                longDescription:  'Grass fed Steak',
                foodGroup:        new FoodGroup(description: 'Beef')
        )
    }

    Food build() {
        return food
    }

    FoodBuilder withId(Long id) {
        food.id = id
        return this
    }

    FoodBuilder withFoodGroup(FoodGroup foodGroup) {
        food.foodGroup = foodGroup
        return this
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
