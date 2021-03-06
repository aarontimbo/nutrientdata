package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodWeight
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
import org.hibernate.criterion.Restrictions

/**
 * Accessor methods for {@link FoodWeight} entity
 */
class FoodWeightDAO extends AbstractDAO<FoodWeight> {

    private static final String FOOD_PARAM = 'food'

    public FoodWeightDAO(SessionFactory factory) {
        super(factory)
    }

    public List<FoodWeight> findAllByFood(Food food) {
        return criteria().add(Restrictions.eq(FOOD_PARAM, food)).list()
    }

    public FoodWeight findByFoodAndSequence(Food food, Long sequence) {
        return uniqueResult(criteria()
                .add(Restrictions.eq(FOOD_PARAM, food))
                .add(Restrictions.eq('sequence', sequence))
        )
    }

}
