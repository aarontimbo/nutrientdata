package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.dao.NutrientDefinitionDAO
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Endpoint for {@link NutrientDefinition} entity
 */
@Path('/nutrientdefinition')
@Produces(MediaType.APPLICATION_JSON)
class NutrientDefinitionResource {
    private final NutrientDefinitionDAO NUTRIENT_DEFINITION_DAO

    NutrientDefinitionResource(NutrientDefinitionDAO nutrientDefinitionDAO) {
        this.NUTRIENT_DEFINITION_DAO = nutrientDefinitionDAO
    }

    @GET
    @Timed
    @UnitOfWork
    public List<NutrientDefinition> findAll() {
        return NUTRIENT_DEFINITION_DAO.findAll()
    }

}
