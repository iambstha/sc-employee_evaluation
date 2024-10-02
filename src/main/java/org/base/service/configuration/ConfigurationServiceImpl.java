package org.base.service.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.base.dto.ConfigurationReqDto;
import org.base.dto.ConfigurationResDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.base.config.MessageSource;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.ConfigurationMapper;
import org.base.model.Configuration;
import org.base.repository.ConfigurationRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    @Inject
    ConfigurationRepository configurationRepository;

    @Inject
    ConfigurationMapper configurationMapper;

    @Inject
    @Named("configurationMessageSource")
    MessageSource messageSource;

    @Override
    public ConfigurationResDto getConfigurationByKey(String key) {

        Configuration configuration = configurationRepository.findByKey(key)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration with key " + key + " not found."));

        return configurationMapper.toResDto(configuration);

    }

    @Override
    public ConfigurationResDto saveConfiguration(ConfigurationReqDto configurationReqDto) {
        try {
            Configuration configuration = configurationMapper.toEntity(configurationReqDto);
            configuration.persist();
            return configurationMapper.toResDto(configuration);
        } catch (Exception e) {
            throw new BadRequestException("Error occurred while creating configuration: " + e.getMessage(), e);
        }

    }

    @Override
    public List<ConfigurationResDto> getAll() {
        try {
            List<Configuration> configurations = configurationRepository.listAll();
            configurations.forEach(config -> logger.info("Fetched config: ID={}, Key={}, Value={}", config.getConfigurationId(), config.getKey(), config.getValue()));

            return configurations.stream().map(configurationMapper::toResDto).collect(Collectors.toList());
        }catch (Exception e){
            throw new BadRequestException("Error occurred while fetching configurations: " + e.getMessage(), e);
        }
    }

    @Override
    public ConfigurationResDto updateConfiguration(Long id, ConfigurationReqDto configurationReqDto) {
        Configuration existingConfiguration = configurationRepository.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration with ID " + id + " not found."));

        configurationMapper.updateEntityFromDto(configurationReqDto, existingConfiguration);
        configurationRepository.getEntityManager().merge(existingConfiguration);

        return configurationMapper.toResDto(existingConfiguration);
    }

    @Override
    public ConfigurationResDto updateConfiguration(String key, String value) {
        Configuration existingConfiguration = configurationRepository.findByKey(key)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration with key " + key + " not found."));

        existingConfiguration.setValue(value);
        configurationRepository.getEntityManager().merge(existingConfiguration);

        return configurationMapper.toResDto(existingConfiguration);
    }

    @Override
    public void deleteByKey(String key) {

        try {
            getConfigurationByKey(key);
            configurationRepository.deleteByKey(key);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }

}
