package org.base.exception;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.base.domain.ErrorResponse;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        e.printStackTrace();

        return switch (e) {
            case ResourceNotFoundException resourceNotFoundException ->
                    buildErrorResponse(Response.Status.NOT_FOUND, e.getMessage(), Response.Status.NOT_FOUND);
            case BadRequestException badRequestException ->
                    buildErrorResponse(Response.Status.BAD_REQUEST, e.getMessage(), Response.Status.BAD_REQUEST);
            case UnauthorizedAccessException unauthorizedAccessException ->
                    buildErrorResponse(Response.Status.UNAUTHORIZED, e.getMessage(), Response.Status.UNAUTHORIZED);
            case ResourceAlreadyExistsException resourceAlreadyExistsException ->
                    buildErrorResponse(Response.Status.NOT_ACCEPTABLE, e.getMessage(), Response.Status.NOT_ACCEPTABLE);
            case ClientException clientException ->
                    buildErrorResponse(Response.Status.SERVICE_UNAVAILABLE, e.getMessage(), Response.Status.SERVICE_UNAVAILABLE);
            case ClientWebApplicationException clientWebApplicationException ->
                    buildErrorResponse(Response.Status.EXPECTATION_FAILED, e.getMessage(), Response.Status.EXPECTATION_FAILED);
            case ProcessingException processingException ->
                    buildErrorResponse(Response.Status.REQUEST_TIMEOUT, e.getMessage(), Response.Status.REQUEST_TIMEOUT);

            default ->
                    buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, "An unexpected error occurred", Response.Status.INTERNAL_SERVER_ERROR);
        };

    }

    private Response buildErrorResponse(Response.Status errorCode, String message, Response.Status status) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(errorCode.getStatusCode())
                .errorCode(errorCode)
                .message(message)
                .build();

        return Response.status(status).entity(errorResponse).build();
    }

}
