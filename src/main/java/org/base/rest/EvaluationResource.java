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
import org.base.dto.EvaluationReqDto;
import org.base.dto.PaginationMetadata;
import org.base.model.enums.ApprovalStage;
import org.base.model.enums.EvaluationByType;
import org.base.model.enums.ReviewStage;
import org.base.service.evaluation.EvaluationService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Slf4j
@Path("/evaluation")
public class EvaluationResource {

    @Inject
    EvaluationService service;

    @Inject
    @Named("evaluationMessageSource")
    MessageSource messageSource;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response save(@RequestBody EvaluationReqDto evaluationReqDto) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.save(evaluationReqDto))
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
            @QueryParam("page") int page,
            @QueryParam("size") int size) {

        page = (page < 1) ? 0 : page;
        size = (size < 0) ? 0 : size;
        long totalCount = service.countTotal();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.getPaginated(page, size))
                .metadata(new PaginationMetadata(page, size, totalPages, totalCount))
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
            @QueryParam("evaluationByType") EvaluationByType evaluationByType,
            @QueryParam("evaluationStage") ReviewStage reviewStage,
            @QueryParam("employeeId") Long employeeId) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.getByFilters(evaluationByType, reviewStage, employeeId))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Loggable
    public Response updateById(@PathParam("id") Long id, @RequestBody EvaluationReqDto evaluationReqDto) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.updateById(id, evaluationReqDto))
                .message(messageSource.getMessage("update.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

    @PUT
    @Path("/stage/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Loggable
    public Response updateStage(
            @PathParam("id") Long id,
            @QueryParam("reviewStage") ReviewStage reviewStage,
            @QueryParam("approvalStage") ApprovalStage approvalStage) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.updateReviewStage(id, reviewStage, approvalStage))
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
