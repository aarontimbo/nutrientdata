package com.atimbo.fitness.nutrient.dao.builders

import com.atimbo.fitness.nutrient.domain.NutrientDefinition

/**
 * User: ast
 * Date: 4/28/15
 */
class NutrientDefinitionBuilder {

    static NutrientDefinition build() {
        return new NutrientDefinition(unit: 'g', description: 'PROTEIN')
    }
}
