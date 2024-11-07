package org.base.client.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.base.client.AuthClient;
import org.base.client.dto.ClientResDto;
import org.base.client.dto.UserInfoResDto;
import org.base.config.MessageSource;
import org.base.domain.CustomObjectMapper;
import org.base.exception.ClientException;
import org.base.exception.ResourceNotFoundException;
import org.base.exception.UnauthorizedAccessException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@ApplicationScoped
public class AuthClientService {

    @Inject
    @Named("clientMessageSource")
    MessageSource messageSource;

    @Inject
    @RestClient
    AuthClient client;

    @Inject
    CustomObjectMapper customObjectMapper;

    public UserInfoResDto validateToken(String token) {

        try {
            ClientResDto clientResDto = client.validateToken(token);
            if(!Objects.equals(clientResDto.getStatus(), "200")){
                throw new UnauthorizedAccessException("Invalid token");
            }
            String json = customObjectMapper.writeValueAsString(clientResDto.getData());
            return customObjectMapper.getObjectMapper().readValue(json, UserInfoResDto.class);
        } catch (IOException | BadRequestException | ProcessingException e){
            throw new ClientException(messageSource.getMessage("client.failed"));
        } catch (ClientWebApplicationException e){
            throw new ResourceNotFoundException(messageSource.getMessage("client.not_found.param", token));
        } catch (UnauthorizedAccessException e){
            throw new UnauthorizedAccessException(e.getMessage());
        }

    }

    public boolean isAdmin(@Context ContainerRequestContext requestContext){
        UserInfoResDto userInfo = (UserInfoResDto) requestContext.getProperty("userInfo");

        return userInfo.getRoles().contains("ADMIN");
    }


}
