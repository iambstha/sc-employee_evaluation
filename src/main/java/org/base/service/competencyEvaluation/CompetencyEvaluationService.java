package org.base.service.competencyEvaluation;

import org.base.dto.CompetencyEvaluationReqDto;
import org.base.dto.CompetencyEvaluationResDto;

import java.util.List;

public interface CompetencyEvaluationService {

    CompetencyEvaluationResDto save(CompetencyEvaluationReqDto competencyEvaluationReqDto);

    List<CompetencyEvaluationResDto> getPaginated(int page, int size);

    CompetencyEvaluationResDto getById(Long id);

    CompetencyEvaluationResDto updateById(Long id, CompetencyEvaluationReqDto competencyEvaluationReqDto);

    void deleteById(Long id);

    long countTotal();

}
