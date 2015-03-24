package com.atimbo.fitness.nutrient.modules

import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.dao.NutrientDefinitionDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.NutrientDefinition

/**
 * Access {@link FoodNutrient} data for {@link Food} items.
 */
class FoodNutrientModule {

    private final FoodDAO foodDAO
    private final FoodNutrientDAO foodNutrientDAO
    private final NutrientDefinitionDAO nutrientDefinitionDAO

    FoodNutrientModule(FoodDAO foodDAO,
                       FoodNutrientDAO foodNutrientDAO,
                       NutrientDefinitionDAO nutrientDefinitionDAO) {
        this.foodDAO = foodDAO
        this.foodNutrientDAO = foodNutrientDAO
        this.nutrientDefinitionDAO = nutrientDefinitionDAO
    }

    List<FoodNutrient> findNutrientsByFood(Food food) {
        return foodNutrientDAO.findAllByFood(food)
    }

    FoodNutrient findByFoodAndDefinition(String foodId, String definitionId) {
        Food food = foodDAO.findById(foodId.toLong())
        NutrientDefinition definition = nutrientDefinitionDAO.findById(definitionId.toLong())
        return foodNutrientDAO.findByFoodAndDefinition(food, definition)
    }
}
