package org.base.service.evaluation;

import org.base.dto.EvaluationReqDto;
import org.base.dto.EvaluationResDto;
import org.base.model.enums.ApprovalStage;
import org.base.model.enums.EvaluationByType;
import org.base.model.enums.ReviewStage;

import java.util.List;

public interface EvaluationService {

    EvaluationResDto save(EvaluationReqDto evaluationReqDto);

    List<EvaluationResDto> getPaginated(int page, int size);

    EvaluationResDto getById(Long id);

    List<EvaluationResDto> getByFilters(EvaluationByType evaluationByType, ReviewStage reviewStage, Long employeeId);

    EvaluationResDto updateById(Long id, EvaluationReqDto evaluationReqDto);

    EvaluationResDto updateReviewStage(Long id, ReviewStage reviewStage, ApprovalStage approvalStage);

    void deleteById(Long id);

    long countTotal();

}
