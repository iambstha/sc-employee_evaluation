package org.base.client.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.base.client.ConfigurationClient;
import org.base.client.dto.ClientResDto;
import org.base.client.dto.ConfigurationDataResDto;
import org.base.config.MessageSource;
import org.base.domain.CustomObjectMapper;
import org.base.exception.ClientException;
import org.base.exception.ResourceNotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.io.IOException;

@Slf4j
@ApplicationScoped
public class ConfigurationClientService {

    @Inject
    @Named("clientMessageSource")
    MessageSource messageSource;

    @Inject
    @RestClient
    ConfigurationClient client;

    @Inject
    CustomObjectMapper customObjectMapper;

    public ConfigurationDataResDto getConfigurationByKey(String key) {

        try {
            ClientResDto clientResDto = client.getConfigurationByKey(key);
            String json = customObjectMapper.writeValueAsString(clientResDto.getData());
            return customObjectMapper.getObjectMapper().readValue(json, ConfigurationDataResDto.class);
        } catch (IOException | BadRequestException | ProcessingException e){
            throw new ClientException(messageSource.getMessage("client.failed"));
        } catch (ClientWebApplicationException e){
            throw new ResourceNotFoundException(messageSource.getMessage("client.not_found.param", key));
        }

    }


}
