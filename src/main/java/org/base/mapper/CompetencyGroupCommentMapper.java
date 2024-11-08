package org.base.mapper;

import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.CompetencyGroupCommentResDto;
import org.base.model.CompetencyGroup;
import org.base.model.CompetencyGroupComment;
import org.base.model.Evaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyGroupCommentMapper {

    @Mapping(source = "competencyGroupCommentId", target = "commentId")
    @Mapping(source = "competencyGroup", target = "competencyGroupId", qualifiedByName = "mapCompetencyGroupId")
    @Mapping(source = "evaluation", target = "evaluationId", qualifiedByName = "mapEvaluationId")
    CompetencyGroupCommentReqDto toReqDto(CompetencyGroupComment competencyGroupComment);

    @Mapping(source = "competencyGroupCommentId", target = "commentId")
    @Mapping(source = "competencyGroup", target = "competencyGroupId", qualifiedByName = "mapCompetencyGroupId")
    @Mapping(source = "evaluation", target = "evaluationId", qualifiedByName = "mapEvaluationId")
    CompetencyGroupCommentResDto toResDto(CompetencyGroupComment competencyGroupComment);

    @Mapping(target = "competencyGroupCommentId", ignore = true)
//    @Mapping(source = "commentId", target = "competencyGroupCommentId")
    @Mapping(source = "competencyGroupId", target = "competencyGroup", qualifiedByName = "mapCompetencyGroupFromId")
    @Mapping(source = "evaluationId", target = "evaluation", qualifiedByName = "mapEvaluationFromId")
    @Mapping(source = "employeeType", target = "employeeType")
    @Mapping(source = "reviewStage", target = "reviewStage")
    CompetencyGroupComment toEntity(CompetencyGroupCommentReqDto competencyGroupCommentReqDto);

    @Mapping(target = "competencyGroupCommentId", ignore = true)
//    @Mapping(source = "commentId", target = "competencyGroupCommentId")
    @Mapping(source = "competencyGroupId", target = "competencyGroup", qualifiedByName = "mapCompetencyGroupFromId")
    @Mapping(source = "evaluationId", target = "evaluation", qualifiedByName = "mapEvaluationFromId")
    @Mapping(source = "employeeType", target = "employeeType")
    @Mapping(source = "reviewStage", target = "reviewStage")
    void updateEntityFromDto(CompetencyGroupCommentReqDto competencyGroupCommentReqDto, @MappingTarget CompetencyGroupComment existingCompetencyGroupComment);

    @Named("mapEvaluationId")
    default Long mapEvaluationId(Evaluation evaluation) {
        if (evaluation == null) {
            return null;
        }
        return evaluation.getEvaluationId();
    }

    @Named("mapCompetencyGroupId")
    default Long mapCompetencyGroupId(CompetencyGroup competencyGroup) {
        if (competencyGroup == null) {
            return null;
        }
        return competencyGroup.getCompetencyGroupId();
    }

    @Named("mapEvaluationFromId")
    default Evaluation mapEvaluationFromId(Long evaluationId) {
        if (evaluationId == null) {
            return null;
        }
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationId(evaluationId);
        return evaluation;
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
