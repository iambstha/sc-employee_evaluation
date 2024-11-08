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
import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.PaginationMetadata;
import org.base.model.enums.EmployeeType;
import org.base.model.enums.ReviewStage;
import org.base.service.competencyGroupComment.CompetencyGroupCommentService;
import org.base.util.GeneralUtil;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Slf4j
@Path("/competency-group-comment")
public class CompetencyGroupCommentResource {

    @Inject
    CompetencyGroupCommentService service;

    @Inject
    @Named("competencyGroupCommentMessageSource")
    MessageSource messageSource;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response save(@RequestBody CompetencyGroupCommentReqDto competencyGroupCommentReqDto) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.save(competencyGroupCommentReqDto))
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
            @QueryParam("sortColumn") @DefaultValue("competencyGroupCommentId") String sortColumn) {

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
    public Response getByIdAndReviewStage(@PathParam("id") Long id, @QueryParam("reviewStage") ReviewStage reviewStage) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.getByIdAndReviewStage(id, reviewStage))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }
    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response getByFilters(
            @QueryParam("employeeType") EmployeeType employeeType,
            @QueryParam("competencyGroupId") Long competencyGroupId,
            @QueryParam("evaluationId") Long evaluationId,
            @QueryParam("employeeId") Long employeeId
    ) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.getByFilters(employeeType, competencyGroupId, evaluationId, employeeId))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }


    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Loggable
    public Response updateByIdAndReviewStage(
            @PathParam("id") Long id,
            @RequestBody CompetencyGroupCommentReqDto competencyGroupCommentReqDto,
            @QueryParam("reviewStage") ReviewStage reviewStage) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.updateByIdAndReviewStage(id, competencyGroupCommentReqDto, reviewStage))
                .message(messageSource.getMessage("update.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response deleteByIdAndReviewStage(@PathParam("id") Long id, @QueryParam("reviewStage") ReviewStage reviewStage) {
        service.deleteByIdAndReviewStage(id, reviewStage);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(messageSource.getMessage("delete.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

}
