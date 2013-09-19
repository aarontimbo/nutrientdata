package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.Query
import org.hibernate.SessionFactory

class NutrientDefinitionDAO extends AbstractDAO<NutrientDefinition> {

    public NutrientDefinitionDAO(SessionFactory factory) {
        super(factory)
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
