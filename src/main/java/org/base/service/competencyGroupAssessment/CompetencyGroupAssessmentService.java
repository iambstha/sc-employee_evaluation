package org.base.service.competencyGroupAssessment;

import org.base.dto.CompetencyGroupAssessmentReqDto;
import org.base.dto.CompetencyGroupAssessmentResDto;

import java.util.List;

public interface CompetencyGroupAssessmentService {

    CompetencyGroupAssessmentResDto save(CompetencyGroupAssessmentReqDto competencyGroupAssessmentReqDto);

    List<CompetencyGroupAssessmentResDto> getAll();

    CompetencyGroupAssessmentResDto getById(Long id);

    CompetencyGroupAssessmentResDto updateById(Long id, CompetencyGroupAssessmentReqDto competencyGroupAssessmentReqDto);

    void deleteById(Long id);

}
