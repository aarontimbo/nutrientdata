package com.atimbo.fitness.nutrient.resources

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("/test/{output}")
class NutrientDataResource {
    @GET
    public String getTest(@PathParam("output") String output){
        return "test $output"
    }

}
