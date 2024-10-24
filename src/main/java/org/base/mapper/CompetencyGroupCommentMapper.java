package org.base.mapper;

import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.CompetencyGroupCommentResDto;
import org.base.model.CompetencyGroupAssessment;
import org.base.model.CompetencyGroupComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyGroupCommentMapper {

    @Mapping(source = "competencyGroupAssessment", target = "competencyGroupAssessmentId", qualifiedByName = "mapCompetencyGroupAssessmentId")
    CompetencyGroupCommentReqDto toReqDto(CompetencyGroupComment competencyGroupComment);

    @Mapping(source = "competencyGroupAssessment", target = "competencyGroupAssessmentId", qualifiedByName = "mapCompetencyGroupAssessmentId")
    CompetencyGroupCommentResDto toResDto(CompetencyGroupComment competencyGroupComment);

    @Mapping(target = "competencyGroupCommentId", ignore = true)
    @Mapping(source = "competencyGroupAssessmentId", target = "competencyGroupAssessment", qualifiedByName = "mapCompetencyGroupAssessmentFromId")
    CompetencyGroupComment toEntity(CompetencyGroupCommentReqDto competencyGroupCommentReqDto);

    @Mapping(target = "competencyGroupCommentId", ignore = true)
    @Mapping(source = "competencyGroupAssessmentId", target = "competencyGroupAssessment", qualifiedByName = "mapCompetencyGroupAssessmentFromId")
    @Mapping(source = "employeeType", target = "employeeType")
    void updateEntityFromDto(CompetencyGroupCommentReqDto competencyGroupCommentReqDto, @MappingTarget CompetencyGroupComment existingCompetencyGroupComment);

    @Named("mapCompetencyGroupAssessmentId")
    default Long mapCompetencyGroupAssessmentId(CompetencyGroupAssessment competencyGroupAssessment) {
        if (competencyGroupAssessment == null) {
            return null;
        }
        return competencyGroupAssessment.getCompetencyGroupAssessmentId();
    }

    @Named("mapCompetencyGroupAssessmentFromId")
    default CompetencyGroupAssessment mapCompetencyGroupAssessmentFromId(Long competencyGroupAssessmentId) {
        if (competencyGroupAssessmentId == null) {
            return null;
        }
        CompetencyGroupAssessment competencyGroupAssessment = new CompetencyGroupAssessment();
        competencyGroupAssessment.setCompetencyGroupAssessmentId(competencyGroupAssessmentId);
        return competencyGroupAssessment;
    }

}
