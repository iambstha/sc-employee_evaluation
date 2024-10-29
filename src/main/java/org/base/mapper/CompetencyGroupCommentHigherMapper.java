package org.base.mapper;

import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.CompetencyGroupCommentResDto;
import org.base.model.CompetencyGroup;
import org.base.model.CompetencyGroupCommentHigher;
import org.base.model.Evaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyGroupCommentHigherMapper {

    @Mapping(source = "competencyGroupCommentHigherId", target = "commentId")
    @Mapping(source = "competencyGroup", target = "competencyGroupId", qualifiedByName = "mapCompetencyGroupId")
    @Mapping(source = "evaluation", target = "evaluationId", qualifiedByName = "mapEvaluationId")
    CompetencyGroupCommentReqDto toReqDto(CompetencyGroupCommentHigher competencyGroupCommentHigher);

    @Mapping(source = "competencyGroupCommentHigherId", target = "commentId")
    @Mapping(source = "competencyGroup", target = "competencyGroupId", qualifiedByName = "mapCompetencyGroupId")
    @Mapping(source = "evaluation", target = "evaluationId", qualifiedByName = "mapEvaluationId")
    CompetencyGroupCommentResDto toResDto(CompetencyGroupCommentHigher competencyGroupCommentHigher);

    @Mapping(target = "competencyGroupCommentHigherId", ignore = true)
//    @Mapping(source = "commentId", target = "competencyGroupCommentHigherId")
    @Mapping(source = "competencyGroupId", target = "competencyGroup", qualifiedByName = "mapCompetencyGroupFromId")
    @Mapping(source = "evaluationId", target = "evaluation", qualifiedByName = "mapEvaluationFromId")
    @Mapping(source = "employeeType", target = "employeeType")
    @Mapping(source = "reviewStage", target = "reviewStage")
    CompetencyGroupCommentHigher toEntity(CompetencyGroupCommentReqDto competencyGroupCommentReqDto);

    @Mapping(target = "competencyGroupCommentHigherId", ignore = true)
//    @Mapping(source = "commentId", target = "competencyGroupCommentHigherId")
    @Mapping(source = "competencyGroupId", target = "competencyGroup", qualifiedByName = "mapCompetencyGroupFromId")
    @Mapping(source = "evaluationId", target = "evaluation", qualifiedByName = "mapEvaluationFromId")
    @Mapping(source = "employeeType", target = "employeeType")
    @Mapping(source = "reviewStage", target = "reviewStage")
    void updateEntityFromDto(CompetencyGroupCommentReqDto competencyGroupCommentReqDto, @MappingTarget CompetencyGroupCommentHigher existingCompetencyGroupCommentHigher);

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
