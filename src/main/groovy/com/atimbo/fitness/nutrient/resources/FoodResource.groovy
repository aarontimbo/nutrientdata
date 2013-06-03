package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.dropwizard.jersey.params.LongParam
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/food/{id}")
@Produces(MediaType.APPLICATION_JSON)
class FoodResource {
    private FoodDAO foodDAO

    public FoodResource(FoodDAO foodDAO){
        this.foodDAO = foodDAO
    }

    @GET
    @Timed
    @UnitOfWork
    public Food findFood(@PathParam('id') LongParam id) {
        return foodDAO.findById(id.get())
    }
}
