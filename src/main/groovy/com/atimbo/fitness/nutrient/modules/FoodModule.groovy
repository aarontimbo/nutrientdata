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
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.FoodWeight
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import groovy.util.logging.Slf4j

import javax.persistence.EntityNotFoundException

/**
 * Entity handling service
 */
@Slf4j
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

    NutrientProfile getNutrientProfile(NutrientProfileRequest request) {
        List<FoodItem> foodItems = request.foodItems
        String definition = request.nutrientType?.id
        if (definition) {
            log.info "Creating profile for ${definition}."
        } else {
            log.info 'No nutrient type provided. Creating profile for all nutrient types.'
        }
        NutrientProfile nutrientProfile = new NutrientProfile()
        List<NutrientDefinition> nutrientDefinitions = getNutrientDefinitions(definition)

        foodItems.each { FoodItem foodItem ->
            Food food = findById(foodItem.foodId)
            FoodWeight foodWeight = foodWeightDAO.findByFoodAndSequence(food, foodItem.sequence)

            List<FoodNutrient> foodNutrients = foodNutrientDAO.findByFoodAndDefinitions(food, nutrientDefinitions)
            foodNutrients.each { FoodNutrient foodNutrient ->
                NutrientItem nutrientItem
                BigDecimal amountInGrams = 0
                nutrientItem = nutrientProfile.items.find { it.nutrient == foodNutrient.definition.description }

                if (nutrientItem) {
                    amountInGrams = nutrientItem.amountInGrams
                } else {
                    nutrientItem = new NutrientItem(nutrient:foodNutrient.definition.description)
                    nutrientProfile.items << nutrientItem
                }
                amountInGrams += NutrientProfileCalculator.calculate(
                        foodItem.amount, foodWeight.gramWeight, foodNutrient.amountPer100Grams)

                // Update the amount in grams for this nutrient item
                nutrientProfile.items.find {
                    it.nutrient == foodNutrient.definition.description
                }.amountInGrams = amountInGrams
            }

        }

        return nutrientProfile
    }

    private List<NutrientDefinition> getNutrientDefinitions(String definition = null) {
        List<String> definitions = NutrientType.values()*.id
        if (definition && definition in definitions) {
            definitions = [definition]
        }
        return nutrientDefinitionDAO.findByDescriptions(definitions)
    }

}
