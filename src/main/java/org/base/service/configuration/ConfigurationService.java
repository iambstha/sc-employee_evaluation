package org.base.service.configuration;

import org.base.dto.ConfigurationReqDto;
import org.base.dto.ConfigurationResDto;

import java.util.List;

public interface ConfigurationService {

    ConfigurationResDto getConfigurationByKey(String key);

    ConfigurationResDto saveConfiguration(ConfigurationReqDto configurationReqDto);

    ConfigurationResDto updateConfiguration(Long id, ConfigurationReqDto configurationReqDto);

    List<ConfigurationResDto> getAll();

    ConfigurationResDto updateConfiguration(String key, String value);

    void deleteByKey(String key);

}
