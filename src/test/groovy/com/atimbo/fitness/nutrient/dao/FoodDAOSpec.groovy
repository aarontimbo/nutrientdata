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
                longDescription: 'meat',
                shortDescription: 'meat',
                foodGroup: foodGroup
        )
        foodDAO.saveOrUpdate(expectedFood)
        assert expectedFood.id

        when:
        Food food = foodDAO.findById(expectedFood.id)

        then:
        food == expectedFood

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
