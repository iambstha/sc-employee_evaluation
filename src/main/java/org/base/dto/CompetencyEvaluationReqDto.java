package org.base.dto;

import lombok.Data;

@Data
public class CompetencyEvaluationReqDto {

    private Long competencyEvaluationId;
    private Long competencyId;
    private Long evaluationId;
    private ScoreReqDto score;
    private String managerComments;
    private String employeeComments;

}
