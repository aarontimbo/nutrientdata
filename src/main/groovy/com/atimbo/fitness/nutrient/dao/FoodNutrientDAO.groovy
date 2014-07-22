package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.Query
import org.hibernate.SessionFactory

/**
 * Accessor methods for {@link FoodNutrient} entity
 */
class FoodNutrientDAO extends AbstractDAO<FoodNutrient> {

    static final String FOOD_PARAM = 'food'

    public FoodNutrientDAO(SessionFactory factory) {
        super(factory)
    }

    public List<FoodNutrient> findAllByFood(Food food) {
        Query query = super.currentSession()
                .getNamedQuery('com.atimbo.fitness.nutrient.domain.FoodNutrient.findAllByFood')
        query.setParameter(FOOD_PARAM, food)
        return list(query)
    }

    public FoodNutrient findByFoodAndDefinition(Food foodDescription, NutrientDefinition definition) {
        Query query = super.currentSession()
                .getNamedQuery('com.atimbo.fitness.nutrient.domain.FoodNutrient.findByFoodAndDefinition')
        query.setParameter(FOOD_PARAM, foodDescription)
        query.setParameter('definition', definition)
        return uniqueResult(query)
    }

}
