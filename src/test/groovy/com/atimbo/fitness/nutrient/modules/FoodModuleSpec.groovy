package com.atimbo.fitness.nutrient.modules

import com.atimbo.fitness.nutrient.api.FoodItem
import com.atimbo.fitness.nutrient.api.NutrientItem
import com.atimbo.fitness.nutrient.api.NutrientProfile
import com.atimbo.fitness.nutrient.api.NutrientProfileRequest
import com.atimbo.fitness.nutrient.api.NutrientType
import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.dao.FoodWeightDAO
import com.atimbo.fitness.nutrient.dao.NutrientDefinitionDAO
import com.atimbo.fitness.nutrient.dao.builders.FoodBuilder
import com.atimbo.fitness.nutrient.dao.builders.FoodNutrientBuilder
import com.atimbo.fitness.nutrient.dao.builders.FoodWeightBuilder
import com.atimbo.fitness.nutrient.dao.builders.NutrientDefinitionBuilder
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.FoodWeight
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import spock.lang.Specification

class FoodModuleSpec extends Specification {

    FoodBuilder foodBuilder
    FoodNutrientBuilder foodNutrientBuilder
    FoodWeightBuilder foodWeightBuilder
    FoodDAO foodDAO
    FoodModule module
    FoodNutrientDAO foodNutrientDAO
    FoodWeightDAO foodWeightDAO
    NutrientDefinitionDAO nutrientDefinitionDAO

    void setup() {
        foodBuilder = new FoodBuilder()
        foodNutrientBuilder = new FoodNutrientBuilder()
        foodWeightBuilder = new FoodWeightBuilder()
        foodDAO = Mock(FoodDAO)
        foodNutrientDAO = Mock(FoodNutrientDAO)
        foodWeightDAO = Mock(FoodWeightDAO)
        nutrientDefinitionDAO = Mock(NutrientDefinitionDAO)

        module = new FoodModule(foodDAO, foodNutrientDAO, foodWeightDAO, nutrientDefinitionDAO)
    }

    void 'find a food by id'() {
        given:
        Food expectedFood = foodBuilder.build()

        when:
        Food food = module.findById(expectedFood.id)

        then:
        1 * foodDAO.findById(expectedFood.id) >> expectedFood
        0 * _

        food
    }

    void 'get nutrient profile given a food and nutrient type'() {
        given:
        NutrientDefinition nutrientDefinition = NutrientDefinitionBuilder.build()
        Food food = foodBuilder.withId(1).build()
        FoodWeight foodWeight = foodWeightBuilder.withFood(food).build()
        FoodNutrient foodNutrient = foodNutrientBuilder
                .withFood(food)
                .withNutrientDefinition(nutrientDefinition)
                .withAmountPer100Grams(150)
                .build()
        FoodItem foodItem = new FoodItem(foodId: food.id, sequence: foodWeight.sequence, amount: 2.0)
        NutrientProfileRequest request = new NutrientProfileRequest(
                foodItems: [foodItem],
                nutrientType: NutrientType.PROTEIN
        )

        when:
        NutrientProfile profile = module.getNutrientProfile(request)

        then:
        1 * foodDAO.findById(food.id) >> food
        1 * foodWeightDAO.findByFoodAndSequence(food, foodWeight.sequence) >> foodWeight
        1 * nutrientDefinitionDAO.findByDescriptions([nutrientDefinition.description]) >> [nutrientDefinition]
        1 * foodNutrientDAO.findByFoodAndDefinitions(food, [nutrientDefinition]) >> [foodNutrient]
        0 * _

        profile.items.size() == 1
        300.0 == profile.items.find{ it.nutrient == nutrientDefinition.description }.amountInGrams
    }

}
