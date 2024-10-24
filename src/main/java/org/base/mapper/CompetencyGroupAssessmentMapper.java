package org.base.mapper;

import org.base.dto.*;
import org.base.model.CompetencyGroupAssessment;
import org.base.model.CompetencyGroupComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = QuarkusMappingConfig.class)
public interface CompetencyGroupAssessmentMapper {


    @Mapping(source = "competencyGroupComments", target = "competencyGroupComments", qualifiedByName = "mapCompetencyGroupCommentsReqDtos")
    CompetencyGroupAssessmentReqDto toReqDto(CompetencyGroupAssessment competencyGroupAssessment);

    @Mapping(source = "competencyGroupComments", target = "competencyGroupComments", qualifiedByName = "mapCompetencyGroupCommentsResDtos")
    CompetencyGroupAssessmentResDto toResDto(CompetencyGroupAssessment competencyGroupAssessment);

    @Mapping(target = "competencyGroupAssessmentId", ignore = true)
    CompetencyGroupAssessment toEntity(CompetencyGroupAssessmentReqDto competencyGroupAssessmentReqDto);

    @Mapping(target = "competencyGroupAssessmentId", ignore = true)
    @Mapping(source = "status", target = "status")
    void updateEntityFromDto(CompetencyGroupAssessmentReqDto competencyGroupAssessmentReqDto, @MappingTarget CompetencyGroupAssessment existingCompetencyGroupAssessment);

    @Named("mapCompetencyGroupCommentsReqDtos")
    default List<CompetencyGroupCommentReqDto> mapCompetencyGroupCommentsReqDtos(List<CompetencyGroupComment> competencyGroupComments) {
        if (competencyGroupComments == null) {
            return null;
        }
        return competencyGroupComments.stream().map(competencyGroupComment -> {
            CompetencyGroupCommentReqDto dto = new CompetencyGroupCommentReqDto();
            dto.setCompetencyGroupAssessmentId(competencyGroupComment.getComment() != null ? competencyGroupComment.getCompetencyGroupAssessment().getCompetencyGroupAssessmentId() : null);
            dto.setEmployeeId(competencyGroupComment.getEmployeeId());
            dto.setComment(competencyGroupComment.getComment());
            return dto;
        }).collect(Collectors.toList());
    }

    @Named("mapCompetencyGroupCommentsResDtos")
    default List<CompetencyGroupCommentResDto> mapCompetencyGroupCommentsResDtos(List<CompetencyGroupComment> competencyGroupComments) {
        if (competencyGroupComments == null) {
            return null;
        }
        return competencyGroupComments.stream().map(competencyGroupComment -> {
            CompetencyGroupCommentResDto dto = new CompetencyGroupCommentResDto();
            dto.setCompetencyGroupAssessmentId(competencyGroupComment.getComment() != null ? competencyGroupComment.getCompetencyGroupAssessment().getCompetencyGroupAssessmentId() : null);
            dto.setEmployeeId(competencyGroupComment.getEmployeeId());
            dto.setComment(competencyGroupComment.getComment());
            return dto;
        }).collect(Collectors.toList());
    }

    @Named("mapCompetencyGroupCommentsFromDtos")
    default List<CompetencyGroupComment> mapCompetencyGroupCommentsFromDtos(List<CompetencyGroupCommentReqDto> competencyGroupCommentReqDtos, CompetencyGroupAssessment competencyGroupAssessment) {
        if (competencyGroupAssessment == null) {
            return null;
        }
        return competencyGroupCommentReqDtos.stream().map(competencyGroupCommentReqDto -> {
            CompetencyGroupComment competencyGroupComment = new CompetencyGroupComment();
            competencyGroupComment.setCompetencyGroupAssessment(competencyGroupAssessment);
            competencyGroupComment.setCompetencyGroupCommentId(competencyGroupCommentReqDto.getCompetencyGroupCommentId());
            competencyGroupComment.setComment(competencyGroupCommentReqDto.getComment());
            competencyGroupComment.setEmployeeId(competencyGroupCommentReqDto.getEmployeeId());
            return competencyGroupComment;
        }).collect(Collectors.toList());
    }

}
