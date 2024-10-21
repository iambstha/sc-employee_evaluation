package org.base.dto;

import lombok.Data;
import org.base.model.enums.EvaluationByType;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EvaluationResDto {

    private Long evaluationId;
    private Long employeeId;
    private List<CompetencyEvaluationResDto> competencyEvaluations;
    private EvaluationByType evaluationByType;
    private LocalDateTime periodFrom;
    private LocalDateTime periodTo;
    private LocalDateTime evaluationDate;
    private String managerComments;
    private String employeeComments;

}
