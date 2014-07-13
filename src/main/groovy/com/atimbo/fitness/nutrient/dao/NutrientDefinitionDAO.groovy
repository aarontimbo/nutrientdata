package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.Query
import org.hibernate.SessionFactory

/**
 * Accessor methods for {@link NutrientDefinition} entity
 */
import javax.persistence.EntityNotFoundException

class NutrientDefinitionDAO extends AbstractDAO<NutrientDefinition> {

    public NutrientDefinitionDAO(SessionFactory factory) {
        super(factory)
    }

    public List<NutrientDefinition> findAll() {
        return criteria().list()
    }

    public NutrientDefinition findById(Long id) {
        NutrientDefinition definition = get(id)
        if (!definition) {
            throw new EntityNotFoundException("Could not find Nutrient Definition with id: $id")
        }
        return definition
    }

    /**
     * This should only be used for testing.
     *
     * @param definition
     * @return
     */
    public NutrientDefinition saveOrUpdate(NutrientDefinition definition) {
        return persist(definition)
    }

}
