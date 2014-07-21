package com.atimbo.fitness.nutrient.conf

import com.yammer.dropwizard.db.DatabaseConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Nutrient data database configuration
 */
class NutrientDataDatabaseConfiguration {

    private static final String SEPARATOR = ':'

    private static final String REQUIRE_SSL = '?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory'
    private static final Logger LOGGER = LoggerFactory.getLogger(NutrientDataDatabaseConfiguration)

    public static DatabaseConfiguration create(String databaseUrl) {
        // Use configuration file
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration()
        if (databaseUrl) {
            try {
                // Override configuration from config file with environment variable data
                URI dbUri = new URI(databaseUrl)
                String user = dbUri.userInfo.split(SEPARATOR)[0]
                String password = dbUri.userInfo.split(SEPARATOR)[1]
                String url = 'jdbc:postgresql://' + dbUri.host + SEPARATOR +
                             dbUri.port + dbUri.path + REQUIRE_SSL
                databaseConfiguration.user = user
                databaseConfiguration.password = password
                databaseConfiguration.url = url
                databaseConfiguration.driverClass = 'org.postgresql.Driver'
            } catch (URISyntaxException e) {
                LOGGER.info(e.message)
            }
        }
        return databaseConfiguration
    }
}
