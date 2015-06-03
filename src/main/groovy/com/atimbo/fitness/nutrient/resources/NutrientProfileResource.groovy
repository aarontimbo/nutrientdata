package com.atimbo.fitness.nutrient.resources

import com.atimbo.fitness.nutrient.api.NutrientProfile
import com.atimbo.fitness.nutrient.api.NutrientProfileRequest
import com.atimbo.fitness.nutrient.modules.NutrientProfileModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.metrics.annotation.Timed

import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Endpoint for {@link NutrientProfile}
 */
@Path('/nutrientProfile')
@Produces(MediaType.APPLICATION_JSON)
class NutrientProfileResource extends AbstractResource {

    private final NutrientProfileModule nutrientProfileModule

    public NutrientProfileResource(NutrientProfileModule nutrientProfileModule,
                                  ObjectMapper objectMapper) {
        super(objectMapper)
        this.nutrientProfileModule = nutrientProfileModule
    }

    @POST
    @Timed
    @UnitOfWork
    public NutrientProfile getNutrientProfile(NutrientProfileRequest nutrientProfileRequest) {
        return nutrientProfileModule.getNutrientProfile(nutrientProfileRequest)
    }

}
