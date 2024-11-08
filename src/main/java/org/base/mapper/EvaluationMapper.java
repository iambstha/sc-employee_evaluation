package org.base.mapper;

import org.base.dto.*;
import org.base.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(config = QuarkusMappingConfig.class)
public interface EvaluationMapper {

    @Mapping(source = "competencyEvaluations", target = "competencyEvaluations", qualifiedByName = "mapCompetencyEvaluationReqDtos")
    @Mapping(target = "evaluationByType", source = "evaluationByType")
    @Mapping(target = "reviewStage", source = "reviewStage")
    @Mapping(target = "approvalStage", source = "approvalStage")
    EvaluationReqDto toReqDto(Evaluation evaluation);

    @Mapping(source = "competencyEvaluations", target = "competencyEvaluations", qualifiedByName = "mapCompetencyEvaluationResDtos")
    @Mapping(target = "evaluationByType", source = "evaluationByType")
    @Mapping(target = "reviewStage", source = "reviewStage")
    @Mapping(target = "approvalStage", source = "approvalStage")
    EvaluationResDto toResDto(Evaluation evaluation);

    @Mapping(target = "evaluationId", ignore = true)
    @Mapping(target = "evaluationByType", source = "evaluationByType")
    @Mapping(target = "reviewStage", source = "reviewStage")
    @Mapping(target = "approvalStage", source = "approvalStage")
    Evaluation toEntity(EvaluationReqDto evaluationReqDto);

    @Mapping(target = "evaluationId", ignore = true)
    @Mapping(target = "evaluationByType", source = "evaluationByType")
    @Mapping(target = "reviewStage", source = "reviewStage")
    @Mapping(target = "approvalStage", source = "approvalStage")
    Evaluation toEntityFromResDto(EvaluationResDto byId);

    @Mapping(target = "evaluationId", ignore = true)
    @Mapping(target = "evaluationByType", source = "evaluationByType")
    @Mapping(target = "reviewStage", source = "reviewStage")
    @Mapping(target = "approvalStage", source = "approvalStage")
    void updateEntityFromDto(EvaluationReqDto evaluationReqDto, @MappingTarget Evaluation existingEvaluation);

    @Named("mapCompetencyEvaluationReqDtos")
    default List<CompetencyEvaluationReqDto> mapCompetencyEvaluationReqDtos(List<CompetencyEvaluation> competencyEvaluations) {
        if (competencyEvaluations == null) {
            return null;
        }
        return competencyEvaluations.stream().map(competencyEvaluation -> {
            CompetencyEvaluationReqDto dto = new CompetencyEvaluationReqDto();
            dto.setCompetencyId(Optional.ofNullable(competencyEvaluation.getCompetency())
                    .map(Competency::getCompetencyId)
                    .orElse(null));
            dto.setCompetencyEvaluationId(competencyEvaluation.getCompetencyEvaluationId());
            dto.setCompetencyId(competencyEvaluation.getCompetency() != null ? competencyEvaluation.getCompetency().getCompetencyId() : null);
            dto.setManagerComments(competencyEvaluation.getManagerComments());
            dto.setEmployeeComments(competencyEvaluation.getEmployeeComments());
            return dto;
        }).collect(Collectors.toList());
    }

    @Named("mapCompetencyEvaluationResDtos")
    default List<CompetencyEvaluationResDto> mapCompetencyEvaluationResDtos(List<CompetencyEvaluation> competencyEvaluations) {
        if (competencyEvaluations == null) {
            return null;
        }
        return competencyEvaluations.stream().map(competencyEvaluation -> {
            CompetencyEvaluationResDto dto = new CompetencyEvaluationResDto();
            dto.setCompetencyId(Optional.ofNullable(competencyEvaluation.getCompetency())
                    .map(Competency::getCompetencyId)
                    .orElse(null));

            if (competencyEvaluation.getScore() != null) {
                ScoreResDto scoreResDto = new ScoreResDto();
                scoreResDto.setScoreId(competencyEvaluation.getScore().getScoreId());
                scoreResDto.setValue(competencyEvaluation.getScore().getValue());
                scoreResDto.setDescription(competencyEvaluation.getScore().getDescription());
                dto.setScore(scoreResDto);
            }

            dto.setCompetencyEvaluationId(competencyEvaluation.getCompetencyEvaluationId());
            dto.setCompetencyId(competencyEvaluation.getCompetency() != null ? competencyEvaluation.getCompetency().getCompetencyId() : null);
            dto.setManagerComments(competencyEvaluation.getManagerComments());
            dto.setEmployeeComments(competencyEvaluation.getEmployeeComments());
            return dto;
        }).collect(Collectors.toList());
    }

    @Named("mapCompetencyEvaluationFromReqDtos")
    default List<CompetencyEvaluation> mapCompetencyEvaluationFromReqDtos(List<CompetencyEvaluationReqDto> competencyEvaluationReqDtos, Evaluation evaluation) {
        if (competencyEvaluationReqDtos == null) {
            return null;
        }

        return competencyEvaluationReqDtos.stream().map(competencyEvaluationReqDto -> {
            CompetencyEvaluation competencyEvaluation = new CompetencyEvaluation();
            competencyEvaluation.setEvaluation(evaluation);
            competencyEvaluation.setManagerComments(competencyEvaluationReqDto.getManagerComments());
            competencyEvaluation.setEmployeeComments(competencyEvaluationReqDto.getEmployeeComments());

            Competency competency = new Competency();
            competency.setCompetencyId(competencyEvaluationReqDto.getCompetencyId());
            competencyEvaluation.setCompetency(competency);

            if (competencyEvaluationReqDto.getScore() != null) {
                Score score = new Score();
                if (competencyEvaluationReqDto.getScore().getScoreId() != null) {
                    score.setScoreId(competencyEvaluationReqDto.getScore().getScoreId());
                }
                score.setValue(competencyEvaluationReqDto.getScore().getValue());
                score.setDescription(competencyEvaluationReqDto.getScore().getDescription());
                competencyEvaluation.setScore(score);
            }
            return competencyEvaluation;
        }).collect(Collectors.toList());
    }


}
