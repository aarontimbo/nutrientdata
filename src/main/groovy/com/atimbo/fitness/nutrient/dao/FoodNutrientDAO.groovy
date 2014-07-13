package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.Query
import org.hibernate.SessionFactory
import org.hibernate.criterion.Restrictions

/**
 * Accessor methods for {@link FoodNutrient} entity
 */
class FoodNutrientDAO extends AbstractDAO<FoodNutrient> {

    static final String FOOD_PARAM = 'food'

    public FoodNutrientDAO(SessionFactory factory) {
        super(factory)
    }

    public List<FoodNutrient> findAllByFood(Food food) {
        return criteria().add(
                Restrictions.eq('food', food)
        ).list()
    }

    public FoodNutrient findByFoodAndDefinition(Food food, NutrientDefinition definition) {
        return criteria()
                .add(Restrictions.eq('food', food))
                .add(Restrictions.eq('definition', definition)
        ).uniqueResult()
    }

}
