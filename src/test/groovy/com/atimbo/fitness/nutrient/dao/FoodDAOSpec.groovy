package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodGroup

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

    void 'find all retrieves a list of foods'() {
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

        when: 'retrieving all foods'
        List<Food> foods = foodDAO.findAll()

        then: 'a list of foods is returned'
        foods.size() == 2

    }

    void 'retrieving a food that does not exist throws an error'() {
        given: 'food that does not exist'
        Long nonExistentFoodId = 999

        when:
        foodDAO.findById(nonExistentFoodId)

        then:
        thrown(EntityNotFoundException)

    }



}
