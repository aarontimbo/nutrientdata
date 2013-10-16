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

@Path("/nutrient")
@Produces(MediaType.APPLICATION_JSON)
class FoodNutrientResource {
    private FoodDAO foodDAO
    private FoodNutrientDAO foodNutrientDAO
    private NutrientDefinitionDAO nutrientDefinitionDAO

    FoodNutrientResource(FoodDAO foodDAO, FoodNutrientDAO foodNutrientDAO, NutrientDefinitionDAO nutrientDefinitionDAO) {
        this.foodDAO = foodDAO
        this.foodNutrientDAO = foodNutrientDAO
        this.nutrientDefinitionDAO = nutrientDefinitionDAO
    }

    @Path("/{id}")
    @GET
    @Timed
    @UnitOfWork
    public List<FoodNutrient> findNutrientsByFood(@PathParam('id') LongParam id) {
        Food food = foodDAO.findById(id.get())
        return foodNutrientDAO.findAllByFood(food)
    }

    @GET
    @Timed
    @UnitOfWork
    public FoodNutrient findByFoodAndDefinition(@QueryParam('foodId') String foodId,
                                                @QueryParam('definitionId') String definitionId) {
        Food food = foodDAO.findById(foodId.toLong())
        NutrientDefinition definition = nutrientDefinitionDAO.findById(definitionId.toLong())
        return foodNutrientDAO.findByFoodAndDefinition(food, definition)
    }

}
