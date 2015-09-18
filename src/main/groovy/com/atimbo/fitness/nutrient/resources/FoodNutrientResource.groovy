package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.modules.FoodNutrientModule
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.dropwizard.jersey.params.LongParam
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

/**
 * Endpoint for {@link FoodNutrient} entity
 */
@Path('/nutrient')
@Produces(MediaType.APPLICATION_JSON)
class FoodNutrientResource {

    private final FoodNutrientModule foodNutrientModule

    FoodNutrientResource(FoodNutrientModule foodNutrientModule) {
        this.foodNutrientModule = foodNutrientModule
    }

    @Path('/{id}')
    @GET
    @Timed
    @UnitOfWork
    public List<FoodNutrient> findNutrientsByFood(@PathParam('id') LongParam id) {
        return foodNutrientModule.findNutrientsByFood(id)
    }

    @GET
    @Timed
    @UnitOfWork
    public FoodNutrient findByFoodAndDefinition(@QueryParam('foodId') String foodId,
                                                @QueryParam('definitionId') String definitionId) {
        return foodNutrientModule.findByFoodAndDefinition(foodId, definitionId)
    }

}
