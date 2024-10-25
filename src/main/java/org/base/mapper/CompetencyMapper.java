package org.base.mapper;

import org.base.dto.CompetencyReqDto;
import org.base.dto.CompetencyResDto;
import org.base.dto.GuidelineReqDto;
import org.base.dto.GuidelineResDto;
import org.base.model.Competency;
import org.base.model.CompetencyGroup;
import org.base.model.Guideline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyMapper {

    @Mapping(source = "guidelines", target = "guidelines", qualifiedByName = "mapGuidelineReqDtos")
    @Mapping(source = "competencyGroup", target = "competencyGroupId", qualifiedByName = "mapCompetencyGroupId")
    CompetencyReqDto toReqDto(Competency competency);

    @Mapping(source = "guidelines", target = "guidelines", qualifiedByName = "mapGuidelineResDtos")
    @Mapping(source = "competencyGroup", target = "competencyGroupId", qualifiedByName = "mapCompetencyGroupId")
    CompetencyResDto toResDto(Competency competency);

    @Mapping(target = "competencyId", ignore = true)
    @Mapping(source = "competencyGroupId", target = "competencyGroup", qualifiedByName = "mapCompetencyGroupFromId")
    Competency toEntity(CompetencyReqDto competencyReqDto);

    @Mapping(target = "competencyId", ignore = true)
    @Mapping(source = "competencyGroupId", target = "competencyGroup", qualifiedByName = "mapCompetencyGroupFromId")
    void updateEntityFromDto(CompetencyReqDto competencyReqDto, @MappingTarget Competency existingCompetency);

    @Named("mapCompetencyGroupId")
    default Long mapCompetencyGroupId(CompetencyGroup competencyGroup) {
        if (competencyGroup == null) {
            return null;
        }
        return competencyGroup.getCompetencyGroupId();
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

    @Named("mapGuidelineReqDtos")
    default List<GuidelineReqDto> mapGuidelineReqDtos(List<Guideline> guidelines) {
        if (guidelines == null) {
            return null;
        }
        return guidelines.stream().map(guideline -> {
            GuidelineReqDto dto = new GuidelineReqDto();
            dto.setCompetencyId(guideline.getCompetency() != null ? guideline.getCompetency().getCompetencyId() : null);
            dto.setGuidelineId(guideline.getGuidelineId());
            dto.setIndicatorDescription(guideline.getIndicatorDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Named("mapGuidelineResDtos")
    default List<GuidelineResDto> mapGuidelineResDtos(List<Guideline> guidelines) {
        if (guidelines == null) {
            return null;
        }
        return guidelines.stream().map(guideline -> {
            GuidelineResDto dto = new GuidelineResDto();
            dto.setCompetencyId(guideline.getCompetency() != null ? guideline.getCompetency().getCompetencyId() : null);
            dto.setGuidelineId(guideline.getGuidelineId());
            dto.setIndicatorDescription(guideline.getIndicatorDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Named("mapGuidelinesFromDtos")
    default List<Guideline> mapGuidelinesFromDtos(List<GuidelineReqDto> guidelineReqDtos, Competency competency) {
        if (guidelineReqDtos == null) {
            return null;
        }
        return guidelineReqDtos.stream().map(guidelineReqDto -> {
            Guideline guideline = new Guideline();
            guideline.setCompetency(competency);
            guideline.setGuidelineId(guidelineReqDto.getGuidelineId());
            guideline.setIndicatorDescription(guidelineReqDto.getIndicatorDescription());
            return guideline;
        }).collect(Collectors.toList());
    }
}
