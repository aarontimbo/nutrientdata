package com.atimbo.fitness.nutrient

import com.atimbo.fitness.nutrient.conf.NutrientDataConfiguration
import com.atimbo.fitness.nutrient.dao.FoodDAO
import com.atimbo.fitness.nutrient.domain.Food
import com.atimbo.fitness.nutrient.resources.FoodResource
import com.atimbo.fitness.nutrient.resources.NutrientDataResource
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle

class NutrientDataService extends Service<NutrientDataConfiguration> {

    public static final List<Class<?>> ENTITIES = [Food]

    private final HibernateBundle<NutrientDataConfiguration> hibernate =
        new HibernateBundle<NutrientDataConfiguration>(Food.class, Food.class) {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(NutrientDataConfiguration configuration) {
                return configuration.getDatabaseConfiguration()
            }
        }

    public static void main(String[] args) throws Exception {
        new NutrientDataService().run(args)
    }

    @Override
    void initialize(Bootstrap<NutrientDataConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate)
    }

    @Override
    void run(NutrientDataConfiguration configuration, Environment environment) throws ClassNotFoundException {
        environment.addResource(new NutrientDataResource())

        final FoodDAO foodDAO = new FoodDAO(hibernate.getSessionFactory())
        environment.addResource(new FoodResource(foodDAO))
    }

}
