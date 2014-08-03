package com.atimbo.fitness.nutrient.modules

import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.domain.Food

import javax.persistence.EntityNotFoundException

/**
 * Entity handling service
 */
class FoodModule {

    FoodDAO foodDAO

    FoodModule(FoodDAO foodDAO) {
        this.foodDAO = foodDAO
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
}
