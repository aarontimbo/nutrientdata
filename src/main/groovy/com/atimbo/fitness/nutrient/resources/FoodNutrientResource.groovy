package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.dropwizard.jersey.params.LongParam
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/nutrient")
@Produces(MediaType.APPLICATION_JSON)
class FoodNutrientResource {
    private FoodDAO foodDAO
    private FoodNutrientDAO foodNutrientDAO

    FoodNutrientResource(FoodDAO foodDAO, FoodNutrientDAO foodNutrientDAO) {
        this.foodDAO = foodDAO
        this.foodNutrientDAO = foodNutrientDAO
    }

    @Path("/{id}")
    @GET
    @Timed
    @UnitOfWork
    public List<FoodNutrient> findNutrientByFood(@PathParam('id') LongParam id) {
        Food food = foodDAO.findById(id.get())
        return foodNutrientDAO.findAllByFood(food)
    }

}
