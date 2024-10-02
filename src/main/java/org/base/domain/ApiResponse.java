package org.base.domain;

import jakarta.ws.rs.core.Response;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ApiResponse {

    private String status;
    private int statusCode;
    private String message;
    private Object data;

    public ApiResponse() {
        this.status = Response.Status.OK.getReasonPhrase();
        this.statusCode = Response.Status.OK.getStatusCode();
        this.message = "";
        this.data = null;
    }

    public static ApiResponseBuilder builder() {
        ApiResponseBuilder builder = new ApiResponseBuilder();
        builder.status(Response.Status.OK.getReasonPhrase());
        builder.statusCode(Response.Status.OK.getStatusCode());
        return builder;
    }

}
