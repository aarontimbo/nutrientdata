package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.dao.NutrientDefinitionDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.dropwizard.jersey.params.LongParam
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/nutrientdefinition")
@Produces(MediaType.APPLICATION_JSON)
class NutrientDefinitionResource {
    private NutrientDefinitionDAO nutrientDefinitionDAO

    NutrientDefinitionResource(NutrientDefinitionDAO nutrientDefinitionDAO) {
        this.nutrientDefinitionDAO = nutrientDefinitionDAO
    }

    @GET
    @Timed
    @UnitOfWork
    public List<NutrientDefinition> findAll() {
        return nutrientDefinitionDAO.findAll()
    }

}
