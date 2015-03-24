package com.atimbo.fitness.nutrient

import com.atimbo.fitness.nutrient.conf.NutrientDataConfiguration
import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodGroupDAO
import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.dao.FoodWeightDAO
import com.atimbo.fitness.nutrient.dao.NutrientDefinitionDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.domain.FoodGroup
import com.atimbo.fitness.nutrient.domain.FoodNutrient
import com.atimbo.fitness.nutrient.domain.FoodWeight
import com.atimbo.fitness.nutrient.domain.NutrientDefinition
import com.atimbo.fitness.nutrient.modules.FoodModule
import com.atimbo.fitness.nutrient.modules.FoodNutrientModule
import com.atimbo.fitness.nutrient.resources.FoodGroupResource
import com.atimbo.fitness.nutrient.resources.FoodNutrientResource
import com.atimbo.fitness.nutrient.resources.FoodResource
import com.atimbo.fitness.nutrient.resources.FoodWeightResource
import com.atimbo.fitness.nutrient.resources.NutrientDefinitionResource
import com.google.common.collect.ImmutableList
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.config.FilterBuilder

import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.hibernate.SessionFactoryFactory
import com.yammer.dropwizard.migrations.MigrationsBundle
import org.eclipse.jetty.servlets.CrossOriginFilter

/**
 * Service providing endpoints for food nutrient data
 */
class NutrientDataService extends Service<NutrientDataConfiguration> {

    private static final int SIXTY = 60

    public static final List<Class<?>> SERVICE_ENTITIES = [
            Food,
            FoodGroup,
            FoodNutrient,
            FoodWeight,
            NutrientDefinition
    ]

    private final HibernateBundle<NutrientDataConfiguration> hibernate =
        new HibernateBundle<NutrientDataConfiguration>(
                ImmutableList.copyOf(serviceEntities),
                new SessionFactoryFactory()
        ) {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(NutrientDataConfiguration configuration) {
                return configuration.databaseConfiguration
            }
        }

    private final MigrationsBundle<NutrientDataConfiguration> migrations =
        new MigrationsBundle<NutrientDataConfiguration>() {
            @Override
            DatabaseConfiguration getDatabaseConfiguration(NutrientDataConfiguration configuration) {
                return configuration.databaseConfiguration
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

        // Add response headers via a filter
        FilterBuilder filterConfig = environment.addFilter(CrossOriginFilter, '/*')
        filterConfig.setInitParam(CrossOriginFilter.PREFLIGHT_MAX_AGE_PARAM, String.valueOf(SIXTY * SIXTY * 24))
        filterConfig.setInitParam(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, '*')

        FoodDAO foodDAO = new FoodDAO(hibernate.sessionFactory)
        FoodGroupDAO foodGroupDAO = new FoodGroupDAO(hibernate.sessionFactory)
        FoodNutrientDAO foodNutrientDAO = new FoodNutrientDAO(hibernate.sessionFactory)
        FoodWeightDAO foodWeightDAO = new FoodWeightDAO(hibernate.sessionFactory)
        NutrientDefinitionDAO nutrientDefinitionDAO = new NutrientDefinitionDAO(hibernate.sessionFactory)
        FoodModule foodModule = new FoodModule(foodDAO, foodNutrientDAO, foodWeightDAO)
        FoodNutrientModule foodNutrientModule = new FoodNutrientModule(foodDAO, foodNutrientDAO, nutrientDefinitionDAO)
        environment.addResource(new FoodResource(foodModule))
        environment.addResource(new FoodGroupResource(foodGroupDAO))
        environment.addResource(new FoodNutrientResource(foodNutrientModule))
        environment.addResource(new FoodWeightResource(foodDAO, foodWeightDAO))
        environment.addResource(new NutrientDefinitionResource(nutrientDefinitionDAO))
    }

    protected List<Class> getServiceEntities() {
        SERVICE_ENTITIES
    }
}
