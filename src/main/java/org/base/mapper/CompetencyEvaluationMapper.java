package org.base.mapper;

import org.base.dto.*;
import org.base.model.Competency;
import org.base.model.CompetencyEvaluation;
import org.base.model.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyEvaluationMapper {

    @Mapping(target = "competencyId", source = "competency", qualifiedByName = "mapCompetency")
    @Mapping(source = "score", target = "score", qualifiedByName = "mapScoreReqDto")
    CompetencyEvaluationReqDto toReqDto(CompetencyEvaluation competencyEvaluation);

    @Mapping(target = "competencyId", source = "competency", qualifiedByName = "mapCompetency")
    @Mapping(source = "score", target = "score", qualifiedByName = "mapScoreResDto")
    @Mapping(source = "evaluation.evaluationId", target = "evaluationId")
    CompetencyEvaluationResDto toResDto(CompetencyEvaluation competencyEvaluation);

    @Mapping(target = "competencyEvaluationId", ignore = true)
    @Mapping(target = "competency.competencyId", source = "competencyId")
    CompetencyEvaluation toEntity(CompetencyEvaluationReqDto competencyEvaluationReqDto);

    @Mapping(target = "competencyEvaluationId", ignore = true)
    @Mapping(target = "competency.competencyId", source = "competencyId")
    void updateEntityFromDto(CompetencyEvaluationReqDto competencyEvaluationReqDto, @MappingTarget CompetencyEvaluation existingCompetencyEvaluation);

    @Named("mapCompetency")
    default Long mapCompetency(Competency competency) {
        if(competency == null){
            return null;
        }
        return competency.getCompetencyId();
    }

    @Named("mapScoreReqDto")
    default ScoreReqDto mapScoreReqDto(Score score) {
        if(score == null){
            return null;
        }
        ScoreReqDto dto = new ScoreReqDto();
        dto.setScoreId(score.getScoreId() != null ? score.getScoreId() : null);
        dto.setValue(score.getValue());
        dto.setDescription(score.getDescription());
        return dto;
    }

    @Named("mapScoreResDto")
    default ScoreResDto mapScoreResDto(Score score) {
        if(score == null){
            return null;
        }
        ScoreResDto dto = new ScoreResDto();
        dto.setScoreId(score.getScoreId() != null ? score.getScoreId() : null);
        dto.setValue(score.getValue());
        dto.setDescription(score.getDescription());
        return dto;
    }

}
