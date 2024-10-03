package org.base.service.evaluation;

import org.base.dto.CompetencyReqDto;
import org.base.dto.CompetencyResDto;
import org.base.dto.EvaluationReqDto;
import org.base.dto.EvaluationResDto;

import java.util.List;

public interface EvaluationService {

    EvaluationResDto save(EvaluationReqDto evaluationReqDto);

    List<EvaluationResDto> getAll();

    EvaluationResDto getById(Long id);

    EvaluationResDto updateById(Long id, EvaluationReqDto evaluationReqDto);

    void deleteById(Long id);

}
