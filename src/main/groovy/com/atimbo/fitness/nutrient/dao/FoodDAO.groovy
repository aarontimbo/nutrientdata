package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

public class FoodDAO extends AbstractDAO<Food> {

    public FoodDAO(SessionFactory factory) {
        super(factory)
    }

    public Food findById(Long id) {
        return get(id)
    }
}
