package org.base.mapper;

import org.base.dto.CompetencyEvaluationReqDto;
import org.base.dto.CompetencyEvaluationResDto;
import org.base.model.Competency;
import org.base.model.CompetencyEvaluation;
import org.base.model.Evaluation;
import org.base.model.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyEvaluationMapper {

//    @Mapping(target = "scoreId", source = "score", qualifiedByName = "mapScore")
    @Mapping(target = "evaluationId", source = "evaluation", qualifiedByName = "mapEvaluation")
    @Mapping(target = "competencyId", source = "competency", qualifiedByName = "mapCompetency")
    CompetencyEvaluationReqDto toReqDto(CompetencyEvaluation competencyEvaluation);

//    @Mapping(target = "scoreId", source = "score", qualifiedByName = "mapScore")
    @Mapping(target = "evaluationId", source = "evaluation", qualifiedByName = "mapEvaluation")
    @Mapping(target = "competencyId", source = "competency", qualifiedByName = "mapCompetency")
    CompetencyEvaluationResDto toResDto(CompetencyEvaluation competencyEvaluation);

    @Mapping(target = "competencyEvaluationId", ignore = true)
//    @Mapping(target = "score.scoreId", source = "scoreId")
    @Mapping(target = "evaluation.evaluationId", source = "evaluationId")
    @Mapping(target = "competency.competencyId", source = "competencyId")
    CompetencyEvaluation toEntity(CompetencyEvaluationReqDto competencyEvaluationReqDto);

    @Mapping(target = "competencyEvaluationId", ignore = true)
//    @Mapping(target = "score.scoreId", source = "scoreId")
    @Mapping(target = "evaluation.evaluationId", source = "evaluationId")
    @Mapping(target = "competency.competencyId", source = "competencyId")
    void updateEntityFromDto(CompetencyEvaluationReqDto competencyEvaluationReqDto, @MappingTarget CompetencyEvaluation existingCompetencyEvaluation);

    @Named("mapScore")
    default Long mapScore(Score score) {
        if(score == null){
            return null;
        }
        return score.getScoreId();
    }

    @Named("mapEvaluation")
    default Long mapEvaluation(Evaluation evaluation) {
        if(evaluation == null){
            return null;
        }
        return evaluation.getEvaluationId();
    }

    @Named("mapCompetency")
    default Long mapCompetency(Competency competency) {
        if(competency == null){
            return null;
        }
        return competency.getCompetencyId();
    }


}
