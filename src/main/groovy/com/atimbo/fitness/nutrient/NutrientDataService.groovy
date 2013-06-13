package com.atimbo.fitness.nutrient

import com.atimbo.fitness.nutrient.conf.NutrientDataConfiguration
import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodGroupDAO
import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.dao.FoodWeightDAO
import com.atimbo.fitness.nutrient.dao.NutrientDefinitionDAO
import com.atimbo.fitness.nutrient.domain.*
import com.atimbo.fitness.nutrient.resources.FoodGroupResource
import com.atimbo.fitness.nutrient.resources.FoodNutrientResource
import com.atimbo.fitness.nutrient.resources.FoodResource
import com.atimbo.fitness.nutrient.resources.FoodWeightResource
import com.atimbo.fitness.nutrient.resources.NutrientDataResource
import com.atimbo.fitness.nutrient.resources.NutrientDefinitionResource
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle

class NutrientDataService extends Service<NutrientDataConfiguration> {

    public static final List<Class<?>> SERVICE_ENTITIES = [
            Food,
            FoodGroup,
            FoodNutrient,
            FoodWeight,
            NutrientDefinition
    ]

    private final HibernateBundle<NutrientDataConfiguration> hibernate =
        new HibernateBundle<NutrientDataConfiguration>(serviceEntities) {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(NutrientDataConfiguration configuration) {
                return configuration.getDatabaseConfiguration()
            }
        }

    private final MigrationsBundle<NutrientDataConfiguration> migrations =
        new MigrationsBundle<NutrientDataConfiguration>() {
            @Override
            DatabaseConfiguration getDatabaseConfiguration(NutrientDataConfiguration configuration) {
                return configuration.getDatabaseConfiguration()
            }
        }

    public static void main(String[] args) throws Exception {
        new NutrientDataService().run(args)
    }

    @Override
    void initialize(Bootstrap<NutrientDataConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate)
        bootstrap.addBundle(migrations)
    }

    @Override
    void run(NutrientDataConfiguration configuration, Environment environment) throws ClassNotFoundException {
        environment.addResource(new NutrientDataResource())

        final FoodDAO foodDAO = new FoodDAO(hibernate.getSessionFactory())
        final FoodGroupDAO foodGroupDAO = new FoodGroupDAO(hibernate.getSessionFactory())
        final FoodNutrientDAO foodNutrientDAO = new FoodNutrientDAO(hibernate.getSessionFactory())
        final FoodWeightDAO foodWeightDAO = new FoodWeightDAO(hibernate.getSessionFactory())
        final NutrientDefinitionDAO nutrientDefinitionDAO = new NutrientDefinitionDAO(hibernate.getSessionFactory())
        environment.addResource(new FoodResource(foodDAO))
        environment.addResource(new FoodGroupResource(foodGroupDAO))
        environment.addResource(new FoodNutrientResource(foodDAO, foodNutrientDAO))
        environment.addResource(new FoodWeightResource(foodDAO, foodWeightDAO))
        environment.addResource(new NutrientDefinitionResource(nutrientDefinitionDAO))
    }

    @Override
    protected List<Class> getServiceEntities() {
        SERVICE_ENTITIES
    }
}
