package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.dao.builders.FoodNutrientBuilder
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodGroup
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.NutrientDefinition

class FoodNutrientDAOSpec extends DatabaseSpecification {

    FoodDAO foodDAO
    FoodGroupDAO foodGroupDAO
    FoodNutrientDAO foodNutrientDAO
    FoodNutrientBuilder builder
    NutrientDefinitionDAO nutrientDefinitionDAO

    FoodGroup foodGroup
    Food food

    @Override
    def setup() {
        builder = new FoodNutrientBuilder()

        foodDAO = new FoodDAO(sessionFactory)
        foodGroupDAO = new FoodGroupDAO(sessionFactory)
        foodNutrientDAO = new FoodNutrientDAO(sessionFactory)
        nutrientDefinitionDAO = new NutrientDefinitionDAO(sessionFactory)

        setupFoodEntities()
    }

    @Override
    List<Class<?>> getEntities() {
        return [
                Food,
                FoodGroup,
                FoodNutrient,
                NutrientDefinition
        ]
    }

    void 'find a food nutrient by food and nutrient definition'() {
        given:
        NutrientDefinition protein = new NutrientDefinition(id: 1, unit: 'g', description: 'Protein')
        nutrientDefinitionDAO.saveOrUpdate(protein)
        FoodNutrient foodNutrient = builder
                .withFood(food)
                .withAmountPer100Grams(12.0)
                .withNutrientDefinition(protein)
                .build()
        foodNutrientDAO.saveOrUpdate(foodNutrient)

        when:
        FoodNutrient expectedFoodNutrient = foodNutrientDAO.findByFoodAndDefinition(food, protein)

        then:
        foodNutrient == expectedFoodNutrient
    }

    void 'find all food nutrients by food'() {
        given:
        NutrientDefinition protein = new NutrientDefinition(id: 1, unit: 'g', description: 'Protein')
        nutrientDefinitionDAO.saveOrUpdate(protein)
        FoodNutrient foodNutrient1 = builder
                .withFood(food)
                .withAmountPer100Grams(12.0)
                .withNutrientDefinition(protein)
                .build()
        foodNutrientDAO.saveOrUpdate(foodNutrient1)

        NutrientDefinition carb = new NutrientDefinition(id: 2, unit: 'g', description: 'carbohydrate')
        nutrientDefinitionDAO.saveOrUpdate(carb)
        FoodNutrient foodNutrient2 = new FoodNutrient(
                food:              food,
                definition:        carb,
                amountPer100Grams: 3
        )
        foodNutrientDAO.saveOrUpdate(foodNutrient2)

        when:
        List<FoodNutrient> foodNutrients = foodNutrientDAO.findAllByFood(food)

        then:
        foodNutrients.size() == 2
        foodNutrients.every { it.food == food }
        foodNutrients.every { it.definition == protein || it.definition == carb }
    }

    private void setupFoodEntities() {
        foodGroup = new FoodGroup(description: 'meat')
        foodGroupDAO.saveOrUpdate(foodGroup)

        food = new Food(
                longDescription: 'steak',
                shortDescription: 'steak',
                foodGroup: foodGroup
        )
        foodDAO.saveOrUpdate(food)
    }

}
