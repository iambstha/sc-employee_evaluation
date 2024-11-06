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
import org.base.dto.PaginationMetadata;
import org.base.service.apiLog.ApiLogService;
import org.base.util.GeneralUtil;

@Slf4j
@Path("/api-logs")
public class ApiLogResource {


    @Inject
    ApiLogService service;

    @Inject
    @Named("apiLogMessageSource")
    MessageSource messageSource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Loggable
    public Response getPaginated(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sortDirection") @DefaultValue("ASC") String sortDirection,
            @QueryParam("sortColumn") @DefaultValue("apiLogId") String sortColumn) {

        ApiResponse apiResponse = ApiResponse.builder()
                .data(service.getPaginated(page, size, sortDirection, sortColumn))
                .metadata(new PaginationMetadata(page, size, GeneralUtil.countTotalPages(service.countTotal(), size), service.countTotal()))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

}
