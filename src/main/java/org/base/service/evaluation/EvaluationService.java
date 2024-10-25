package org.base.service.evaluation;

import org.base.dto.CompetencyReqDto;
import org.base.dto.CompetencyResDto;
import org.base.dto.EvaluationReqDto;
import org.base.dto.EvaluationResDto;
import org.base.model.enums.EvaluationByType;

import java.util.List;

public interface EvaluationService {

    EvaluationResDto save(EvaluationReqDto evaluationReqDto);

    List<EvaluationResDto> getAll();

    EvaluationResDto getById(Long id);

    List<EvaluationResDto> getByFilters(EvaluationByType evaluationByType, Long employeeId);

    EvaluationResDto updateById(Long id, EvaluationReqDto evaluationReqDto);

    void deleteById(Long id);

}
