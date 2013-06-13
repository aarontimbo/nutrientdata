package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodWeightDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodWeight
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.dropwizard.jersey.params.LongParam
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/weight")
@Produces(MediaType.APPLICATION_JSON)
class FoodWeightResource {
    private FoodDAO foodDAO
    private FoodWeightDAO foodWeightDAO

    FoodWeightResource(FoodDAO foodDAO, FoodWeightDAO foodWeightDAO) {
        this.foodDAO = foodDAO
        this.foodWeightDAO = foodWeightDAO
    }

    @Path("/{id}")
    @GET
    @Timed
    @UnitOfWork
    public List<FoodWeight> findWeightByFood(@PathParam('id') LongParam id) {
        Food food = foodDAO.findById(id.get())
        return foodWeightDAO.findAllByFood(food)
    }

}
