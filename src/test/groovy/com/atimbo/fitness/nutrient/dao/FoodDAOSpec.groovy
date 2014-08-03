package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodGroup
import spock.lang.Unroll

import javax.persistence.EntityNotFoundException

class FoodDAOSpec extends DatabaseSpecification {

    FoodDAO foodDAO
    FoodGroupDAO foodGroupDAO

    def setup() {
        foodGroupDAO= new FoodGroupDAO(sessionFactory)
        foodDAO = new FoodDAO(sessionFactory)
    }

    List<Class<?>> getEntities() {
        return [
                Food,
                FoodGroup
        ]
    }

    void 'find food'() {
        given:
        FoodGroup foodGroup = new FoodGroup(description: 'meat')
        foodGroupDAO.saveOrUpdate(foodGroup)
        Food expectedFood = new Food(
                longDescription: 'steak',
                shortDescription: 'steak',
                foodGroup: foodGroup
        )
        foodDAO.saveOrUpdate(expectedFood)
        assert expectedFood.id

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
        Food food = new Food(
                longDescription: 'steak',
                shortDescription: 'steak',
                foodGroup: foodGroup
        )
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
