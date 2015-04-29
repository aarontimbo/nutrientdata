package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
import org.hibernate.criterion.Restrictions

/**
 * Accessor methods for {@link FoodNutrient} entity
 */
class FoodNutrientDAO extends AbstractDAO<FoodNutrient> {

    private static final String FOOD_PARAM       = 'food'
    private static final String DEFINITION_PARAM = 'definition'

    public FoodNutrientDAO(SessionFactory factory) {
        super(factory)
    }

    /**
     * Only used for testing
     * @param foodNutrient
     * @return
     */
    public FoodNutrient saveOrUpdate(FoodNutrient foodNutrient) {
        persist(foodNutrient)
    }

    public List<FoodNutrient> findAllByFood(Food food) {
        return criteria().add(Restrictions.eq(FOOD_PARAM, food)).list()
    }

    public FoodNutrient findByFoodAndDefinition(Food food, NutrientDefinition nutrientDef) {
        return criteria()
                .add(Restrictions.eq(FOOD_PARAM, food))
                .add(Restrictions.eq(DEFINITION_PARAM, nutrientDef))
                .uniqueResult()
    }

    public List<FoodNutrient> findByFoodAndDefinitions(Food food, List<NutrientDefinition> nutrientDefs) {

        return criteria()
                .add(Restrictions.eq(FOOD_PARAM, food))
                .add(Restrictions.in(DEFINITION_PARAM, nutrientDefs))
                .list()
    }

}
