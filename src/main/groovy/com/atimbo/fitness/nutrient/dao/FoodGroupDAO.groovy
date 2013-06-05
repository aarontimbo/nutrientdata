package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.FoodGroup
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

class FoodGroupDAO extends AbstractDAO<FoodGroup> {
    FoodGroupDAO(SessionFactory factory) {
        super(factory)
    }

    public FoodGroup findById(Long id) {
        return get(id)
    }

}
