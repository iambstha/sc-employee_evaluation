package org.base.service.competencyGroupComment;

import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.CompetencyGroupCommentResDto;
import org.base.model.enums.EmployeeType;
import org.base.model.enums.ReviewStage;

import java.util.List;

public interface CompetencyGroupCommentService {


    CompetencyGroupCommentResDto save(CompetencyGroupCommentReqDto competencyGroupCommentReqDto);

    List<CompetencyGroupCommentResDto> getPaginated(int page, int size);

    CompetencyGroupCommentResDto getByIdAndReviewStage(Long id, ReviewStage reviewStage);

    List<CompetencyGroupCommentResDto> getByFilters(EmployeeType employeeType, Long competencyGroupId, Long evaluationId, Long employeeId);

    CompetencyGroupCommentResDto updateByIdAndReviewStage(Long id, CompetencyGroupCommentReqDto competencyGroupCommentReqDto, ReviewStage reviewStage);

    void deleteByIdAndReviewStage(Long id, ReviewStage reviewStage);

    long countTotal();

}
