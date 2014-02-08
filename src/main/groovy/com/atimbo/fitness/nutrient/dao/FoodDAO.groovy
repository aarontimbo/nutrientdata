package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.Query
import org.hibernate.SessionFactory

import javax.persistence.EntityNotFoundException

class FoodDAO extends AbstractDAO<Food> {

    static final String QUERY_WILDCARD = '%'

    public FoodDAO(SessionFactory factory) {
        super(factory)
    }

    /**
     * Only used for testing
     * @param food
     * @return
     */
    public Food saveOrUpdate(Food food) {
        persist(food)
    }

    public Food findById(Long id) {
        Food food = get(id)
        if (!food) {
            throw new EntityNotFoundException("Could not find food with id: ${id}")
        }
        return get(id)
    }

    public List<Food> findAll() {
        Query query = super.currentSession().getNamedQuery('com.atimbo.fitness.nutrient.domain.Food.findAll')
        return list(query)
    }

    public List<Food> findAllByDescription(String foodDescription) {
        Query query = super.currentSession()
                .getNamedQuery('com.atimbo.fitness.nutrient.domain.Food.findAllByDescription')
        query.setParameter('description', QUERY_WILDCARD + foodDescription + QUERY_WILDCARD)
        return list(query)
    }

}
