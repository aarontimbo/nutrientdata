package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.FoodWeight
import com.atimbo.fitness.nutrient.modules.FoodModule
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
    private final FoodModule foodModule

    public FoodResource(FoodModule foodModule) {
        this.foodModule = foodModule
    }

    @GET
    @Timed
    @UnitOfWork(transactional = false)
    public List<Food> findAllFoods() {
        return foodModule.findAll()
    }

    @Path('/{id}')
    @GET
    @Timed
    @UnitOfWork
    public Food findFood(@PathParam('id') LongParam id) {
        return foodModule.findById(id.get())
    }

    @Path('/filter/{description}')
    @GET
    @Timed
    @UnitOfWork
    public List<Food> findAllByDescription(@PathParam('description') String description) {
        return foodModule.findAllByDescription(description)
    }

    @Path('/{id}/nutrients')
    @GET
    @Timed
    @UnitOfWork
    public List<FoodNutrient> getNutrients(@PathParam('id') LongParam id) {
        return foodModule.getNutrients(id.get())
    }

    @Path('/{id}/weights')
    @GET
    @Timed
    @UnitOfWork
    public List<FoodWeight> findWeightByFood(@PathParam('id') LongParam id) {
        return foodModule.getWeights(id.get())
    }

}
