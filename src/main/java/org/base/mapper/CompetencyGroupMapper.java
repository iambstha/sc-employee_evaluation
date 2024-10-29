package org.base.mapper;

import org.base.dto.*;
import org.base.model.CompetencyGroup;
import org.base.model.CompetencyGroupComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyGroupMapper {

    CompetencyGroupReqDto toReqDto(CompetencyGroup competencyGroup);

    @Mapping(source = "competencyGroupComments", target = "competencyGroupComments", qualifiedByName = "mapCompetencyGroupCommentResDtos")
    CompetencyGroupResDto toResDto(CompetencyGroup competencyGroup);

    @Mapping(target = "competencyGroupId", ignore = true)
    @Mapping(source = "competencyType", target = "competencyType")
    CompetencyGroup toEntity(CompetencyGroupReqDto competencyGroupReqDto);

    @Mapping(target = "competencyGroupId", ignore = true)
    @Mapping(source = "status", target = "status")
    @Mapping(source = "competencyType", target = "competencyType")
    void updateEntityFromDto(CompetencyGroupReqDto competencyGroupReqDto, @MappingTarget CompetencyGroup existingCompetencyGroup);

    @Named("mapCompetencyGroupCommentResDtos")
    default List<CompetencyGroupCommentResDto> mapCompetencyGroupCommentResDtos(List<CompetencyGroupComment> competencyGroupComments) {
        if (competencyGroupComments == null) {
            return null;
        }
        return competencyGroupComments.stream().map(competencyGroupComment -> {
            CompetencyGroupCommentResDto dto = new CompetencyGroupCommentResDto();
            dto.setCompetencyGroupId(Optional.ofNullable(competencyGroupComment.getCompetencyGroup())
                    .map(CompetencyGroup::getCompetencyGroupId)
                    .orElse(null));
            dto.setCommentId(competencyGroupComment.getCompetencyGroupCommentId());
            dto.setComment(competencyGroupComment.getComment());
            dto.setEmployeeId(competencyGroupComment.getEmployeeId());
            dto.setEmployeeType(competencyGroupComment.getEmployeeType());
            return dto;
        }).collect(Collectors.toList());
    }
    
}
