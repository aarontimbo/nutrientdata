package com.atimbo.fitness.nutrient.conf

import com.yammer.dropwizard.db.DatabaseConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class NutrientDataDatabaseConfiguration {

    final static String REQUIRE_SSL = '?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory'
    final static Logger logger = LoggerFactory.getLogger(NutrientDataDatabaseConfiguration.class);

    public static DatabaseConfiguration create(String databaseUrl) {
        // Use configuration file
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration()
        if (databaseUrl) {
            try {
                // Override configuration from config file with environment variable data
                URI dbUri = new URI(databaseUrl)
                String user = dbUri.getUserInfo().split(":")[0]
                String password = dbUri.getUserInfo().split(":")[1]
                String url = 'jdbc:postgresql://' + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + REQUIRE_SSL
                databaseConfiguration.user = user
                databaseConfiguration.password = password
                databaseConfiguration.url = url
                databaseConfiguration.driverClass = 'org.postgresql.Driver'
            } catch (URISyntaxException e) {
                logger.info(e.getMessage())
            }
        }
        return databaseConfiguration
    }
}
