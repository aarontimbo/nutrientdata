package com.atimbo.fitness.nutrient.dao

import com.atimbo.fitness.nutrient.domain.NutrientDefinition

import javax.persistence.EntityNotFoundException

class NutrientDefinitionDAOSpec extends DatabaseSpecification{

    NutrientDefinition definition
    NutrientDefinitionDAO nutrientDefinitionDAO

    def setup() {
        nutrientDefinitionDAO = new NutrientDefinitionDAO(sessionFactory)

        definition = new NutrientDefinition(id:1, unit: 'cup', description: 'one cup')
        nutrientDefinitionDAO.saveOrUpdate(definition)
    }

    List<Class<?>> getEntities() {
        return [
                NutrientDefinition
        ]
    }

    void 'retrieving all nutrient definitions returns a list'() {
        setup:
        List<NutrientDefinition> definitions = nutrientDefinitionDAO.findAll()

        expect:
        definitions
        definitions[0] == definition
    }

    void 'retrieving an existing nutrient definition by id is successful'() {
        setup:
        NutrientDefinition nutrientDefinition = nutrientDefinitionDAO.findById(definition.id)

        expect:
        nutrientDefinition
    }

    void 'retrieving a nutrient definition for a null id throws exception'() {
        when:
        nutrientDefinitionDAO.findById(null)

        then:
        thrown(NullPointerException)
    }

    void 'retrieving a nutrient definition for a non-existent id throws exception'() {
        when:
        nutrientDefinitionDAO.findById(999)

        then:
        thrown(EntityNotFoundException)
    }
}
