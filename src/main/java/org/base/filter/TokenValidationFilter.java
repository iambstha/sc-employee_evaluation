package org.base.filter;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;
import org.base.aop.publicEndpoint.PublicEndpoint;
import org.base.client.dto.UserInfoResDto;
import org.base.client.service.AuthClientService;
import org.base.exception.UnauthorizedAccessException;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;
import org.jboss.resteasy.reactive.server.ServerResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;

@Provider
@Priority(Priorities.AUTHENTICATION + 1)
@ApplicationScoped
public class TokenValidationFilter implements ContainerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenValidationFilter.class);

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    AuthClientService authClientService;

    @ServerRequestFilter
    public void onRequest(ContainerRequestContext requestContext) throws IOException {
        logger.info("Server request filter triggered . . . \n");
    }

    @ServerResponseFilter
    public void onResponse(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        logger.info("Server response filter triggered . . . \n");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {

        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(PublicEndpoint.class) || method.getDeclaringClass().isAnnotationPresent(PublicEndpoint.class)) {
            logger.info("Public endpoint accessed");
            return;
        }

        String token = requestContext.getHeaderString("X-TOKEN");
            if (token == null || token.isEmpty()) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

                logger.error("Unauthorized access");
//            return;
                throw new UnauthorizedAccessException("Unauthorized access");
            }

        try {
            UserInfoResDto userInfo = authClientService.validateToken(token);
            requestContext.setProperty("userInfo", userInfo);
        } catch (Exception e) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build());
            logger.error("Unauthorized access");
            throw new UnauthorizedAccessException("Unauthorized access");
        }
    }
}
