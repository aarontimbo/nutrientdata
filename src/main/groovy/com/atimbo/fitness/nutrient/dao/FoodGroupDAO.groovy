package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.FoodGroup
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

/**
 * Accessor methods for {@link FoodGroup} entity
 */
class FoodGroupDAO extends AbstractDAO<FoodGroup> {
    FoodGroupDAO(SessionFactory factory) {
        super(factory)
    }

    public FoodGroup findById(Long id) {
        return get(id)
    }

    /**
     * Only used for testing
     * @param foodGroup
     * @return
     */
    public FoodGroup saveOrUpdate(FoodGroup foodGroup) {
        persist(foodGroup)
    }

}
