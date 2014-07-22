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

/**
 * Endpoint for {@link FoodWeight} entity
 */
@Path('/weight')
@Produces(MediaType.APPLICATION_JSON)
class FoodWeightResource {
    private final FoodDAO FOOD_DAO
    private final FoodWeightDAO FOOD_WEIGHT_DAO

    FoodWeightResource(FoodDAO foodDAO, FoodWeightDAO foodWeightDAO) {
        this.FOOD_DAO = foodDAO
        this.FOOD_WEIGHT_DAO = foodWeightDAO
    }

    @Path('/{id}')
    @GET
    @Timed
    @UnitOfWork
    public List<FoodWeight> findWeightByFood(@PathParam('id') LongParam id) {
        Food food = FOOD_DAO.findById(id.get())
        return FOOD_WEIGHT_DAO.findAllByFood(food)
    }

}
