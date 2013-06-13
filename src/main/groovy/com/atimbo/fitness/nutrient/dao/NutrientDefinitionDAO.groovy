package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

class NutrientDefinitionDAO extends AbstractDAO<NutrientDefinition> {

    public NutrientDefinitionDAO(SessionFactory factory) {
        super(factory)
    }

    public List<NutrientDefinition> findAll() {
        return list(namedQuery('com.atimbo.fitness.nutrient.domain.NutrientDefinition.findAll'))
    }

}
