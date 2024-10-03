package org.base.dto;

import lombok.Data;

@Data
public class CompetencyEvaluationReqDto {

    private Long competencyEvaluationId;
    private Long evaluationId;
    private Long competencyId;
    private Long scoreId;
    private String managerComments;
    private String employeeComments;

}
