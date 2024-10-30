package org.base.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiLogResDto {

    private Long id;
    private String endpoint;
    private String methodName;
    private String parameters;
    private String username;
    private LocalDateTime timestamp;
    private List<String> repositoryAccessed;
    private String requestBody;
    private String responseBody;
    private int statusCode;
    private String userAgent;
    private String userIp;
    private String referer;
    private String correlationId;
    private Long executionTime;
    private String serverNode;

}
