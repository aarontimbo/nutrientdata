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

/**
 * Endpoint for {@link Food} entity
 */
@Path('/food')
@Produces(MediaType.APPLICATION_JSON)
class FoodResource {
    // TODO: move usages of DAOs out of the resource into modules

    private final FoodDAO FOOD_DAO

    public FoodResource(FoodDAO foodDAO) {
        this.FOOD_DAO = foodDAO
    }

    @GET
    @Timed
    @UnitOfWork(transactional = false)
    public List<Food> findAllFoods() {
        return FOOD_DAO.findAll()
    }

    @Path('/{id}')
    @GET
    @Timed
    @UnitOfWork
    public Food findFood(@PathParam('id') LongParam id) {
        return FOOD_DAO.findById(id.get())
    }

    @Path('/filter/{description}')
    @GET
    @Timed
    @UnitOfWork
    public List<Food> findAllByDescription(@PathParam('description') String description) {
        return FOOD_DAO.findAllByDescription(description)
    }

}
