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
import com.google.common.collect.ImmutableList
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
//import com.yammer.dropwizard.config.FilterBuilder
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.hibernate.SessionFactoryFactory
import com.yammer.dropwizard.migrations.MigrationsBundle
//import org.eclipse.jetty.servlets.CrossOriginFilter

class NutrientDataService extends Service<NutrientDataConfiguration> {

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
        environment.addResource(new NutrientDataResource())

        // Allow access to the RESTful service running on same domain with different port
        // NEEDS TO BE REVISITED BEFORE GOING LIVE!!!
        //FilterBuilder filterConfig = environment.addFilter(CrossOriginFilter.class, "/*");
        // 1 day - jetty-servlet CrossOriginFilter will convert to Int.
        //filterConfig.setInitParam(CrossOriginFilter.PREFLIGHT_MAX_AGE_PARAM, String.valueOf(60*60*24));
        //filterConfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM,
        // "http://localhost:8090, http://localhost:9000"); // comma separated list of allowed origin domains

        final FoodDAO FOOD_DAO = new FoodDAO(hibernate.sessionFactory)
        final FoodGroupDAO FOOD_GROUP_DAO = new FoodGroupDAO(hibernate.sessionFactory)
        final FoodNutrientDAO FOOD_NUTRIENT_DAO = new FoodNutrientDAO(hibernate.sessionFactory)
        final FoodWeightDAO FOOD_WEIGHT_DAO = new FoodWeightDAO(hibernate.sessionFactory)
        final NutrientDefinitionDAO NUTRIENT_DEFINITION_DAO = new NutrientDefinitionDAO(hibernate.sessionFactory)
        environment.addResource(new FoodResource(FOOD_DAO))
        environment.addResource(new FoodGroupResource(FOOD_GROUP_DAO))
        environment.addResource(new FoodNutrientResource(FOOD_DAO, FOOD_NUTRIENT_DAO, NUTRIENT_DEFINITION_DAO))
        environment.addResource(new FoodWeightResource(FOOD_DAO, FOOD_WEIGHT_DAO))
        environment.addResource(new NutrientDefinitionResource(NUTRIENT_DEFINITION_DAO))
    }

    @Override
    protected List<Class> getServiceEntities() {
        SERVICE_ENTITIES
    }
}
