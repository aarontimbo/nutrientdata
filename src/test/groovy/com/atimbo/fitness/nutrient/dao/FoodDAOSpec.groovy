package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.dao.builders.FoodBuilder
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodGroup
import spock.lang.Unroll

class FoodDAOSpec extends DatabaseSpecification {

    FoodBuilder builder
    FoodDAO foodDAO
    FoodGroupDAO foodGroupDAO

    def setup() {
        builder = new FoodBuilder()
        foodGroupDAO = new FoodGroupDAO(sessionFactory)
        foodDAO = new FoodDAO(sessionFactory)
    }

    List<Class<?>> getEntities() {
        return [
                Food,
                FoodGroup
        ]
    }

    void 'find food by id returns a food item'() {
        given:
        FoodGroup foodGroup = new FoodGroup(description: 'meat')
        foodGroupDAO.saveOrUpdate(foodGroup)

        Food expectedFood = builder.withFoodGroup(foodGroup).build()
        foodDAO.saveOrUpdate(expectedFood)

        when:
        Food food = foodDAO.findById(expectedFood.id)

        then:
        food == expectedFood
    }

    @Unroll
    void 'find #findBy retrieves an expected list of foods'() {
        given: 'a food'
        FoodGroup foodGroup = new FoodGroup(description: 'meat')
        foodGroupDAO.saveOrUpdate(foodGroup)
        Food food = builder.withFoodGroup(foodGroup).build()
        foodDAO.saveOrUpdate(food)

        and: 'another food'
        food = new Food(
                longDescription: 'pork chop',
                shortDescription: 'pork chop',
                foodGroup: foodGroup
        )
        foodDAO.saveOrUpdate(food)
        List<Food> foods = []

        when: 'retrieving foods'
        if (description) {
            foods = foodDAO.findAllByDescription(description)
        } else {
            foods = foodDAO.findAll()
        }

        then: 'a list of foods is returned'
        foods.size() == expectedListSize

        where:
        findBy          | description   || expectedListSize
        'all'           | null          || 2
        'by pork chop'  | 'pork'        || 1
    }

}
