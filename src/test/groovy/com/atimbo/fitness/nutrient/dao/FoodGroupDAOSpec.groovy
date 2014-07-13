package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodGroup

class FoodGroupDAOSpec extends DatabaseSpecification {

    FoodGroupDAO foodGroupDAO

    def setup() {
        foodGroupDAO= new FoodGroupDAO(sessionFactory)
    }

    List<Class<?>> getEntities() {
        return [
                Food,
                FoodGroup
        ]
    }

    void 'retrieving an existing food group is successful'() {
        setup:
        FoodGroup foodGroup = new FoodGroup(description: 'Pork')
        foodGroupDAO.saveOrUpdate(foodGroup)

        expect:
        foodGroupDAO.findById(foodGroup.id)
    }

}
