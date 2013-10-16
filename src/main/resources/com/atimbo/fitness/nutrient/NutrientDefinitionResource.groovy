package com.atimbo.fitness.nutrient

import com.atimbo.fitness.nutrient.dao.NutrientDefinitionDAO
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.dropwizard.jersey.params.LongParam
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/nutrientDefinition")
@Produces(MediaType.APPLICATION_JSON)
class NutrientDefinitionResource {
    private NutrientDefinitionDAO nutrientDefinitionDAO

    NutrientDefinitionResource(NutrientDefinitionDAO nutrientDefinitionDAO) {
        this.nutrientDefinitionDAO = nutrientDefinitionDAO
    }

    @GET
    @Timed
    @UnitOfWork(transactional = false)
    public List<NutrientDefinition> findAllNutrientDefinitions() {
        return nutrientDefinitionDAO.findAll()
    }

    @Path("/{id}")
    @GET
    @Timed
    @UnitOfWork
    public List<NutrientDefinition> findNutrientDefinition(@PathParam('id') LongParam id) {
        return nutrientDefinitionDAO.findById(id.get())
    }

}
