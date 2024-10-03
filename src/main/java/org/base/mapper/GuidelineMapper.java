package org.base.mapper;

import org.base.dto.GuidelineReqDto;
import org.base.dto.GuidelineResDto;
import org.base.model.Competency;
import org.base.model.Guideline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = QuarkusMappingConfig.class)
public interface GuidelineMapper {

    @Mapping(source = "competency", target = "competencyId", qualifiedByName = "mapCompetencyId")
    GuidelineReqDto toReqDto(Guideline guideline);

    @Mapping(source = "competency", target = "competencyId", qualifiedByName = "mapCompetencyId")
    GuidelineResDto toResDto(Guideline guideline);

    @Mapping(target = "guidelineId", ignore = true)
    @Mapping(source = "competencyId", target = "competency", qualifiedByName = "mapCompetencyFromId")
    Guideline toEntity(GuidelineReqDto guidelineReqDto);

    @Mapping(target = "guidelineId", ignore = true)
    @Mapping(source = "competencyId", target = "competency", qualifiedByName = "mapCompetencyFromId")
    void updateEntityFromDto(GuidelineReqDto guidelineReqDto, @MappingTarget Guideline existingGuideline);

    @Named("mapCompetencyId")
    default Long mapCompetencyId(Competency competency) {
        if (competency == null) {
            return null;
        }
        return competency.getCompetencyId();
    }

    @Named("mapCompetencyFromId")
    default Competency mapCompetencyFromId(Long competencyId) {
        if (competencyId == null) {
            return null;
        }
        Competency competency = new Competency();
        competency.setCompetencyId(competencyId);
        return competency;
    }
}
