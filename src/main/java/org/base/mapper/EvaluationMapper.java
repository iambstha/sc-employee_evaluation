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
    EvaluationReqDto toReqDto(Evaluation evaluation);

    @Mapping(source = "competencyEvaluations", target = "competencyEvaluations", qualifiedByName = "mapCompetencyEvaluationResDtos")
    @Mapping(target = "evaluationByType", source = "evaluationByType")
    EvaluationResDto toResDto(Evaluation evaluation);

    @Mapping(target = "evaluationId", ignore = true)
    @Mapping(target = "evaluationByType", source = "evaluationByType")
    Evaluation toEntity(EvaluationReqDto evaluationReqDto);

    @Mapping(target = "evaluationId", ignore = true)
    @Mapping(target = "evaluationByType", source = "evaluationByType")
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
                dto.setScoreResDto(scoreResDto);
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
            CompetencyEvaluation entity = new CompetencyEvaluation();
            entity.setEvaluation(evaluation);
            entity.setManagerComments(competencyEvaluationReqDto.getManagerComments());
            entity.setEmployeeComments(competencyEvaluationReqDto.getEmployeeComments());

            Competency competency = new Competency();
            competency.setCompetencyId(competencyEvaluationReqDto.getCompetencyId());
            entity.setCompetency(competency);

            if (competencyEvaluationReqDto.getScoreReqDto() != null) {
                Score score = new Score();
                if (competencyEvaluationReqDto.getScoreReqDto().getScoreId() != null) {
                    score.setScoreId(competencyEvaluationReqDto.getScoreReqDto().getScoreId());
                }
                score.setValue(competencyEvaluationReqDto.getScoreReqDto().getValue());
                score.setDescription(competencyEvaluationReqDto.getScoreReqDto().getDescription());
                entity.setScore(score);
            }

            entity.setEvaluation(evaluation);

            return entity;

        }).collect(Collectors.toList());
    }

}
