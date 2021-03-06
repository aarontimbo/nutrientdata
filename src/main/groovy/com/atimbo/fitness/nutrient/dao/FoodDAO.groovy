package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.Food
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
import org.hibernate.criterion.Restrictions

/**
 * Accessor methods for {@link Food} entity
 */
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
        return get(id)
    }

    public List<Food> findAll() {
        return criteria().list()
    }

    public List<Food> findAllByDescription(String foodDescription) {
        String searchStr = QUERY_WILDCARD + foodDescription + QUERY_WILDCARD
        return criteria().add(
                Restrictions.like('longDescription', searchStr)
        ).list()
    }

}
