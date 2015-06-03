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

/**
 * User: ast
 * Date: 6/2/15
 */
@Slf4j
class NutrientProfileModule {

    FoodDAO foodDAO
    FoodNutrientDAO foodNutrientDAO
    FoodWeightDAO foodWeightDAO
    NutrientDefinitionDAO nutrientDefinitionDAO

    NutrientProfileModule(FoodDAO foodDAO,
                          FoodNutrientDAO foodNutrientDAO,
                          FoodWeightDAO foodWeightDAO,
                          NutrientDefinitionDAO nutrientDefinitionDAO) {
        this.foodDAO               = foodDAO
        this.foodNutrientDAO       = foodNutrientDAO
        this.foodWeightDAO         = foodWeightDAO
        this.nutrientDefinitionDAO = nutrientDefinitionDAO
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
            Food food = foodDAO.findById(foodItem.foodId)
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
