package org.base.rest;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.base.aop.Loggable;
import org.base.config.MessageSource;
import org.base.domain.ApiResponse;
import org.base.dto.ConfigurationReqDto;
import org.base.service.configuration.ConfigurationService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Slf4j
@Path("/configuration")
public class ConfigurationResource {

    @Inject
    ConfigurationService configurationService;

    @Inject
    @Named("configurationMessageSource")
    MessageSource messageSource;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response saveConfiguration(@RequestBody ConfigurationReqDto configurationReqDto) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(configurationService.saveConfiguration(configurationReqDto))
                .statusCode(Response.Status.CREATED.getStatusCode())
                .status(Response.Status.CREATED.getReasonPhrase())
                .message(messageSource.getMessage("creation.success"))
                .build();

        return Response.status(Response.Status.CREATED).entity(apiResponse).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response getAllConfigurations() {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(configurationService.getAll())
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @GET
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response getConfigurationByKey(@PathParam("key") String key) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(configurationService.getConfigurationByKey(key))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response updateConfiguration(@PathParam("id") Long id, @RequestBody ConfigurationReqDto configurationReqDto) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(configurationService.updateConfiguration(id, configurationReqDto))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @PUT
    @Path("/key/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response updateConfigurationByKey(@PathParam("key") String key, @QueryParam("value") String value) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(configurationService.updateConfiguration(key, value))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @DELETE
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response deleteConfigurationById(@PathParam("key") String key) {
        configurationService.deleteByKey(key);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(messageSource.getMessage("delete.success"))
                .build();

        return Response.ok(apiResponse).build();
    }


}
