package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.Query
import org.hibernate.SessionFactory

class FoodDAO extends AbstractDAO<Food> {

    public FoodDAO(SessionFactory factory) {
        super(factory)
    }

    public Food findById(Long id) {
        return get(id)
    }

    public List<Food> findAll() {
        Query query = super.currentSession().getNamedQuery('com.atimbo.fitness.nutrient.domain.Food.findAll')
        return list(query)
    }

    public List<Food> findAllByDescription(String description) {
        Query query = super.currentSession().getNamedQuery('com.atimbo.fitness.nutrient.domain.Food.findAllByDescription')
        query.setParameter('description', '%' + description + '%')
        return list(query)
    }

}
