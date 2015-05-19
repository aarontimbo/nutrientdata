package com.atimbo.fitness.nutrient.modules

import com.atimbo.fitness.nutrient.api.FoodItem
import com.atimbo.fitness.nutrient.api.NutrientItem
import com.atimbo.fitness.nutrient.api.NutrientProfile
import com.atimbo.fitness.nutrient.api.NutrientType
import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.dao.FoodWeightDAO
import com.atimbo.fitness.nutrient.dao.NutrientDefinitionDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.FoodWeight
import com.atimbo.fitness.nutrient.domain.NutrientDefinition

import javax.persistence.EntityNotFoundException

/**
 * Entity handling service
 */
class FoodModule {

    FoodDAO foodDAO
    FoodNutrientDAO foodNutrientDAO
    FoodWeightDAO foodWeightDAO
    NutrientDefinitionDAO nutrientDefinitionDAO

    FoodModule(FoodDAO foodDAO,
               FoodNutrientDAO foodNutrientDAO,
               FoodWeightDAO foodWeightDAO,
               NutrientDefinitionDAO nutrientDefinitionDAO) {
        this.foodDAO               = foodDAO
        this.foodNutrientDAO       = foodNutrientDAO
        this.foodWeightDAO         = foodWeightDAO
        this.nutrientDefinitionDAO = nutrientDefinitionDAO
    }

    Food findById(Long id) {
        Food food = foodDAO.findById(id)
        if (!food) {
            throw new EntityNotFoundException("Could not find food with id: ${id}")
        }
        return food
    }

    List<Food> findAll() {
        return foodDAO.findAll()
    }

    List<Food> findAllByDescription(String description) {
        return foodDAO.findAllByDescription(description)
    }

    List<FoodWeight> getWeights(Long id) {
        Food food = findById(id)
        return foodWeightDAO.findAllByFood(food)
    }

    List<FoodNutrient> getNutrients(Long id, List<NutrientDefinition> definitions) {
        Food food = findById(id)
        if (definitions) {
            return foodNutrientDAO.findByFoodAndDefinitions(food, definitions)
        }
        return foodNutrientDAO.findAllByFood(food)
    }

    NutrientProfile getNutrientProfile(List<FoodItem> foodItems, String definition = null) {
        NutrientProfile nutrientProfile
        List<NutrientDefinition> nutrientDefinitions = getNutrientDefinitions(definition)

        foodItems.each { FoodItem foodItem ->
            Food food = findById(foodItem.foodId)
            FoodWeight foodWeight = foodWeightDAO.findByFoodAndSequence(food, foodItem.sequence)

            List<FoodNutrient> foodNutrients = foodNutrientDAO.findByFoodAndDefinitions(food, nutrientDefinitions)
            foodNutrients.each { FoodNutrient foodNutrient ->
                NutrientItem nutrientItem
                if (nutrientProfile?.items) {
                    nutrientItem = nutrientProfile.items.find { it.nutrient == foodNutrient.definition.description }
                }

                if (!nutrientItem) {
                    nutrientItem = new NutrientItem(
                        nutrient:foodNutrient.definition.description, amountInGrams: 0.0)
                        amountInGrams:NutrientProfileCalculator.calculate(
                            foodItem.amount, foodWeight.gramWeight, foodNutrient.amountPer100Grams)

                    nutrientProfile.items << nutrientItem
                } else {
                    nutrientItem.amountInGrams = NutrientProfileCalculator.calculate(
                            foodItem.amount, foodWeight.gramWeight, foodNutrient.amountPer100Grams)
                }

            }

        }

        return nutrientProfile
    }

    private List<NutrientDefinition> getNutrientDefinitions(String definition = null) {
        List<String> definitions = []
        if (definition) {
            definitions << definition
        } else {
            definitions = NutrientType.values()*.id as Set
        }
        return nutrientDefinitionDAO.findByDescriptions(definitions)
    }

}
