package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.Query
import org.hibernate.SessionFactory

/**
 * Accessor methods for {@link NutrientDefinition} entity
 */
class NutrientDefinitionDAO extends AbstractDAO<NutrientDefinition> {

    public NutrientDefinitionDAO(SessionFactory factory) {
        super(factory)
    }

    /**
     * Only used for testing
     * @param nutrientDefinition
     * @return
     */
    public NutrientDefinition saveOrUpdate(NutrientDefinition nutrientDefinition) {
        persist(nutrientDefinition)
    }

    public List<NutrientDefinition> findAll() {
        Query query = super.currentSession()
                .getNamedQuery('com.atimbo.fitness.nutrient.domain.NutrientDefinition.findAll')
        return list(query)
    }

    public NutrientDefinition findById(Long id) {
        return get(id)
    }

}
