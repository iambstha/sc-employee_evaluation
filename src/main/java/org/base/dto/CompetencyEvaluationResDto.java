package org.base.dto;

import lombok.Data;

@Data
public class CompetencyEvaluationResDto {

    private Long competencyEvaluationId;
    private Long competencyId;
    private Long evaluationId;
    private ScoreResDto score;
    private String managerComments;
    private String employeeComments;

}
