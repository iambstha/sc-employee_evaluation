package org.base.mapper;

import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.CompetencyGroupCommentResDto;
import org.base.model.CompetencyGroup;
import org.base.model.CompetencyGroupComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyGroupCommentMapper {

    @Mapping(source = "competencyGroup", target = "competencyGroupId", qualifiedByName = "mapCompetencyGroupId")
    CompetencyGroupCommentReqDto toReqDto(CompetencyGroupComment competencyGroupComment);

    @Mapping(source = "competencyGroup", target = "competencyGroupId", qualifiedByName = "mapCompetencyGroupId")
    CompetencyGroupCommentResDto toResDto(CompetencyGroupComment competencyGroupComment);

    @Mapping(target = "competencyGroupCommentId", ignore = true)
    @Mapping(source = "competencyGroupId", target = "competencyGroup", qualifiedByName = "mapCompetencyGroupFromId")
    @Mapping(source = "employeeType", target = "employeeType")
    CompetencyGroupComment toEntity(CompetencyGroupCommentReqDto competencyGroupCommentReqDto);

    @Mapping(target = "competencyGroupCommentId", ignore = true)
    @Mapping(source = "competencyGroupId", target = "competencyGroup", qualifiedByName = "mapCompetencyGroupFromId")
    @Mapping(source = "employeeType", target = "employeeType")
    void updateEntityFromDto(CompetencyGroupCommentReqDto competencyGroupCommentReqDto, @MappingTarget CompetencyGroupComment existingCompetencyGroupComment);

    @Named("mapCompetencyGroupId")
    default Long mapCompetencyGroupId(CompetencyGroup competencyGroup) {
        if (competencyGroup == null) {
            return null;
        }
        return competencyGroup.getCompetencyGroupId();
    }

    @Named("mapCompetencyGroupFromId")
    default CompetencyGroup mapCompetencyGroupFromId(Long competencyGroupId) {
        if (competencyGroupId == null) {
            return null;
        }
        CompetencyGroup competencyGroup = new CompetencyGroup();
        competencyGroup.setCompetencyGroupId(competencyGroupId);
        return competencyGroup;
    }

}
