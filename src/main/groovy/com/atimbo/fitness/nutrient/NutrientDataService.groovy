package com.atimbo.fitness.nutrient

import com.atimbo.fitness.nutrient.conf.NutrientDataConfiguration
import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodGroupDAO
import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.domain.*
import com.atimbo.fitness.nutrient.resources.FoodGroupResource
import com.atimbo.fitness.nutrient.resources.FoodNutrientResource
import com.atimbo.fitness.nutrient.resources.FoodResource
import com.atimbo.fitness.nutrient.resources.NutrientDataResource
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
            FoodNutrient
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
        environment.addResource(new FoodResource(foodDAO))
        environment.addResource(new FoodGroupResource(foodGroupDAO))
        // TODO:
        // add a food nutrient dao to build a list of food nutrients for a given food description
        // add a food nutrient resource to find food resources by food description id
        environment.addResource(new FoodNutrientResource(foodDAO, foodNutrientDAO))
    }

    @Override
    protected List<Class> getServiceEntities() {
        SERVICE_ENTITIES
    }
}
