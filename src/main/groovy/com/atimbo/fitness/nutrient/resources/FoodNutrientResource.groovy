package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.dao.NutrientDefinitionDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
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
    // TODO: move usages of DAOs out of the resource into modules

    private final FoodDAO FOOD_DAO
    private final FoodNutrientDAO FOOD_NUTRIENT_DAO
    private final NutrientDefinitionDAO NUTRIENT_DEFINITION_DAO

    FoodNutrientResource(FoodDAO foodDAO,
                         FoodNutrientDAO foodNutrientDAO,
                         NutrientDefinitionDAO nutrientDefinitionDAO) {
        this.FOOD_DAO = foodDAO
        this.FOOD_NUTRIENT_DAO = foodNutrientDAO
        this.NUTRIENT_DEFINITION_DAO = nutrientDefinitionDAO
    }

    @Path('/{id}')
    @GET
    @Timed
    @UnitOfWork
    public List<FoodNutrient> findNutrientsByFood(@PathParam('id') LongParam id) {
        Food food = FOOD_DAO.findById(id.get())
        return FOOD_NUTRIENT_DAO.findAllByFood(food)
    }

    @GET
    @Timed
    @UnitOfWork
    public FoodNutrient findByFoodAndDefinition(@QueryParam('foodId') String foodId,
                                                @QueryParam('definitionId') String definitionId) {
        Food food = FOOD_DAO.findById(foodId.toLong())
        NutrientDefinition definition = NUTRIENT_DEFINITION_DAO.findById(definitionId.toLong())
        return FOOD_NUTRIENT_DAO.findByFoodAndDefinition(food, definition)
    }

}
