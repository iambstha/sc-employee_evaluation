package org.base.rest;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.base.aop.loggable.Loggable;
import org.base.config.MessageSource;
import org.base.domain.ApiResponse;
import org.base.dto.CompetencyGroupReqDto;
import org.base.dto.PaginationMetadata;
import org.base.model.enums.CompetencyStatus;
import org.base.model.enums.CompetencyType;
import org.base.service.competencyGroup.CompetencyGroupService;
import org.base.util.GeneralUtil;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Slf4j
@Path("/competency-group")
public class CompetencyGroupResource {

    @Inject
    CompetencyGroupService service;

    @Inject
    @Named("competencyGroupMessageSource")
    MessageSource messageSource;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response save(@RequestBody CompetencyGroupReqDto competencyGroupReqDto) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.save(competencyGroupReqDto))
                .statusCode(Response.Status.CREATED.getStatusCode())
                .status(Response.Status.CREATED.getReasonPhrase())
                .message(messageSource.getMessage("creation.success"))
                .build();

        return Response.status(Response.Status.CREATED).entity(apiResponse).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response getPaginated(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sortDirection") @DefaultValue("ASC") String sortDirection,
            @QueryParam("sortColumn") @DefaultValue("competencyGroupId") String sortColumn) {

        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.getPaginated(page, size, sortDirection, sortColumn))
                .metadata(new PaginationMetadata(page, size, GeneralUtil.countTotalPages(service.countTotal(), size), service.countTotal()))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response getById(@PathParam("id") Long id) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.getById(id))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response getByFilters(
            @QueryParam("competencyType") CompetencyType competencyType,
            @QueryParam("competencyStatus") CompetencyStatus status) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.getByFilters(competencyType, status))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Loggable
    public Response updateById(@PathParam("id") Long id, @RequestBody CompetencyGroupReqDto competencyGroupReqDto) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.updateById(id, competencyGroupReqDto))
                .message(messageSource.getMessage("update.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response deleteById(@PathParam("id") Long id) {
        service.deleteById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(messageSource.getMessage("delete.success"))
                .build();

        return Response.ok(apiResponse).build();
    }


}
