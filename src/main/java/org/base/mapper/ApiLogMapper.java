package org.base.mapper;

import org.base.dto.ApiLogResDto;
import org.base.model.ApiLog;
import org.mapstruct.Mapper;

@Mapper(config = QuarkusMappingConfig.class)
public interface ApiLogMapper {

    ApiLogResDto toResDto(ApiLog apiLog);

    ApiLog toEntityFromResDto(ApiLogResDto apiLogResDto);

}
