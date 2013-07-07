package com.atimbo.fitness.nutrient

import com.atimbo.fitness.nutrient.conf.NutrientDataConfiguration
import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.dao.FoodGroupDAO
import com.atimbo.fitness.nutrient.dao.FoodNutrientDAO
import com.atimbo.fitness.nutrient.dao.FoodWeightDAO
import com.atimbo.fitness.nutrient.domain.*
import com.atimbo.fitness.nutrient.resources.FoodGroupResource
import com.atimbo.fitness.nutrient.resources.FoodNutrientResource
import com.atimbo.fitness.nutrient.resources.FoodResource
import com.atimbo.fitness.nutrient.resources.FoodWeightResource
import com.atimbo.fitness.nutrient.resources.NutrientDataResource
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.config.FilterBuilder
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle
import org.eclipse.jetty.servlets.CrossOriginFilter

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

        // Allow access to the RESTful service running on same domain with different port
        // NEEDS TO BE REVISITED BEFORE GOING LIVE!!!
        FilterBuilder filterConfig = environment.addFilter(CrossOriginFilter.class, "/*");
        filterConfig.setInitParam(CrossOriginFilter.PREFLIGHT_MAX_AGE_PARAM, String.valueOf(60*60*24)); // 1 day - jetty-servlet CrossOriginFilter will convert to Int.
        filterConfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://localhost:8090, http://localhost:9000"); // comma separated list of allowed origin domains

        final FoodDAO foodDAO = new FoodDAO(hibernate.getSessionFactory())
        final FoodGroupDAO foodGroupDAO = new FoodGroupDAO(hibernate.getSessionFactory())
        final FoodNutrientDAO foodNutrientDAO = new FoodNutrientDAO(hibernate.getSessionFactory())
        final FoodWeightDAO foodWeightDAO = new FoodWeightDAO(hibernate.getSessionFactory())
        environment.addResource(new FoodResource(foodDAO))
        environment.addResource(new FoodGroupResource(foodGroupDAO))
        environment.addResource(new FoodNutrientResource(foodDAO, foodNutrientDAO))
        environment.addResource(new FoodWeightResource(foodDAO, foodWeightDAO))
    }

    @Override
    protected List<Class> getServiceEntities() {
        SERVICE_ENTITIES
    }
}
