package io.floodplain.movie

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@javax.ws.rs.Path("/cdcapi")
class WebApi {

    @Inject
    lateinit var app: App

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun hello(): String {
        return "{\"total\": ${app.totalAdded.get()}}"
    }

}