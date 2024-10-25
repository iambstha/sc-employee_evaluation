package org.base.mapper;

import org.base.dto.*;
import org.base.model.CompetencyGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyGroupMapper {

    CompetencyGroupReqDto toReqDto(CompetencyGroup competencyGroup);

    CompetencyGroupResDto toResDto(CompetencyGroup competencyGroup);

    @Mapping(target = "competencyGroupId", ignore = true)
    @Mapping(source = "competencyType", target = "competencyType")
    CompetencyGroup toEntity(CompetencyGroupReqDto competencyGroupReqDto);

    @Mapping(target = "competencyGroupId", ignore = true)
    @Mapping(source = "status", target = "status")
    @Mapping(source = "competencyType", target = "competencyType")
    void updateEntityFromDto(CompetencyGroupReqDto competencyGroupReqDto, @MappingTarget CompetencyGroup existingCompetencyGroup);

}
