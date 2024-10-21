package org.base.dto;

import lombok.Data;

@Data
public class CompetencyEvaluationResDto {

    private Long competencyEvaluationId;
    private Long evaluationId;
    private Long competencyId;
    private ScoreResDto scoreResDto;
    private String managerComments;
    private String employeeComments;

}
