package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodWeight
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.Query
import org.hibernate.SessionFactory

class FoodWeightDAO extends AbstractDAO<FoodWeight> {

    public FoodWeightDAO(SessionFactory factory) {
        super(factory)
    }

    public List<FoodWeight> findAllByFood(Food food) {
        Query query = super.currentSession()
                .getNamedQuery('com.atimbo.fitness.nutrient.domain.FoodWeight.findAllByFood')
        query.setParameter('food', food)
        return list(query)
    }

}
