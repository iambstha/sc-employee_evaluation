package org.base.mapper;

import org.base.dto.EvaluationReqDto;
import org.base.dto.EvaluationResDto;
import org.base.model.CompetencyEvaluation;
import org.base.model.Evaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = QuarkusMappingConfig.class)
public interface EvaluationMapper {

    @Mapping(source = "competencyEvaluations", target = "competencyEvaluationIds", qualifiedByName = "mapCompetencyEvaluationIds")
    EvaluationReqDto toReqDto(Evaluation evaluation);

    @Mapping(source = "competencyEvaluations", target = "competencyEvaluationIds", qualifiedByName = "mapCompetencyEvaluationIds")
    EvaluationResDto toResDto(Evaluation evaluation);

    @Mapping(target = "evaluationId", ignore = true)
    @Mapping(source = "competencyEvaluationIds", target = "competencyEvaluations", qualifiedByName = "mapCompetencyEvaluationsFromIds")
    Evaluation toEntity(EvaluationReqDto evaluationReqDto);

    @Mapping(target = "evaluationId", ignore = true)
    @Mapping(source = "competencyEvaluationIds", target = "competencyEvaluations", qualifiedByName = "mapCompetencyEvaluationsFromIds")
    void updateEntityFromDto(EvaluationReqDto evaluationReqDto, @MappingTarget Evaluation existingEvaluation);

    @Named("mapCompetencyEvaluationIds")
    default List<Long> mapCompetencyEvaluationIds(List<CompetencyEvaluation> competencyEvaluations) {
        if (competencyEvaluations == null) {
            return null;
        }
        return competencyEvaluations.stream()
                .map(CompetencyEvaluation::getCompetencyEvaluationId)
                .collect(Collectors.toList());
    }

    @Named("mapCompetencyEvaluationsFromIds")
    default List<CompetencyEvaluation> mapCompetencyEvaluationsFromIds(List<Long> competencyEvaluationIds) {
        if (competencyEvaluationIds == null) {
            return null;
        }
        return competencyEvaluationIds.stream()
                .map(id -> {
                    CompetencyEvaluation competencyEvaluation = new CompetencyEvaluation();
                    competencyEvaluation.setCompetencyEvaluationId(id);
                    return competencyEvaluation;
                })
                .collect(Collectors.toList());
    }
}
