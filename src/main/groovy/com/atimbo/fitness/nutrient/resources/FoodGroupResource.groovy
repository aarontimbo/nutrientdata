package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.dao.FoodGroupDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodGroup
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.dropwizard.jersey.params.LongParam
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/foodgroup")
@Produces(MediaType.APPLICATION_JSON)
class FoodGroupResource {
    private FoodGroupDAO foodGroupDAO

    public FoodGroupResource(FoodGroupDAO foodGroupDAO) {
        this.foodGroupDAO = foodGroupDAO
    }

    @Path('/{id}')
    @GET
    @Timed
    @UnitOfWork
    public FoodGroup findFoodGroup(@PathParam('id') LongParam id) {
        return foodGroupDAO.findById(id.get())
    }

}
