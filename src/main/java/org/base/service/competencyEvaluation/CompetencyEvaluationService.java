package org.base.service.competencyEvaluation;

import org.base.dto.CompetencyEvaluationReqDto;
import org.base.dto.CompetencyEvaluationResDto;
import org.base.dto.EvaluationReqDto;
import org.base.dto.EvaluationResDto;

import java.util.List;

public interface CompetencyEvaluationService {

    CompetencyEvaluationResDto save(CompetencyEvaluationReqDto competencyEvaluationReqDto);

    List<CompetencyEvaluationResDto> getAll();

    CompetencyEvaluationResDto getById(Long id);

    CompetencyEvaluationResDto updateById(Long id, CompetencyEvaluationReqDto competencyEvaluationReqDto);

    void deleteById(Long id);

}
