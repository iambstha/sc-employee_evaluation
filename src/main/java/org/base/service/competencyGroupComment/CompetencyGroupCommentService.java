package org.base.service.competencyGroupComment;

import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.CompetencyGroupCommentResDto;
import org.base.model.CompetencyGroup;
import org.base.model.enums.EmployeeType;

import java.util.List;

public interface CompetencyGroupCommentService {


    CompetencyGroupCommentResDto save(CompetencyGroupCommentReqDto competencyGroupCommentReqDto);

    List<CompetencyGroupCommentResDto> getAll();

    CompetencyGroupCommentResDto getById(Long id);

    List<CompetencyGroupCommentResDto> getByFilters(EmployeeType employeeType, Long competencyGroupId, Long employeeId);

    CompetencyGroupCommentResDto updateById(Long id, CompetencyGroupCommentReqDto competencyGroupCommentReqDto);

    void deleteById(Long id);

}
