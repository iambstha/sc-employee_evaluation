package org.base.aop;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.base.domain.CustomObjectMapper;
import org.base.model.ApiLog;
import org.base.repository.ApiLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Loggable
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Inject
    ApiLogRepository apiLogRepository;

    @Context
    ContainerRequestContext requestContext;

    @Inject
    CustomObjectMapper customObjectMapper;

    @AroundInvoke
    public Object logMethod(InvocationContext context) throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        String methodName = context.getMethod().getName();
        String className = context.getMethod().getDeclaringClass().getName();
        String parameters = Arrays.toString(context.getParameters());

        String username = "test_user";
        String endpoint = className + "." + methodName;

        String userAgent = requestContext.getHeaderString("User-Agent");
        String referer = requestContext.getHeaderString("Referer");
        String userIp = requestContext.getHeaderString("X-Forwarded-For");
        if (userIp == null) {
            userIp = requestContext.getUriInfo().getBaseUri().getHost();
        }

        String correlationId = requestContext.getHeaderString("X-Correlation-ID");
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        logger.info("Request to {} with parameters: {}", endpoint, parameters);

        Object result;

        Response response = null;
        String responseBody = "";
        String requestBody = "";
        int statusCode = 0;

        try {

            requestBody = readRequestBody(requestContext);
            result = context.proceed();
            if (result != null) {
                responseBody = customObjectMapper.writeValueAsString(result);
            }

            response = (Response) result;
            if (response != null) {
                statusCode = response.getStatus();
                responseBody = customObjectMapper.writeValueAsString(response.getEntity());
            }

        } catch (Exception e) {
            logger.error("Error in method {}: {}", methodName, e.getMessage());
            throw e;
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            long executionTime = java.time.Duration.between(startTime, endTime).toMillis();

            List<String> accessedRepositories = RepositoryAccessTracker.getAccessedRepositories();

            ApiLog apiLog = new ApiLog();
            apiLog.setEndpoint(endpoint);
            apiLog.setMethodName(methodName);
            apiLog.setParameters(parameters);
            apiLog.setExecutionTime(executionTime);
            apiLog.setCreatedBy(username);
            apiLog.setUsername(username);
            apiLog.setUserAgent(userAgent);
            apiLog.setUserIp(userIp);
            apiLog.setReferer(referer);
            apiLog.setRequestBody(requestBody);
            if(statusCode == 0){
                apiLog.setResponseBody("Some error occurred.");
            }else {
                apiLog.setResponseBody(responseBody);
            }
            apiLog.setStatusCode(statusCode);
            apiLog.setCorrelationId(correlationId);
            apiLog.setTimestamp(startTime);
            apiLog.setRepositoryAccessed(accessedRepositories);

            apiLogRepository.save(apiLog);
            logger.info("Execution time for {}: {} ms", methodName, executionTime);

            RepositoryAccessTracker.clear();
        }

        return result;
    }

    private String readRequestBody(ContainerRequestContext requestContext) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(requestContext.getEntityStream(), StandardCharsets.UTF_8));
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            return requestBody.toString();
        } catch (IOException e) {
            logger.error("Failed to read request body: {}", e.getMessage());
            return "";
        }
    }

}
