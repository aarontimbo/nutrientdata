package com.atimbo.fitness.nutrient.modules

import com.atimbo.fitness.nutrient.dao.NutrientDefinitionDAO
import com.atimbo.fitness.nutrient.domain.NutrientDefinition

/**
 * Service to support NutrientDefinition data.
 */
class NutrientDefinitionModule {

    NutrientDefinitionDAO nutrientDefinitionDAO

    NutrientDefinitionModule(NutrientDefinitionDAO nutrientDefinitionDAO) {
        this.nutrientDefinitionDAO =  nutrientDefinitionDAO
    }

    List<NutrientDefinition> findByDescriptions(List<String> descriptions) {
        return nutrientDefinitionDAO.findByDescriptions(descriptions)
    }
}
