package com.atimbo.fitness.nutrient.conf

import com.fasterxml.jackson.annotation.JsonProperty
import com.yammer.dropwizard.config.Configuration
import com.yammer.dropwizard.db.DatabaseConfiguration

import javax.validation.Valid
import javax.validation.constraints.NotNull

class NutrientDataConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty
    private final DatabaseConfiguration database = new DatabaseConfiguration()

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database
    }


}
