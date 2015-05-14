package com.atimbo.fitness.nutrient.modules

import com.atimbo.fitness.nutrient.api.NutrientProfile
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

    NutrientProfile getNutrientProfile(Long foodId, Long sequence, Float amount, String definition) {
        Food food = findById(foodId)
        FoodWeight foodWeight = foodWeightDAO.findByFoodAndSequence(food, sequence)
        NutrientDefinition nutrientDefinition = nutrientDefinitionDAO.findByDescriptions([definition]).first()
        FoodNutrient foodNutrient = foodNutrientDAO.findByFoodAndDefinition(food, nutrientDefinition)

        return new NutrientProfile(
                nutrient:nutrientDefinition.description,
                amountInGrams:NutrientProfileCalculator.calculate(
                        amount, foodWeight.gramWeight, foodNutrient.amountPer100Grams)
        )
    }
}
