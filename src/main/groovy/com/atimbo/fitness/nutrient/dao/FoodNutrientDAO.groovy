package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.Query
import org.hibernate.SessionFactory

class FoodNutrientDAO extends AbstractDAO<FoodNutrient> {

    public FoodNutrientDAO(SessionFactory factory) {
        super(factory)
    }

    public List<FoodNutrient> findAllByFood(Food food) {
        Query query = super.currentSession().getNamedQuery('com.atimbo.fitness.nutrient.domain.FoodNutrient.findAllByFood')
        query.setParameter('food', food)
        return list(query)
    }

}
