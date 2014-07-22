package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.dao.FoodGroupDAO
import com.atimbo.fitness.nutrient.domain.FoodGroup
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.dropwizard.jersey.params.LongParam
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Endpoint for {@link FoodGroup} entity
 */
@Path('/foodgroup')
@Produces(MediaType.APPLICATION_JSON)
class FoodGroupResource {
    private final FoodGroupDAO FOOD_GROUP_DAO

    public FoodGroupResource(FoodGroupDAO foodGroupDAO) {
        this.FOOD_GROUP_DAO = foodGroupDAO
    }

    @Path('/{id}')
    @GET
    @Timed
    @UnitOfWork
    public FoodGroup findFoodGroup(@PathParam('id') LongParam id) {
        return FOOD_GROUP_DAO.findById(id.get())
    }

}
