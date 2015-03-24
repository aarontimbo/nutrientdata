package com.atimbo.fitness.nutrient.modules

import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.dao.FoodWeightDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.FoodWeight

import javax.persistence.EntityNotFoundException

/**
 * Entity handling service
 */
class FoodModule {

    FoodDAO foodDAO
    FoodNutrientDAO foodNutrientDAO
    FoodWeightDAO foodWeightDAO

    FoodModule(FoodDAO foodDAO, FoodNutrientDAO foodNutrientDAO, FoodWeightDAO foodWeightDAO) {
        this.foodDAO = foodDAO
        this.foodNutrientDAO = foodNutrientDAO
        this.foodWeightDAO = foodWeightDAO
    }

    Food findById(Long id) {
        Food food = foodDAO.findById(id)
        if (!food) {
            throw new EntityNotFoundException("Could not find food with id: ${id}")
        }
        return food
    }

    List<Food> findAll() {
        return foodDAO.findAll()
    }

    List<Food> findAllByDescription(String description) {
        return foodDAO.findAllByDescription(description)
    }

    public List<FoodWeight> getWeights(Long id) {
        Food food = findById(id)
        return foodWeightDAO.findAllByFood(food)
    }

    public List<FoodNutrient> getNutrients(Long id) {
        Food food = findById(id)
        return foodNutrientDAO.findAllByFood(food)
    }

}
