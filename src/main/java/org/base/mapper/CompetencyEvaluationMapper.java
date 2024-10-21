package org.base.mapper;

import org.base.dto.CompetencyEvaluationReqDto;
import org.base.dto.CompetencyEvaluationResDto;
import org.base.model.Competency;
import org.base.model.CompetencyEvaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyEvaluationMapper {

    @Mapping(target = "competencyId", source = "competency", qualifiedByName = "mapCompetency")
    CompetencyEvaluationReqDto toReqDto(CompetencyEvaluation competencyEvaluation);

    @Mapping(target = "competencyId", source = "competency", qualifiedByName = "mapCompetency")
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

}
