package org.base.rest;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.base.aop.Loggable;
import org.base.config.MessageSource;
import org.base.domain.ApiResponse;
import org.base.dto.ApiLogResDto;
import org.base.dto.PaginationMetadata;
import org.base.service.apiLog.ApiLogService;

import java.util.List;

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
            @QueryParam("page") int page,
            @QueryParam("size") int size) {

        page = (page < 1) ? 1 : page;
        size = (size < 1) ? 10 : size;

        List<ApiLogResDto> logs = service.getPaginated(page, size);
        long totalCount = service.countTotal();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(logs)
                .metadata(new PaginationMetadata(page, size, totalPages, totalCount))
                .message(messageSource.getMessage("fetch.success"))
                .build();

        return Response.ok(apiResponse).build();
    }

}
