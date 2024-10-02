package org.base.mapper;

import org.base.dto.ConfigurationReqDto;
import org.base.dto.ConfigurationResDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.base.model.Configuration;

@Mapper(config = QuarkusMappingConfig.class)
public interface ConfigurationMapper {

    ConfigurationReqDto toReqDto(Configuration configuration);

    ConfigurationResDto toResDto(Configuration configuration);

    @Mapping(target = "configurationId", ignore = true)
    Configuration toEntity(ConfigurationReqDto configurationReqDto);

    @Mapping(target = "configurationId", ignore = true)
    void updateEntityFromDto(ConfigurationReqDto configurationReqDto, @MappingTarget Configuration existingConfiguration);
}
